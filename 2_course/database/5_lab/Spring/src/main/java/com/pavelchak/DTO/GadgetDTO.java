package com.pavelchak.DTO;

import com.pavelchak.controller.PersonController;
import com.pavelchak.domain.Gadget;
import com.pavelchak.exceptions.NoSuchBookException;
import com.pavelchak.exceptions.NoSuchPersonException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class GadgetDTO extends ResourceSupport {
    Gadget gadget;
    public GadgetDTO(Gadget gadget, Link selfLink) throws NoSuchBookException, NoSuchPersonException {
        this.gadget = gadget;
        add(selfLink);
        add(linkTo(methodOn(PersonController.class).getPersonsByGadgetID(gadget.getId())).withRel("persons"));
    }

    public Long getGadgetId() {
        return gadget.getId();
    }

    public String getGadgetName() {
        return gadget.getGadgetName();
    }

    public String getAuthor() {
        return gadget.getAuthor();
    }

    public String getPublisher() {
        return gadget.getPublisher();
    }

    public Integer getYear_of_creating() {
        return gadget.getYear_of_creating();
    }

    public Integer getAmount() {
        return gadget.getAmount();
    }
}
