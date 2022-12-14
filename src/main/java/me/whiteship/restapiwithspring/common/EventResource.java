package me.whiteship.restapiwithspring.common;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import me.whiteship.restapiwithspring.events.Event;
import me.whiteship.restapiwithspring.events.EventController;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class EventResource extends RepresentationModel {
    @JsonUnwrapped
    private Event event;

    public EventResource(Event event) {
        this.event = event;
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }

    public Event getEvent() {
        return event;
    }
}
