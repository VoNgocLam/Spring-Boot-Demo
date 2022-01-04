package com.ngoclam.vo.bootingweb.web;

import com.ngoclam.vo.bootingweb.business.domain.RoomReservation;
import com.ngoclam.vo.bootingweb.business.service.RoomReservationService;
import com.ngoclam.vo.bootingweb.data.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class RoomReservationWebServiceController {
    private final RoomReservationService roomReservationService;

    @Autowired
    public RoomReservationWebServiceController(RoomReservationService roomReservationService) {
        this.roomReservationService = roomReservationService;
    }

    @GetMapping
    public ResponseEntity<List<RoomReservation>> getRoomReservations(
            @RequestParam(value = "date", required = false) String dateString){
        try{
            List<RoomReservation> roomReservations = new ArrayList<>();
            roomReservations = this.roomReservationService.getRoomReservationByDate(dateString);
            return new ResponseEntity<>(roomReservations, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<RoomReservation> insertRoomReservation(
            @RequestBody Reservation reservation
    ){
        try{
            Optional<Reservation> _reservation = this.roomReservationService.getReservationByRoomIdAndDate(reservation.getRoomId(),reservation.getDate());
            if(_reservation.isPresent()){
                RoomReservation roomReservation = this.roomReservationService.getRoomReservationById(_reservation.get().getReservationId());
                return new ResponseEntity<>(roomReservation, HttpStatus.FOUND);
            }else{
                RoomReservation roomReservation = this.roomReservationService.insertReservation(reservation);
                return new ResponseEntity<>(roomReservation,HttpStatus.CREATED);
            }

        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReservationById(@PathVariable long id){
        try{
            this.roomReservationService.deleteReservation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
