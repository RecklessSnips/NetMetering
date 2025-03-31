package com.example.netmetering;

import com.example.netmetering.entities.Contact;
import com.example.netmetering.entities.User;
import com.example.netmetering.repository.ContactsRepository;
import com.example.netmetering.repository.UserRepository;
import com.example.netmetering.service.ContactService;
import com.example.netmetering.service.TransactionService;
import com.example.netmetering.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ContactTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactsRepository contactsRepository;

    @Test
    void testAddContact(){
        User user1 = userService.findUserByEmail("qiao0174@gmail.com");
        User user2 = userService.findUserByEmail("ahsoka@gmail.com");
        contactService.addContact(user1, user2);
    }

    @Test
    void checkDuplicateContact(){
        // This test should fail
        User user1 = userService.findUserByEmail("qiao0174@gmail.com");
        // This should not be in the db as well
        User user2 = userService.findUserByEmail("loblaws@gmail.com");
        contactService.addContact(user1, user2);
    }

    @Test
    void checkDateTimeZone(){
        User lloyd = userService.findUserByEmail("lloyddonegan@gmail.com");
        User user2 = userService.findUserByEmail("ahsoka@gmail.com");
        Optional<Contact> contact = contactsRepository.findById(
                new Contact.ContactId(
                        lloyd.getUserID(),
                        user2.getUserID()));

        System.out.println(contact.get().getDateAdded());
    }

    @Test
    void testGetAllContacts(){
        List<User> allContacts = contactService.getAllContacts();
    }
}
