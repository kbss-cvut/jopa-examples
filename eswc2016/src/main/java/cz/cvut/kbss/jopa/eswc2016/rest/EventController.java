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
package cz.cvut.kbss.jopa.eswc2016.rest;

import cz.cvut.kbss.jopa.eswc2016.model.model.Event;
import cz.cvut.kbss.jopa.eswc2016.rest.exception.NotFoundException;
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
