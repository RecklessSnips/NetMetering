package com.example.netmetering.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "contacts")
public class Contact {

    @EmbeddedId
    private ContactId id;

    // Main user
    @ManyToOne
    @MapsId("userID")  // Map with ContactId's userID
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    private User user;

    // Contact
    @ManyToOne
    @MapsId("contactID")  // Map with ContactId's'contactID
    @JoinColumn(name = "contactID", referencedColumnName = "userID")
    private User contact;

    @Column(name = "dateAdded")
    private LocalDateTime dateAdded;

    public Contact() {
        this.dateAdded = LocalDateTime.now();
    }

    public Contact(User user, User contact) {
        this.user = user;
        this.contact = contact;
        this.id = new ContactId(user.getUserID(), contact.getUserID());
        this.dateAdded = LocalDateTime.now();
    }

    public ContactId getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getContact() {
        return contact;
    }

    public void setContact(User contact) {
        this.contact = contact;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }


    @Embeddable
    public static class ContactId implements Serializable {

        private String userID;
        private String contactID;

        public ContactId() {}

        public ContactId(String userID, String contactID) {
            this.userID = userID;
            this.contactID = contactID;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getContactID() {
            return contactID;
        }

        public void setContactID(String contactID) {
            this.contactID = contactID;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ContactId)) return false;
            ContactId that = (ContactId) o;
            return userID.equals(that.userID) && contactID.equals(that.contactID);
        }

        @Override
        public int hashCode() {
            return userID.hashCode() + contactID.hashCode();
        }
    }
}





