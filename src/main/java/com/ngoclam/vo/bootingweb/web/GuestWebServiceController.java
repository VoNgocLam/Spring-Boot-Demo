package com.ngoclam.vo.bootingweb.web;

import com.ngoclam.vo.bootingweb.business.service.GuestService;
import com.ngoclam.vo.bootingweb.data.entity.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/guests")
public class GuestWebServiceController {
    private final GuestService guestService;

    @Autowired
    public GuestWebServiceController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public ResponseEntity<List<Guest>> getGuests(
            @RequestParam(value = "firstname", required = false) String firstName,
            @RequestParam(value = "lastname", required = false) String lastName,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email
    ){
        try{
            List<Guest> guests = new ArrayList<>();
            if(phone != null){
                guests = this.guestService.getGuestByPhoneNumber(phone);
            } else if (email != null){
                guests = this.guestService.getGuestByEmail(email);
            } else if(firstName!=null && lastName!=null){
                guests = this.guestService.getGuestsByFirstNameAndLastName(firstName, lastName);
            } else if (firstName != null){
                guests = this.guestService.getGuestsByFirstName(firstName);
            } else if (lastName != null){
                guests = this.guestService.getGuestsByLastName(lastName);
            } else
                guests = this.guestService.getGuests();

            if(guests.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(guests, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable long id){
        Optional<Guest> guest = this.guestService.getGuestById(id);
        if(guest.isPresent()){
            return new ResponseEntity<>(guest.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Guest> insertGuest(@RequestBody Guest guest){
        try{
            Guest guestData = new Guest();
            guestData = this.guestService.insertGuest(guest);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Guest","guests/"+guestData.getGuestId().toString());
            return new ResponseEntity<>(guestData, httpHeaders ,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(@PathVariable long id, @RequestBody Guest guest){
        try{
            Optional<Guest> guestData = this.guestService.getGuestById(id);
            if(guestData.isPresent()){
                this.guestService.updateGuest(id, guest);
                return new ResponseEntity<>(guestData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGuestById(@PathVariable long id){
        try{
            this.guestService.deleteGuest(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
