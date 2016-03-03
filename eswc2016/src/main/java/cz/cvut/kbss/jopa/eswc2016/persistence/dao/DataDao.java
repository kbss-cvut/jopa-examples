/**
 * Copyright (C) 2016 Czech Technical University in Prague
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.jopa.eswc2016.persistence.dao;

import com.github.jsonldjava.sesame.SesameJSONLDWriter;
import cz.cvut.kbss.jopa.eswc2016.config.ConfigurationService;
import cz.cvut.kbss.jopa.eswc2016.persistence.owlapi.OwlapiDataProvider;
import cz.cvut.kbss.jopa.eswc2016.service.DataFormat;
import cz.cvut.kbss.jopa.eswc2016.util.ConfigParam;
import cz.cvut.kbss.jopa.eswc2016.util.RepositoryType;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.rdfxml.util.RDFXMLPrettyWriter;
import org.openrdf.rio.turtle.TurtleWriter;
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
                return new SesameJSONLDWriter(os);
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
