package com.toolsapp.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toolsapp.model.AirCompressor;
import com.toolsapp.model.Digger;
import com.toolsapp.model.Drill;
import com.toolsapp.model.Generator;
import com.toolsapp.model.Gun;
import com.toolsapp.model.Hammer;
import com.toolsapp.model.Mixer;
import com.toolsapp.model.Pliers;
import com.toolsapp.model.PowerTool;
import com.toolsapp.model.Pruner;
import com.toolsapp.model.Rake;
import com.toolsapp.model.Ratchet;
import com.toolsapp.model.Sander;
import com.toolsapp.model.Saw;
import com.toolsapp.model.Screwdriver;
import com.toolsapp.model.Socket;
import com.toolsapp.model.StepLadder;
import com.toolsapp.model.StraightLadder;
import com.toolsapp.model.Striking;
import com.toolsapp.model.Tool;
import com.toolsapp.model.ToolReport;
import com.toolsapp.model.Wheelbarrow;
import com.toolsapp.repository.AccessoryRepository;
import com.toolsapp.repository.AirCompressorRepository;
import com.toolsapp.repository.DiggerRepository;
import com.toolsapp.repository.DrillRepository;
import com.toolsapp.repository.GeneratorRepository;
import com.toolsapp.repository.GunRepository;
import com.toolsapp.repository.HammerRepository;
import com.toolsapp.repository.MixerRepository;
import com.toolsapp.repository.PlierRepository;
import com.toolsapp.repository.PowertoolRepository;
import com.toolsapp.repository.PrunerRepository;
import com.toolsapp.repository.RakeRepository;
import com.toolsapp.repository.RatchetRepository;
import com.toolsapp.repository.ReservedToolsRepository;
import com.toolsapp.repository.SanderRepository;
import com.toolsapp.repository.SawRepository;
import com.toolsapp.repository.ScrewdriverRepository;
import com.toolsapp.repository.SocketRepository;
import com.toolsapp.repository.StepLadderRepository;
import com.toolsapp.repository.StraightLadderRepository;
import com.toolsapp.repository.StrikingRepository;
import com.toolsapp.repository.ToolRepository;
import com.toolsapp.repository.WheelbarrowRepository;

@Service
public class ToolService extends BaseService {

    @Autowired
    ToolRepository tools;
	
    @Autowired
    ScrewdriverRepository screws;

    @Autowired
    PowertoolRepository ptools;
    
    @Autowired
    SawRepository saws;
    
    @Autowired
    AccessoryRepository accessories;
    
    @Autowired
    ReservedToolsRepository reservedTools;
    
    @Autowired
    StepLadderRepository stepLadders;
    
    @Autowired
    StraightLadderRepository straightLadders;

    @Autowired
    StrikingRepository strikers;

    @Autowired
    DiggerRepository diggers;

    @Autowired
    RakeRepository rakes;

    @Autowired
    WheelbarrowRepository wheelbarrows;

    @Autowired
    PrunerRepository pruners;

    @Autowired
    SocketRepository sockets;

    @Autowired
    RatchetRepository ratchets;

    @Autowired
    PlierRepository pliers;
    
    @Autowired
    GunRepository guns;
    
    @Autowired
    HammerRepository hammers;
   
    @Autowired
    DrillRepository drills;

    @Autowired
    SanderRepository sanders;

    @Autowired
    AirCompressorRepository airCompressors;

    @Autowired
    MixerRepository mixers;

    @Autowired
    GeneratorRepository generators;

    
    public List<Tool> searchToolsAvailableForRent ( String type, 
					    							String subType, 
					    							String subOptionKeyword, 
					    							String powerSource,
					    							java.sql.Date startDate,
					    							java.sql.Date endDate) {
					    	
    	List<Integer> toolIds = reservedTools.searchToolsAvailableForRent(type, subType, subOptionKeyword, powerSource, startDate, endDate, 0);
    	List<Tool> tools = new ArrayList<Tool>();
    	for (Integer id : toolIds) {
    		tools.add(get(id));
    	}
    	return tools;
    }
    
    public Tool add(Tool t) {
    	String toolSubType = t.getSubType().toLowerCase();
		switch (toolSubType) {
			case "screwdriver":
				t = screws.add((Screwdriver)t);
				break;
			case "saw":
				t = saws.add((Saw)t);
				break;
			case "drill":
				t = drills.add((Drill)t);
				break;
			case "sander":
				t = sanders.add((Sander)t);
				break;
			case "aircompressor":
				t = airCompressors.add((AirCompressor)t);
				break;
			case "mixer":
				t = mixers.add((Mixer)t);
				break;
			case "generator":
				t = generators.add((Generator)t);
				break;				
			case "step":
				t = stepLadders.add((StepLadder)t);
				break;
			case "straight":
				t = straightLadders.add((StraightLadder)t);
				break;
			case "digger":
				t = diggers.add((Digger)t);
				break;
			case "pruner":
				t = pruners.add((Pruner)t);
				break;
			case "rake":
				t = rakes.add((Rake)t);
				break;
			case "wheelbarrow":
				t = wheelbarrows.add((Wheelbarrow)t);
				break;
			case "striking":
				t = strikers.add((Striking)t);
				break;
			case "socket":
				t = sockets.add((Socket)t);
				break;
			case "ratchet":
				t = ratchets.add((Ratchet)t);
				break;
			case "pliers":
				t = pliers.add((Pliers)t);
				break;
			case "gun":
				t = guns.add((Gun)t);
				break;
			case "hammer":
				t = hammers.add((Hammer)t);
				break;
			default:
				log.error("Unrecognized tool subtype " + t.getSubType());
				return null;
		}    	
		
		if (isElectric(t.getPowerSource()) && t instanceof PowerTool) {
			accessories.add((PowerTool)t);
		}
		
		return t;		
    }
    
    public Tool get(int toolNumber) {
    	Tool t = tools.get(toolNumber);
    	if (t!= null) {
        	return getToolBySubType(t);
    	} else {
    		return null;
    	}
    }
    
    private Tool getToolBySubType(Tool t) {
    	
    	String subtype = t.getSubType().toLowerCase();
    	
    	if (isElectric(t.getPowerSource())) {
    		PowerTool pt = ptools.get(t);
    		
        	switch (subtype) {
	    		case "saw":
	    			pt = saws.get(pt);
	    			break;
	    		case "drill":
	    			pt = drills.get(pt);
	    			break;
	    		case "sander":
	    			pt = sanders.get(pt);
	    			break;
	    		case "aircompressor":
	    			pt = airCompressors.get(pt);
	    			break;
	    		case "mixer":
	    			pt = mixers.get(pt);
	    			break;
	    		case "generator":
	    			pt = generators.get(pt);
	    			break;
	    		default:
	    			return null;
        	} 
        	
        	// Get accessories
        	pt.setAccessories(accessories.get(t.getToolNumber()));
        	
        	return pt;
        	
    	} else {

    		switch (subtype) {
	    		case "screwdriver":
	    			return screws.get(t);
	    		case "step":
	    			return stepLadders.get(t);
	    		case "straight":
	    			return straightLadders.get(t);
	    		case "digger":
	    			return diggers.get(t);
	    		case "pruner":
	    			return pruners.get(t);
	    		case "rake":
	    			return rakes.get(t);
	    		case "wheelbarrow":
	    			return wheelbarrows.get(t);
	    		case "striking":
	    			return strikers.get(t);
	    		case "socket":
	    			return sockets.get(t);
	    		case "ratchet":
	    			return ratchets.get(t);
	    		case "pliers":
	    			return pliers.get(t);
	    		case "gun":
	    			return guns.get(t);
	    		case "hammer":
	    			return hammers.get(t);
	    		default:
	    			return null;
    		}    	    	
    	}
    }
    
    private boolean isElectric(String powerSource) {
    	String ps = powerSource.toLowerCase();
    	return ps.contains("electric") || ps.contains("ac") || ps.contains("dc") || ps.contains("cordless");
    }
    
    public List<ToolReport> report() {
    	List<ToolReport> tools = reservedTools.report();
    	
    	for (ToolReport tr : tools) {
    		tr.setTool(getToolBySubType(tr.getTool()));
    	}
    	
    	return tools;
    }
    
}
