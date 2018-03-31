package com.pavelchak.controller;


import com.pavelchak.DTO.GadgetDTO;
import com.pavelchak.domain.Gadget;
import com.pavelchak.exceptions.ExistsPersonForBookException;
import com.pavelchak.exceptions.NoSuchBookException;
import com.pavelchak.exceptions.NoSuchPersonException;
import com.pavelchak.service.GadgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class GadgetController {
    @Autowired
    GadgetService gadgetService;

    @GetMapping(value = "/api/gadget/person/{person_id}")
    public ResponseEntity<List<GadgetDTO>> getGadgetsByPersonID(@PathVariable Long person_id) throws NoSuchPersonException, NoSuchBookException {
        Set<Gadget> gadgetList = gadgetService.getGadgetsByPersonId(person_id);
        Link link = linkTo(methodOn(GadgetController.class).getAllGadgets()).withSelfRel();

        List<GadgetDTO> gadgetsDTO = new ArrayList<>();
        for (Gadget entity : gadgetList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            GadgetDTO dto = new GadgetDTO(entity, selfLink);
            gadgetsDTO.add(dto);
        }

        return new ResponseEntity<>(gadgetsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/gadget/{gadget_id}")
    public ResponseEntity<GadgetDTO> getGadget(@PathVariable Long gadget_id) throws NoSuchBookException, NoSuchPersonException {
        Gadget gadget = gadgetService.getGadget(gadget_id);
        Link link = linkTo(methodOn(GadgetController.class).getGadget(gadget_id)).withSelfRel();

        GadgetDTO gadgetDTO = new GadgetDTO(gadget, link);

        return new ResponseEntity<>(gadgetDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/gadget")
    public ResponseEntity<List<GadgetDTO>> getAllGadgets() throws NoSuchBookException, NoSuchPersonException {
        List<Gadget> gadgetList = gadgetService.getAllGadgets();
        Link link = linkTo(methodOn(GadgetController.class).getAllGadgets()).withSelfRel();

        List<GadgetDTO> gadgetsDTO = new ArrayList<>();
        for (Gadget entity : gadgetList) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            GadgetDTO dto = new GadgetDTO(entity, selfLink);
            gadgetsDTO.add(dto);
        }

        return new ResponseEntity<>(gadgetsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/gadget")
    public ResponseEntity<GadgetDTO> addGadget(@RequestBody Gadget newGadget) throws NoSuchBookException, NoSuchPersonException {
        gadgetService.createGadget(newGadget);
        Link link = linkTo(methodOn(GadgetController.class).getGadget(newGadget.getId())).withSelfRel();

        GadgetDTO gadgetDTO = new GadgetDTO(newGadget, link);

        return new ResponseEntity<>(gadgetDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/gadget/{gadget_id}")
    public ResponseEntity<GadgetDTO> updateGadget(@RequestBody Gadget uGadget, @PathVariable Long gadget_id) throws NoSuchBookException, NoSuchPersonException {
        gadgetService.updateGadget(uGadget, gadget_id);
        Gadget gadget = gadgetService.getGadget(gadget_id);
        Link link = linkTo(methodOn(GadgetController.class).getGadget(gadget_id)).withSelfRel();

        GadgetDTO gadgetDTO = new GadgetDTO(gadget, link);

        return new ResponseEntity<>(gadgetDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/gadget/{gadget_id}")
    public  ResponseEntity deleteGadget(@PathVariable Long gadget_id) throws ExistsPersonForBookException, NoSuchBookException {
        gadgetService.deleteGadget(gadget_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
