package com.pavelchak.service;

import com.pavelchak.Repository.GagdetRepository;
import com.pavelchak.Repository.CityRepository;
import com.pavelchak.Repository.PersonRepository;
import com.pavelchak.domain.Gadget;
import com.pavelchak.domain.City;
import com.pavelchak.domain.Person;
import com.pavelchak.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    GagdetRepository gagdetRepository;

    public List<Person> getPersonByCityId(Long city_id) throws NoSuchCityException {
//        City city = cityRepository.findOne(city_id);//1.5.9
        City city = cityRepository.findById(city_id).get();//2.0.0.M7
        if (city == null) throw new NoSuchCityException();
        return city.getPersons();
    }

    public Person getPerson(Long person_id) throws NoSuchPersonException {
//        Person person = personRepository.findOne(person_id);//1.5.9
        Person person = personRepository.findById(person_id).get();//2.0.0.M7
        if (person == null) throw new NoSuchPersonException();
        return person;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Set<Person> getPersonsByGadgetId(Long gadget_id) throws NoSuchBookException {
//        Gadget gadget = bookRepository.findOne(gadget_id);//1.5.9
        Gadget gadget = gagdetRepository.findById(gadget_id).get();//2.0.0.M7
        if (gadget == null) throw new NoSuchBookException();
        return gadget.getPersons();
    }

    @Transactional
    public void createPerson(Person person, Long city_id) throws NoSuchCityException {
        if (city_id > 0) {
//            City city = cityRepository.findOne(city_id);//1.5.9
            City city = cityRepository.findById(city_id).get();//2.0.0.M7
            if (city == null) throw new NoSuchCityException();
            person.setCity(city);
        }
        personRepository.save(person);
    }

    @Transactional
    public void updatePerson(Person uPerson, Long person_id, Long city_id) throws NoSuchCityException, NoSuchPersonException {
//        City city = cityRepository.findOne(city_id);//1.5.9
        City city = cityRepository.findById(city_id).get();//2.0.0.M7
        if (city_id > 0) {
            if (city == null) throw new NoSuchCityException();
        }
//        Person person = personRepository.findOne(person_id);//1.5.9
        Person person = personRepository.findById(person_id).get();//2.0.0.M7
        if (person == null) throw new NoSuchPersonException();
        //update
        person.setSurname(uPerson.getSurname());
        person.setName(uPerson.getName());
        person.setEmail(uPerson.getEmail());
        if (city_id > 0) person.setCity(city);
        else person.setCity(null);
        person.setStreet(uPerson.getStreet());
        person.setApartment(uPerson.getApartment());
        personRepository.save(person);
    }

    @Transactional
    public void deletePerson(Long person_id) throws NoSuchPersonException, ExistsBooksForPersonException {
//        Person person = personRepository.findOne(person_id);//1.5.9
        Person person = personRepository.findById(person_id).get();//2.0.0.M7
        if (person == null) throw new NoSuchPersonException();
        if (person.getGadgets().size() != 0) throw new ExistsBooksForPersonException();
        personRepository.delete(person);
    }

    @Transactional
    public void addGadgetForPerson(Long person_id, Long gadget_id)
            throws NoSuchPersonException, NoSuchBookException, AlreadyExistsBookInPersonException, BookAbsentException {
//        Person person = personRepository.findOne(person_id);//1.5.9
        Person person = personRepository.findById(person_id).get();//2.0.0.M7
        if (person == null) throw new NoSuchPersonException();
//        Gadget gadget = bookRepository.findOne(gadget_id);//1.5.9
        Gadget gadget = gagdetRepository.findById(gadget_id).get();//2.0.0.M7
        if (gadget == null) throw new NoSuchBookException();
        if (person.getGadgets().contains(gadget) == true) throw new AlreadyExistsBookInPersonException();
        if (gadget.getAmount() <= gadget.getPersons().size()) throw new BookAbsentException();
        person.getGadgets().add(gadget);
        personRepository.save(person);
    }

    @Transactional
    public void removeGadgetForPerson(Long person_id, Long gadget_id)
            throws NoSuchPersonException, NoSuchBookException, PersonHasNotBookException {
//        Person person = personRepository.findOne(person_id);//1.5.9
        Person person = personRepository.findById(person_id).get();//2.0.0.M7
        if (person == null) throw new NoSuchPersonException();
//        Gadget gadget = bookRepository.findOne(gadget_id);//1.5.9
        Gadget gadget = gagdetRepository.findById(gadget_id).get();//2.0.0.M7
        if (gadget == null) throw new NoSuchBookException();
        if (person.getGadgets().contains(gadget) == false) throw new PersonHasNotBookException();
        person.getGadgets().remove(gadget);
        personRepository.save(person);
    }
}
