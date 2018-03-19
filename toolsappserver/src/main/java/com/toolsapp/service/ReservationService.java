package com.toolsapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toolsapp.model.CreditCard;
import com.toolsapp.model.Reservation;
import com.toolsapp.model.ToolReservation;
import com.toolsapp.repository.ReservationRepository;
import com.toolsapp.repository.ReservedToolsRepository;

@Service
public class ReservationService extends BaseService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservedToolsRepository reservedToolsRepository;
    
    public void add(Reservation r) {
    	reservationRepository.add(r);
    }
    
    public void add(ToolReservation tr) {
    	reservedToolsRepository.reserve(tr);
    }
    
    public Reservation get(int reservationId) {
    	return reservationRepository.get(reservationId);
    }

    public List<ToolReservation> getAll(String customer, boolean pickup, boolean dropoff) {
    	return reservedToolsRepository.getAll(customer, pickup, dropoff);
    }
    
    public void pickup(int reservationId, String clerk) {
    	reservationRepository.pickup(reservationId, clerk);
    }
    
    public void dropoff(int reservationId, String clerk) {
    	reservationRepository.dropoff(reservationId, clerk);
    }
    
    public void updateCreditCard(int reservationId, CreditCard cc) {
    	reservationRepository.updateCreditCard(reservationId, cc);
    }
    
}
