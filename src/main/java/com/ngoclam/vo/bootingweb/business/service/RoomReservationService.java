package com.ngoclam.vo.bootingweb.business.service;

import com.ngoclam.vo.bootingweb.Utils.DateUtil;
import com.ngoclam.vo.bootingweb.business.domain.RoomReservation;
import com.ngoclam.vo.bootingweb.data.entity.Guest;
import com.ngoclam.vo.bootingweb.data.entity.Reservation;
import com.ngoclam.vo.bootingweb.data.entity.Room;
import com.ngoclam.vo.bootingweb.data.repository.GuestRepository;
import com.ngoclam.vo.bootingweb.data.repository.ReservationRepository;
import com.ngoclam.vo.bootingweb.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomReservationService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public RoomReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public RoomReservation getRoomReservationById(long id){
        RoomReservation roomReservation = new RoomReservation();
        Reservation reservation = this.reservationRepository.findById(id).get();
        Room room = this.roomRepository.findById(reservation.getRoomId()).get();
        Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
        roomReservation.setRoomId(room.getRoomId());
        roomReservation.setRoomName(room.getRoomName());
        roomReservation.setRoomNumber(room.getRoomNumber());
        roomReservation.setGuestId(guest.getGuestId());
        roomReservation.setFirstName(guest.getFirstName());
        roomReservation.setLastName(guest.getLastName());
        roomReservation.setPhone(guest.getPhoneNumber());
        roomReservation.setEmail(guest.getEmail());
        roomReservation.setDate(reservation.getDate());
        return roomReservation;
    }
    public List<RoomReservation> getRoomReservationByDate(String dateString){
        Date date = DateUtil.getDateForDateString(dateString);
        Map<Long, RoomReservation> roomReservations = new HashMap<>();
        Iterable<Room> rooms = this.roomRepository.findAll();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getRoomId());
            roomReservation.setRoomName(room.getRoomName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservations.put(room.getRoomId(),roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findReservationsByDate(date);
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservations.get(reservation.getRoomId());
            roomReservation.setDate(reservation.getDate());
            Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setGuestId(guest.getGuestId());
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setPhone(guest.getPhoneNumber());
            roomReservation.setEmail(guest.getEmail());
        });
        List<RoomReservation> roomReservationList = new ArrayList<>();
        roomReservations.values().forEach(roomReservationList::add);
        return roomReservationList;
    }

    public Optional<Reservation> getReservationById(long reservationId){
        Optional<Reservation> reservation = this.reservationRepository.findById(reservationId);
        return reservation;
    }

    public Optional<Reservation> getReservationByRoomIdAndDate(long roomId, Date date){
        Optional<Reservation> reservation = this.reservationRepository.findReservationByRoomIdAndDate(roomId, date);
        return reservation;
    }

    public RoomReservation insertReservation(Reservation reservation){
        reservation.setReservationId(null);
        Reservation _reservation = this.reservationRepository.save(reservation);
        RoomReservation roomReservation = new RoomReservation();
        Room room = this.roomRepository.findById(_reservation.getRoomId()).get();
        Guest guest = this.guestRepository.findById(_reservation.getGuestId()).get();
        roomReservation.setRoomId(room.getRoomId());
        roomReservation.setRoomName(room.getRoomName());
        roomReservation.setRoomNumber(room.getRoomNumber());
        roomReservation.setGuestId(guest.getGuestId());
        roomReservation.setFirstName(guest.getFirstName());
        roomReservation.setLastName(guest.getLastName());
        roomReservation.setPhone(guest.getPhoneNumber());
        roomReservation.setEmail(guest.getEmail());
        roomReservation.setDate(_reservation.getDate());

        return roomReservation;
    }

    public void deleteReservation(long id){
        this.reservationRepository.deleteById(id);
    }

}
