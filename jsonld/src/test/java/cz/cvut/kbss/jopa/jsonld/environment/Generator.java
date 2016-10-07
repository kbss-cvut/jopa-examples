package cz.cvut.kbss.jopa.jsonld.environment;

import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.jsonld.model.Study;
import cz.cvut.kbss.jopa.jsonld.model.User;

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
