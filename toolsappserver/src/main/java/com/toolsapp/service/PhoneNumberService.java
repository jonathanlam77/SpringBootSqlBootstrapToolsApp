package com.toolsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toolsapp.model.PhoneNumber;
import com.toolsapp.repository.PhoneNumberRepository;

@Service
public class PhoneNumberService extends BaseService {

    @Autowired
    PhoneNumberRepository phone;
    
    public PhoneNumber add(PhoneNumber num) {
    	phone.add(num);
    	return num;
    }
    
    public PhoneNumber get(String phoneNumberId) {
    	return phone.get(Long.parseLong(phoneNumberId));
    }

    public PhoneNumber get(int areacode, int number, int ext) {
    	return phone.get(areacode, number, ext);
    }
    
}
