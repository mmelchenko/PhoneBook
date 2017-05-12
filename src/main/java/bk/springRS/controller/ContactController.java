package bk.springRS.controller;

import bk.springRS.exception.BadRequestException;
import bk.springRS.exception.ContactNotFoundException;
import bk.springRS.request.ContactRequest;
import bk.springRS.entity.Contact;
import bk.springRS.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService service;

    @RequestMapping(path = "/update_or_delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public Contact updateOrDelete(@Param("id") Long id, @Param("delete") boolean delete, @RequestBody ContactRequest contactRequest) {
        if(contactRequest.isAnyEmptyProperties()) {
            throw new BadRequestException("Bad request.");
        }

        Contact contact = service.findById(id);

        if(contact == null) {
            throw new ContactNotFoundException("Contact " + contactRequest + " was not found.");
        }

        if(delete) {
            service.deleteContact(id);
            return contact;
        }

        service.updateContact(id, contactRequest);
        Contact contactAfterUpdate = service.findById(id);
        return contactAfterUpdate;
    }

    @RequestMapping(path = "/create_or_update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public Contact createOrUpdate(@Param("update") String update, @RequestBody ContactRequest contactRequest) {

        if(contactRequest.isAnyEmptyProperties()) {
            throw new BadRequestException("Bad request.");
        }

        ArrayList<Contact> contacts = new ArrayList<>(service.findByNameAndSurname(contactRequest.getName(), contactRequest.getSurname()));
        if (!contacts.isEmpty() && update.toLowerCase().equals("true")) {
            Contact contact;
            try {
                contact = contacts.get(contacts.size() - 1);
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                throw new ContactNotFoundException("Contact " + contactRequest + " was not found.");
            }

            long contactId = contact.getId();
            service.updateContact(contactId, contactRequest);
            Contact contactAfter = service.findById(contactId);
            return contactAfter;
        }

        return service.addContact(contactRequest);
    }

    @RequestMapping(path = "/create_or_delete", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public Contact createOrDelete(@Param("delete") String delete, @RequestBody ContactRequest contactRequest) {
        if(contactRequest.isAnyEmptyProperties()) {
            throw new BadRequestException("Bad request.");
        }

        ArrayList<Contact> contacts = new ArrayList<>(service.findByNameAndSurname(contactRequest.getName(),contactRequest.getSurname()));

        Contact contact = contacts.get(0);

        if(delete.toLowerCase().equals("true")) {
            if(!contacts.isEmpty()) {
                service.deleteContact(contact.getId());
                return contact;
            } else
                throw new ContactNotFoundException("Contact " + contactRequest + " was not found.");
        }

        service.addContact(contactRequest);
        return contact;
    }

    @RequestMapping(value = "/all_contacts", method = RequestMethod.GET)
    public List<Contact> getAllContacts() {
        return service.findAllContacts();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insert() {
        ContactRequest first = new ContactRequest("Jack", "Bauer", "111");
        ContactRequest second = new ContactRequest("Chloe", "O'Brian", "222");
        ContactRequest third = new ContactRequest("Kim", "Bauer", "333");
        ContactRequest fourth = new ContactRequest("David", "Palmer", "444");
        ContactRequest fifth = new ContactRequest("Michelle", "Dessler", "555");

        service.addContact(first);
        service.addContact(second);
        service.addContact(third);
        service.addContact(fourth);
        service.addContact(fifth);
    }
}
