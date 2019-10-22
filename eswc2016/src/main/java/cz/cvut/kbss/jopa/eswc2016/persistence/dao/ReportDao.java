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

import cz.cvut.kbss.jopa.eswc2016.model.Vocabulary;
import cz.cvut.kbss.jopa.eswc2016.model.dto.ReportDto;
import cz.cvut.kbss.jopa.eswc2016.model.model.Report;
import cz.cvut.kbss.jopa.eswc2016.util.KeyGenerator;
import cz.cvut.kbss.jopa.exceptions.NoResultException;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.descriptors.EntityDescriptor;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReportDao extends BaseDao<Report> {

    public ReportDao() {
        super(Report.class);
    }

    @Override
    protected Report findByUri(String uri, EntityManager em) {
        return em.find(Report.class, uri, getDescriptor());
    }

    protected Report findByKey(Long key, EntityManager em) {
        try {
            final Object uri = em.createNativeQuery("SELECT ?x WHERE { ?x <" + Vocabulary.s_p_identifier + "> ?key ;" +
                    "a ?type }")
                                 .setParameter("key", key).setParameter("type", typeUri)
                                 .getSingleResult();
            return findByUri(uri.toString(), em);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    protected void persist(Report entity, EntityManager em) {
        entity.setIdentifier(KeyGenerator.generateKey());
        em.persist(entity, getDescriptor());
    }

    private EntityDescriptor getDescriptor() {
        final EntityDescriptor descriptor = new EntityDescriptor();
        try {
            descriptor.addAttributeDescriptor(Report.class.getDeclaredField("hasAuthor"), PersonDao.PERSON_DESCRIPTOR);
        } catch (NoSuchFieldException e) {
            LOG.error("Unable to set person context for report.", e);
        }
        return descriptor;
    }

    public List<ReportDto> findAllDtos() {
        final EntityManager em = entityManager();
        try {
            final List<?> reports = em
                    .createNativeQuery("SELECT ?r ?key ?aTitle ?aDate (count(?record) as ?recordCount) WHERE {" +
                            "?r a ?type ;" +
                            "?hasKey ?key ;" +
                            "?hasRecord ?record ." +
                            "?r ?documents ?audit ." +
                            "?audit ?hasTitle ?aTitle ." +
                            "?audit ?date ?aDate ." +
                            "} GROUP BY ?r ?key ?aTitle ?aDate \n" +
                            "HAVING (?recordCount > 0)")
                    .setParameter("type", URI.create(Vocabulary.s_c_report))
                    .setParameter("hasKey", URI.create(Vocabulary.s_p_identifier))
                    .setParameter("hasRecord", URI.create(Vocabulary.s_p_has_documentation_part))
                    .setParameter("documents", URI.create(Vocabulary.s_p_documents))
                    .setParameter("hasTitle", URI.create(Vocabulary.s_p_title))
                    .setParameter("date", URI.create(Vocabulary.s_p_date)).getResultList();
            return reports.stream().map(item -> {
                final Object[] row = (Object[]) item;
                final ReportDto dto = new ReportDto();
                dto.setId(row[0].toString());
                dto.setIdentifier((Long) row[1]);
                dto.setAuditTitle((String) row[2]);
                dto.setAuditDate((Date) row[3]);
                dto.setRecordCount((Integer) row[4]);
                return dto;
            }).collect(Collectors.toList());
        } finally {
            em.close();
        }
    }
}
