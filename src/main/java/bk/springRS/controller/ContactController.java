package bk.springRS.controller;

import bk.springRS.request.ContactRequest;
import bk.springRS.entity.Contact;
import bk.springRS.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private ContactRepository repository;

    @Autowired
    public ContactController(ContactRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Contact> findAllContacts() {
        return repository.findAll();
    }

    @RequestMapping(path = "/find_by_name", method = RequestMethod.GET)
    public List<Contact> findContactsByName(@Param("name") String name) {
        return repository.findByName(name);
    }

    @RequestMapping(path = "/find_by_surname", method = RequestMethod.GET)
    public List<Contact> findContactsBySurname(@Param("surname") String surname) {
        return repository.findBySurname(surname);
    }

    @RequestMapping(path = "/find_by_name_and_surname", method = RequestMethod.GET)
    public Contact findByNameAndSurname(@Param("name") String name, @Param("surname") String surname) {
        return repository.findByNameAndSurname(name, surname);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addContact(@RequestBody ContactRequest contactRequest) {
        Contact contact = new Contact();
        contact.setName(contactRequest.getName());
        contact.setSurname(contactRequest.getSurname());
        contact.setPhone(contactRequest.getPhone());
        repository.save(contact);
        repository.flush();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateContact(@Param("id") Long id, @RequestBody ContactRequest contactRequest) {
        Contact contact = repository.findOne(id);
        contact.setName(contactRequest.getName());
        contact.setSurname(contactRequest.getSurname());
        contact.setPhone(contactRequest.getPhone());
        repository.save(contact);
        repository.flush();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteContact(@Param("id")  Long id) {
        repository.delete(id);
    }
}
