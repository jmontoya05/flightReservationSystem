package com.makaia.flightReservation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "passengers")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private Integer passengerId;
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, length = 15, unique = true)
    private String passport;
    @Column(nullable = false, length = 20)
    private String nationality;
    @Column(length = 30, unique = true)
    private String email;
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;
    @Column(name = "emergency_contact", nullable = false, length = 30)
    private String emergencyContact;
    @Column(name = "contact_phone_number", nullable = false, length = 15)
    private String contactPhoneNumber;
    @OneToMany(mappedBy = "passenger")
    private List<Reservation> reservations;

    public Passenger() {
    }

    public Passenger(Integer passengerId, String firstName, String lastName, String passport, String nationality, String email, String phoneNumber, String emergencyContact, String contactPhoneNumber, List<Reservation> reservations) {
        this.passengerId = passengerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passport = passport;
        this.nationality = nationality;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.emergencyContact = emergencyContact;
        this.contactPhoneNumber = contactPhoneNumber;
        this.reservations = reservations;
    }

    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
