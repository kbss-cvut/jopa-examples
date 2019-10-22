/**
 * Copyright (C) 2019 Czech Technical University in Prague
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.jopa.eswc2016.persistence.dao;

import cz.cvut.kbss.jopa.eswc2016.config.ConfigurationService;
import cz.cvut.kbss.jopa.eswc2016.persistence.owlapi.OwlapiDataProvider;
import cz.cvut.kbss.jopa.eswc2016.service.DataFormat;
import cz.cvut.kbss.jopa.eswc2016.util.ConfigParam;
import cz.cvut.kbss.jopa.eswc2016.util.RepositoryType;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandler;
import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.eclipse.rdf4j.rio.rdfjson.RDFJSONWriter;
import org.eclipse.rdf4j.rio.rdfxml.util.RDFXMLPrettyWriter;
import org.eclipse.rdf4j.rio.turtle.TurtleWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Component
public class DataDao {

    private static final Logger LOG = LoggerFactory.getLogger(DataDao.class);

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private Repository repository;

    @Autowired
    private OwlapiDataProvider owlapiProvider;

    public String getRepositoryData(DataFormat format) {
        final RepositoryType repoType = RepositoryType
                .fromString(configurationService.get(ConfigParam.REPOSITORY_TYPE));
        switch (repoType) {
            case SESAME:
                return getSesameData(format);
            case OWLAPI:
                return getOwlapiData();
            default:
                throw new IllegalArgumentException("Unsupported repository type " + repoType);
        }
    }

    private String getSesameData(DataFormat format) {
        try {
            final RepositoryConnection connection = repository.getConnection();
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final RDFHandler rdfHandler = getHandler(format, bos);
            connection.export(rdfHandler);
            connection.close();
            return new String(bos.toByteArray());
        } catch (RepositoryException | RDFHandlerException e) {
            LOG.error("Unable to read data from repository.", e);
            return "";
        }
    }

    private RDFHandler getHandler(DataFormat format, OutputStream os) {
        switch (format) {
            case JSON:
                return new RDFJSONWriter(os, RDFFormat.RDFJSON);
            case RDFXML:
                return new RDFXMLPrettyWriter(os);
            case TURTLE:
                return new TurtleWriter(os);
            default:
                throw new IllegalArgumentException("Unsupported data format: " + format);
        }
    }

    private String getOwlapiData() {
        return owlapiProvider.getData();
    }
}
