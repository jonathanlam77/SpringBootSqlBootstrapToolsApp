package com.toolsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toolsapp.model.CreditCard;
import com.toolsapp.repository.CreditCardRepository;

@Service
public class CreditCardService extends BaseService {

    @Autowired
    CreditCardRepository ccRepo;
    
    public void add(CreditCard c) {
    	ccRepo.add(c);
    }
    
    public CreditCard get(String number) {
    	return ccRepo.get(Long.parseLong(number));
    }
	
}
