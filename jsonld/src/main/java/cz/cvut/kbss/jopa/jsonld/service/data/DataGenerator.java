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
package cz.cvut.kbss.jopa.jsonld.service.data;

import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.jsonld.model.Study;
import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.jsonld.service.repository.OrganizationRepositoryService;
import cz.cvut.kbss.jopa.jsonld.service.repository.StudyRepositoryService;
import cz.cvut.kbss.jopa.jsonld.service.repository.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataGenerator {

    private static final int MIN_COUNT = 10;
    private static final int MAX_COUNT = 20;

    @Autowired
    private UserRepositoryService userService;

    @Autowired
    private StudyRepositoryService studyService;

    @Autowired
    private OrganizationRepositoryService organizationService;

    private final Random random = new Random();

    @PostConstruct
    private void generateSampleData() {
        final List<Organization> organizations = getOrganizations();
        final List<User> users = getUsers(organizations);
        generateStudies(users);
    }

    private List<Organization> getOrganizations() {
        List<Organization> organizations = organizationService.findAll();
        if (!organizations.isEmpty()) {
            return organizations;
        }
        organizations = new ArrayList<>();
        for (int i = 0; i < randomCount(); i++) {
            final Organization org = new Organization();
            org.setName("Organization" + random.nextInt());
            org.setEmailAddress(org.getName().toLowerCase() + "@jopaexample.org");
            organizations.add(org);
        }
        organizationService.persist(organizations);
        return organizations;
    }

    private int randomCount() {
        int result;
        do {
            result = random.nextInt(MAX_COUNT);
        } while (result < MIN_COUNT);
        return result;
    }

    private List<User> getUsers(List<Organization> organizations) {
        List<User> users = userService.findAll();
        if (users.size() > 1) { // Do not forget the default user
            return users;
        }
        users = new ArrayList<>();
        for (int i = 0; i < randomCount(); i++) {
            final User user = new User();
            user.setFirstName("FirstName" + random.nextInt());
            user.setLastName("LastName" + random.nextInt());
            user.setEmailAddress(user.getFirstName() + '.' + user.getLastName() + "@jopaexample.org");
            user.setClinic(randomItem(organizations));
            users.add(user);
        }
        userService.persist(users);
        return users;
    }

    private <T> T randomItem(List<T> items) {
        return items.get(random.nextInt(items.size()));
    }

    private void generateStudies(List<User> users) {
        if (!studyService.findAll().isEmpty()) {
            return;
        }
        final List<Study> studies = new ArrayList<>();
        for (int i = 0; i < randomCount(); i++) {
            final Study s = new Study();
            s.setName("Study" + random.nextInt());
            s.addAdministrator(randomItem(users));
            for (int j = 0; j < random.nextInt(users.size()); j++) {
                s.addParticipant(randomItem(users));
            }
            studies.add(s);
        }
        studyService.persist(studies);
    }
}
