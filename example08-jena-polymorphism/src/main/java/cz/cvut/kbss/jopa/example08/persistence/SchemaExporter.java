/**
 * Copyright (C) 2019 Czech Technical University in Prague
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.jopa.example08.persistence;

import cz.cvut.kbss.jopa.example08.model.Vocabulary;
import cz.cvut.kbss.ontodriver.jena.config.JenaOntoDriverProperties;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.RDFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.jena.rdf.model.ResourceFactory.createResource;
import static org.apache.jena.rdf.model.ResourceFactory.createStatement;

@Component
public class SchemaExporter {

    @Value("${repository.type}")
    private String repoType;

    @Value("${repositoryUrl}")
    private String repoUrl;

    public void exportSchema() {
        switch (repoType) {
            case JenaOntoDriverProperties.TDB:
                exportToTDB();
                break;
            case JenaOntoDriverProperties.FUSEKI:
                exportToFuseki();
                break;
            default:
                throw new IllegalArgumentException("Unsupported Jena repository type " + repoType);
        }
    }

    private void exportToTDB() {
        final Dataset ds = TDBFactory.createDataset(repoUrl);
        try {
            ds.begin(ReadWrite.WRITE);
            export(ds);
            ds.commit();
        } finally {
            ds.close();
        }
    }

    private void export(Dataset ds) {
        final Model jenaModel = ds.getDefaultModel();
        jenaModel.add(createStatement(createResource(Vocabulary.C_OCCURRENCE_REPORT), RDFS.subClassOf,
                createResource(Vocabulary.C_REPORT)));
        jenaModel.add(createStatement(createResource(Vocabulary.C_AUDIT_REPORT), RDFS.subClassOf,
                createResource(Vocabulary.C_REPORT)));
        jenaModel.add(createStatement(createResource(Vocabulary.C_AUDIT), RDFS.subClassOf,
                createResource(Vocabulary.C_EVENT)));
        jenaModel.add(createStatement(createResource(Vocabulary.C_OCCURRENCE), RDFS.subClassOf,
                createResource(Vocabulary.C_EVENT)));
    }

    private void exportToFuseki() {
        try (final RDFConnection connection = RDFConnectionFuseki.create().destination(repoUrl).build()) {
            connection.begin(ReadWrite.WRITE);
            final Dataset ds = connection.fetchDataset();
            export(ds);
            connection.putDataset(ds);
            connection.commit();
        }
    }
}
