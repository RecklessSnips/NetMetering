package com.example.netmetering.service;

import com.example.netmetering.entities.Contact;
import com.example.netmetering.entities.User;
import com.example.netmetering.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactsRepository contactsRepository;

    @Autowired
    private UserService userService;

    public boolean addContact(User... users) {
        if (users == null || users.length == 0) {
            throw new IllegalArgumentException("At least one contact must be provided.");
        }
//        For testing only
//        User currentUser = userService.findUserByEmail("lloyddonegan@gmail.com");
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("User is not logged in.");
        }

        for (User user : users) {
            boolean exists = contactsRepository.existsByUser_UserIDAndContact_UserID(
                    currentUser.getUserID(), user.getUserID());

            if (exists) {
                throw new IllegalArgumentException("Contact " + user.getFull_name() + " already exists");
            }

            contactsRepository.save(new Contact(currentUser, user));
        }
        // All contacts are successfully added
        return true;
    }

    public boolean deleteContact(User... users){
        if (users == null || users.length == 0) {
            throw new IllegalArgumentException("At least one contact must be provided.");
        }
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("User is not logged in.");
        }

        for (User user : users) {
            boolean exists = contactsRepository.existsByUser_UserIDAndContact_UserID(
                    currentUser.getUserID(), user.getUserID());

            if (!exists) {
                throw new IllegalArgumentException("Cannot delete, contact " + user.getFull_name() + " doesn't exists");
            }

            contactsRepository.delete(new Contact(currentUser, user));
        }

        return true;
    }


    public List<User> getAllContacts(){
        User currentUser = userService.getCurrentUser();
//        For testing only
//        User currentUser = userService.findUserByEmail("lloyddonegan@gmail.com");
        if (currentUser != null){
            // Get all contacts
            List<Contact> list =
                    contactsRepository.findContactsByUser_UserID(currentUser.getUserID());
            if (list != null){
                // Change to User object
                List<User> contacts = list.stream()
                        .map(Contact::getContact)
                        .toList();
                return contacts;
            }else{
                return Collections.emptyList();
            }
        }else{
            throw new IllegalStateException("User haven't logged in!");
        }
    }
}
