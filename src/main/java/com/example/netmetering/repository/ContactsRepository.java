package com.example.netmetering.repository;

import com.example.netmetering.entities.Contact;
import com.example.netmetering.entities.Contact.ContactId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactsRepository extends CrudRepository<Contact, ContactId> {

    // Check if a user has already have this contact
    boolean existsByUser_UserIDAndContact_UserID(String userID, String contactID);

    // Find all contacts by a userID
    List<Contact> findContactsByUser_UserID(String userID);
}
