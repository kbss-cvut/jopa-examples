package cz.cvut.kbss.jopa.jsonld.rest;

import cz.cvut.kbss.jopa.jsonld.model.User;
import cz.cvut.kbss.jopa.jsonld.rest.exception.NotFoundException;
import cz.cvut.kbss.jopa.jsonld.service.UserService;
import cz.cvut.kbss.jsonld.JsonLd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, produces = JsonLd.MEDIA_TYPE)
    public List<User> getUsers() {
        return userService.findAll();
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET, produces = JsonLd.MEDIA_TYPE)
    public User getUser(@PathVariable(name = "key") String key) {
        final User user = userService.findByKey(key);
        if (user == null) {
            throw NotFoundException.create("User", key);
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = JsonLd.MEDIA_TYPE)
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.persist(user);
        LOG.trace("User {} successfully persisted.", user);
        final String key = user.getKey();
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{key}", key);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.PUT, consumes = JsonLd.MEDIA_TYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable(name = "key") String key, @RequestBody User user) {
        if (!key.equals(user.getKey())) {
            throw new IllegalArgumentException("The passed user's key is different from the specified one.");
        }
        getUser(key);  // Check that the user exists
        userService.update(user);
        LOG.trace("User {} successfully updated.", user);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(name = "key") String key) {
        final User user = getUser(key);
        userService.remove(user);
        LOG.trace("User {} removed.", user);
    }
}
