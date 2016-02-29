package cz.cvut.kbss.jopa.eswc2016.rest;

import cz.cvut.kbss.jopa.eswc2016.model.model.Event;
import cz.cvut.kbss.jopa.eswc2016.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController extends BaseController {

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getEvents() {
        return eventService.findAll();
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Event getEvent(@PathVariable("key") Long key) {
        return getEventInternal(key);
    }

    private Event getEventInternal(Long key) {
        final Event evt = eventService.findByKey(key);
        if (evt == null) {
            throw NotFoundException.create("Event", key);
        }
        return evt;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createEvent(@RequestBody Event event) {
        eventService.persist(event);
        if (LOG.isTraceEnabled()) {
            LOG.trace("Event " + event + " successfully created.");
        }
        final HttpHeaders header = RestUtils.createLocationHeader("/{key}", event.getIdentifier());
        return new ResponseEntity<>(header, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEvent(@PathVariable("key") Long key, @RequestBody Event update) {
        final Event orig = getEventInternal(key);
        assert orig.getId().equals(update.getId());
        eventService.update(update);
        if (LOG.isTraceEnabled()) {
            LOG.trace("Updated event {}.", update);
        }
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable("key") Long key) {
        final Event toRemove = getEventInternal(key);
        eventService.remove(toRemove);
        if (LOG.isTraceEnabled()) {
            LOG.trace("Event {} successfully removed.", toRemove);
        }
    }
}
