package bk.springRS.service;

import bk.springRS.entity.Contact;
import bk.springRS.exception.ContactNotFoundException;
import bk.springRS.repository.ContactRepository;
import bk.springRS.request.ContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository repository;

    public Contact findById(Long id) {
        Contact contact = repository.findOne(id);

        if(contact == null) {
            throw new ContactNotFoundException();
        }
        return contact;
    }

    public List<Contact> findAllContacts() {
        return repository.findAll();
    }

    public List<Contact> findContactsByName(String name) {
        return repository.findByName(name);
    }

    public List<Contact> findContactsBySurname(String surname) {
        return repository.findBySurname(surname);
    }

    public List<Contact> findByNameAndSurname(String name, String surname) {
        return repository.findByNameAndSurname(name, surname);
    }

    public void addContact(ContactRequest contactRequest) {
        Contact contact = new Contact();
        contact.setName(contactRequest.getName());
        contact.setSurname(contactRequest.getSurname());
        contact.setPhone(contactRequest.getPhone());
        repository.save(contact);
        repository.flush();
    }

    public void updateContact(Long id, ContactRequest contactRequest) {
        Contact contact = repository.findOne(id);
        contact.setName(contactRequest.getName());
        contact.setSurname(contactRequest.getSurname());
        contact.setPhone(contactRequest.getPhone());
        repository.save(contact);
        repository.flush();
    }

    public void deleteContact(Long id) {
        repository.delete(id);
    }
}
