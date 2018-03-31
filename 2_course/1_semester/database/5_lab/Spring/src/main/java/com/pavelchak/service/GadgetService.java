package com.pavelchak.service;

import com.pavelchak.Repository.GagdetRepository;
import com.pavelchak.Repository.PersonRepository;
import com.pavelchak.domain.Gadget;
import com.pavelchak.domain.Person;
import com.pavelchak.exceptions.ExistsPersonForBookException;
import com.pavelchak.exceptions.NoSuchBookException;
import com.pavelchak.exceptions.NoSuchPersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class GadgetService {
    @Autowired
    GagdetRepository gagdetRepository;

    @Autowired
    PersonRepository personRepository;

    public Set<Gadget> getGadgetsByPersonId(Long person_id) throws NoSuchPersonException {
//        Person person = personRepository.findOne(person_id);//1.5.9
        Person person = personRepository.findById(person_id).get();//2.0.0.M7
        if (person == null) throw new NoSuchPersonException();
        return person.getGadgets();
    }

    public Gadget getGadget(Long gadget_id) throws NoSuchBookException {
//        Gadget gadget = bookRepository.findOne(gadget_id);//1.5.9
        Gadget gadget = gagdetRepository.findById(gadget_id).get();//2.0.0.M7
        if (gadget == null) throw new NoSuchBookException();
        return gadget;
    }

    public List<Gadget> getAllGadgets() {
        return gagdetRepository.findAll();
    }

    @Transactional
    public void createGadget(Gadget gadget) {
        gagdetRepository.save(gadget);
    }

    @Transactional
    public void updateGadget(Gadget uGadget, Long gadget_id) throws NoSuchBookException {
//        Gadget gadget = bookRepository.findOne(gadget_id);//1.5.9
        Gadget gadget = gagdetRepository.findById(gadget_id).get();//2.0.0.M7
        if (gadget == null) throw new NoSuchBookException();
        //update
        gadget.setGadgetName(uGadget.getGadgetName());
        gadget.setAuthor(uGadget.getAuthor());
        gadget.setPublisher(uGadget.getPublisher());
        gadget.setYear_of_creating(uGadget.getYear_of_creating());
        gadget.setAmount(uGadget.getAmount());
    }

    @Transactional
    public void deleteGadget(Long gadget_id) throws NoSuchBookException, ExistsPersonForBookException {
//        Gadget gadget = bookRepository.findOne(gadget_id);//1.5.9
        Gadget gadget = gagdetRepository.findById(gadget_id).get();//2.0.0.M7

        if (gadget == null) throw new NoSuchBookException();
        if (gadget.getPersons().size() != 0) throw new ExistsPersonForBookException();
        gagdetRepository.delete(gadget);
    }
}
