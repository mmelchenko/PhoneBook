package bk.springRS.controller;

import bk.springRS.exception.BadRequestException;
import bk.springRS.exception.ContactNotFoundException;
import bk.springRS.request.ContactRequest;
import bk.springRS.entity.Contact;
import bk.springRS.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService service;

    @RequestMapping(path = "/update_or_delete", method = RequestMethod.POST)
    public String updateOrDelete(@Param("id") Long id, @Param("delete") boolean delete, @RequestBody ContactRequest contactRequest) {
        if(contactRequest.isAnyEmptyProperties()) {
            throw new BadRequestException("Bad request.");
        }

        Contact contact = service.findById(id);

        if(contact == null) {
            throw new ContactNotFoundException("Contact " + contactRequest + " was not found.");
        }

        if(delete) {
            service.deleteContact(id);
            return "Contact " + contact + " was deleted.";
        }

        Contact contactBeforeUpdate = new Contact();
        contactBeforeUpdate.setName(contact.getName());
        contactBeforeUpdate.setSurname(contact.getSurname());
        contactBeforeUpdate.setPhone(contact.getPhone());
        service.updateContact(id, contactRequest);

        Contact contactAfterUpdate = service.findById(id);
        return "Contact " + contactBeforeUpdate + " was updated. \n " +
                "Now it looks like that " + contactAfterUpdate + ".";
    }

    @RequestMapping(path = "/create_or_update", method = RequestMethod.POST)
    public String createOrUpdate(@Param("update") String update, @RequestBody ContactRequest contactRequest) {
        if(contactRequest.isAnyEmptyProperties()) {
            throw new BadRequestException("Bad request.");
        }
        System.out.println("IN THE CONTROLLERS METHOD!!!");
        ArrayList<Contact> contacts = new ArrayList<>(service.findByNameAndSurname(contactRequest.getName(),contactRequest.getSurname()));

        if(update.toLowerCase().equals("true")) {
            if(!contacts.isEmpty()) {
                Contact contact = contacts.get(contacts.size()-1);

                Contact contactBefore = new Contact();
                contactBefore.setId(contact.getId());
                contactBefore.setName(contact.getName());
                contactBefore.setSurname(contact.getSurname());
                contactBefore.setPhone(contact.getPhone());

                service.updateContact(contact.getId(), contactRequest);
                Contact contactAfter = service.findById(contact.getId());
                return "Contact " + contactBefore + " was updated. \n " +
                        "Now it looks like that " + contactAfter + ".";
            } else throw new ContactNotFoundException("Contact " + contactRequest + " was not found.");
        }
        System.out.println("IN THE CONTROLLERS METHOD!!!(THE END))");
        service.addContact(contactRequest);
        System.out.println("Contact " + contactRequest + " was added to the phone-book.");
        return "Contact " + contactRequest + " was added to the phone-book.";
    }

    @RequestMapping(path = "/create_or_delete", method = RequestMethod.POST)
    public @ResponseBody String createOrDelete(@Param("delete") String delete, @RequestBody ContactRequest contactRequest) {
        if(contactRequest.isAnyEmptyProperties()) {
            throw new BadRequestException("Bad request.");
        }

        ArrayList<Contact> contacts = new ArrayList<>(service.findByNameAndSurname(contactRequest.getName(),contactRequest.getSurname()));

        if(delete.toLowerCase().equals("true")) {
            if(!contacts.isEmpty()) {
                Contact contact = contacts.get(0);
                service.deleteContact(contact.getId());
                return "Contact " + contact + " was deleted.";
            } else throw new ContactNotFoundException("Contact " + contactRequest + " was not found.");
        }

        service.addContact(contactRequest);
        return "Contact " + contactRequest + " was added to the phone-book.";
    }
/*
    @RequestMapping(value = "/all_contacts", method = RequestMethod.GET)
    public ResponseEntity<List<Contact>> getAllContacts() throws IOException, ServletException {
        System.out.println("IN THE CONTROLLERS METHOD!!!(THE END))");
        ArrayList<Contact> allContacts = new ArrayList<>(service.findAllContacts());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type:application/json", "application/json");
        ResponseEntity<List<Contact>> responseEntity = new ResponseEntity(allContacts, headers, HttpStatus.OK);
        System.out.println("IN THE CONTROLLERS METHOD!!!(THE END))");
        return responseEntity;
    }*/

    @ResponseBody
    @RequestMapping(value = "/all_contacts", method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity<String> getAllContacts() {
        ArrayList<Contact> allContacts = new ArrayList<>(service.findAllContacts());

        StringBuilder response = new StringBuilder();

        for (Contact allContact : allContacts) {
            response.append(allContact.toString()).append("\n");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>(response.toString(), headers, HttpStatus.OK);
    }
}
