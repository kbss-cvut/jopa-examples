'use strict';

module.exports = {
    REPOSITORY_TYPE_PARAM: 'repositoryType',

    RECORD_TYPES: {
        VALID: {
            value: 'http://onto.fel.cvut.cz/ontologies/ufo/ValidAnswer',
            className: 'valid'
        },
        PARTIAL: {
            value: 'http://onto.fel.cvut.cz/ontologies/ufo/PartiallyValidAnswer',
            className: 'part-valid'
        },
        INVALID: {
            value: 'http://onto.fel.cvut.cz/ontologies/ufo/InvalidAnswer',
            className: 'invalid'
        }
    },

    STORAGE_INFO: {
        sesame: 'Sesame storage.\nSupported:\n - contexts,\n - remote repository\n\n' +
        'NOT Supported:\n - OWL inference (e.g. the isDocumentedBy property, so audit detail shows no reports)\n',

        owlapi: 'OWL API storage.\nSupported:\n - inference (Pellet),\n\n' +
        'NOT Supported:\n - contexts,\n - SPARQL count (due to OWL2Query engine, so record count in reports is always 0)\n'
    }
};
