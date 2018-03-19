package com.toolsapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toolsapp.model.Clerk;
import com.toolsapp.model.ClerkReport;
import com.toolsapp.repository.ClerkRepository;

@Service
public class ClerkService extends BaseService {

    @Autowired
    ClerkRepository clerk;
	
    public void add(Clerk c) {
    	clerk.add(c);
    }
    
    public Clerk get(String username) {
    	return clerk.get(username);
    }
    
    public List<ClerkReport> report() {
    	return clerk.report();
    }
	
}
