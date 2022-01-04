package com.ngoclam.vo.bootingweb.data.repository;

import com.ngoclam.vo.bootingweb.data.entity.Guest;
import org.springframework.data.repository.CrudRepository;

public interface GuestRepository extends CrudRepository<Guest, Long> {
    Iterable<Guest> findGuestsByFirstName(String firstName);
    Iterable<Guest> findGuestsByLastName(String lastName);
    Iterable<Guest> findGuestsByFirstNameAndLastName(String lastName, String firstName);
    Iterable<Guest> findGuestByEmail(String email);
    Iterable<Guest> findGuestByPhoneNumberContaining(String phoneNumber);
}
