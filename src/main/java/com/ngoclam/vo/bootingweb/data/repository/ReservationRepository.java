package com.ngoclam.vo.bootingweb.data.repository;

import com.ngoclam.vo.bootingweb.data.entity.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    Iterable<Reservation> findReservationsByDate(Date date);
    Optional<Reservation> findReservationByRoomIdAndDate(long id, Date date);
}
