package com.ngoclam.vo.bootingweb.business.service;

import com.ngoclam.vo.bootingweb.data.entity.Guest;
import com.ngoclam.vo.bootingweb.data.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Guest> getGuests(){
        Iterable<Guest> guests = this.guestRepository.findAll();
        List<Guest> guestList = new ArrayList<>();
        guests.forEach(guestList::add);
        guestList.sort(new Comparator<Guest>() {
            @Override
            public int compare(Guest o1, Guest o2) {
                if(o1.getLastName().equals(o2.getLastName())){
                    return o1.getFirstName().compareTo(o2.getFirstName());
                }
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        return guestList;
    }

    public Optional<Guest> getGuestById(long id){
        Optional<Guest> guest = this.guestRepository.findById(id);
        return guest;
    }

    public List<Guest> getGuestsByFirstNameAndLastName(String firstName, String lastName){
        Iterable<Guest> guests = this.guestRepository.findGuestsByFirstNameAndLastName(firstName, lastName);
        List<Guest> guestList = new ArrayList<>();
        guests.forEach(guestList::add);
        guestList.sort(new Comparator<Guest>() {
            @Override
            public int compare(Guest o1, Guest o2) {
                if(o1.getLastName().equals(o2.getLastName())){
                    return o1.getFirstName().compareTo(o2.getFirstName());
                }
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        return guestList;
    }

    public List<Guest> getGuestsByFirstName(String firstName){
        Iterable<Guest> guests = this.guestRepository.findGuestsByFirstName(firstName);
        List<Guest> guestList = new ArrayList<>();
        guests.forEach(guestList::add);
        guestList.sort(new Comparator<Guest>() {
            @Override
            public int compare(Guest o1, Guest o2) {
                if(o1.getFirstName().equals(o2.getFirstName())){
                    return o1.getLastName().compareTo(o2.getLastName());
                }
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
        });
        return guestList;
    }

    public List<Guest> getGuestsByLastName(String lastName){
        Iterable<Guest> guests = this.guestRepository.findGuestsByLastName(lastName);
        List<Guest> guestList = new ArrayList<>();
        guests.forEach(guestList::add);
        guestList.sort(new Comparator<Guest>() {
            @Override
            public int compare(Guest o1, Guest o2) {
                if(o1.getLastName().equals(o2.getLastName()))
                {
                    return o1.getFirstName().compareTo(o2.getFirstName());
                }
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        return guestList;
    }

    public List<Guest> getGuestByEmail(String email){
        Iterable<Guest> guests = this.guestRepository.findGuestByEmail(email);
        List<Guest> guestList = new ArrayList<>();
        guests.forEach(guestList::add);
        guestList.sort(new Comparator<Guest>() {
            @Override
            public int compare(Guest o1, Guest o2) {
                if(o1.getLastName().equals(o2.getLastName())){
                    return o1.getFirstName().compareTo(o2.getFirstName());
                }
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        return guestList;
    }

    public List<Guest> getGuestByPhoneNumber(String phoneNumber){
        Iterable<Guest> guests = this.guestRepository.findGuestByPhoneNumberContaining(phoneNumber);
        List<Guest> guestList = new ArrayList<>();
        guests.forEach(guestList::add);
        guestList.sort(new Comparator<Guest>() {
            @Override
            public int compare(Guest o1, Guest o2) {
                if(o1.getLastName().equals(o2.getFirstName())){
                    return o1.getFirstName().compareTo(o2.getFirstName());
                }
                return o1.getLastName().compareTo(o2.getLastName());
            }
        });
        return guestList;
    }

    public Guest insertGuest(Guest guest){
        guest.setGuestId(null);
        Guest _guest = this.guestRepository.save(guest);
        return _guest;
    }

    public void updateGuest(long id, Guest guest){
        Guest _guest = this.guestRepository.findById(id).get();
        if(guest.getFirstName()!=null)
            _guest.setFirstName(guest.getFirstName());
        if(guest.getLastName()!=null)
            _guest.setLastName(guest.getLastName());
        if(guest.getEmail()!=null)
            _guest.setEmail(guest.getEmail());
        if(guest.getPhoneNumber()!=null)
            _guest.setPhoneNumber(guest.getPhoneNumber());
        if(guest.getAddress()!=null)
            _guest.setAddress(guest.getAddress());
        if(guest.getState()!=null)
            _guest.setState(guest.getState());
        if(guest.getCountry()!=null)
            _guest.setCountry(guest.getCountry());
        this.guestRepository.save(_guest);

    }

    public void deleteGuest(long id){ this.guestRepository.deleteById(id);}
}
