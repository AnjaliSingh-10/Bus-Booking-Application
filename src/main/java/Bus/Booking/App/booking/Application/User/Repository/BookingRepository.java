package com.RedBus.User.Repository;


import com.RedBus.User.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Dictionary;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,String> {



}

