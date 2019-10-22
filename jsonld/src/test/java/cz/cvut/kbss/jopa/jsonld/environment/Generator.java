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
package cz.cvut.kbss.jopa.jsonld.environment;

import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.jsonld.model.Study;
import cz.cvut.kbss.jopa.jsonld.model.User;

import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class Generator {

    private static final Random RAND = new Random();

    public static User generateUser() {
        final User user = new User();
        user.setFirstName("FirstName" + RAND.nextInt());
        user.setLastName("LastName" + RAND.nextInt());
        user.setEmailAddress(user.getFirstName() + '.' + user.getLastName() + "@example.org");
        return user;
    }

    public static Organization generateOrganization() {
        final Organization organization = new Organization();
        organization.setName("Organization" + RAND.nextInt());
        return organization;
    }

    public static Study generateStudy(boolean setAttributes) {
        final Study study = new Study();
        study.setAdministrators(Collections.singleton(generateUser()));
        study.setName("Study" + RAND.nextInt());
        if (setAttributes) {
            study.setCreated(new Date());
            study.setAuthor(generateUser());
        }
        return study;
    }

    public static int randomPositiveInt(int min, int max) {
        int result;
        do {
            result = RAND.nextInt(max);
        } while (min > result);
        return result;
    }
}
