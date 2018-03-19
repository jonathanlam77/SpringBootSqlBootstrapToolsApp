package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Mixer;
import com.toolsapp.model.PowerTool;

@Repository
@DependsOn(value = { "powertoolRepository", "toolRepository" })
public class MixerRepository extends SqlRepository {

    @Autowired	
    PowertoolRepository powertoolRepository;	
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
    			.append("CREATE TABLE ")
    			.append(getTableName())
    			.append("(")
    			.append("tool_number	INT(8) PRIMARY KEY,")
    			.append("motor_rating	INT(6) NOT NULL,")
    			.append("drum_size		FLOAT(10,2) NOT NULL,")
    			.append("CONSTRAINT fk_Mixer_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES PowerTool(tool_number)")
    			.append(");")
    			.toString();
	}
    
	@Override
	protected String getTableName() {
		return "Mixer";
	}
	
	
    public Mixer get(PowerTool t) {
    	
    	String sql = String.format("SELECT motor_rating, drum_size FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Mixer> tools = query(sql, (rs, rowNum) -> {
    									Mixer s = new Mixer(t);
    									s.setMotorRating(rs.getInt("motor_rating"));
    									s.setDrumSize(rs.getDouble("drum_size"));
						     			return s;
					        		});
        
    	return (tools.size() > 0) ? tools.get(0) : null;
    }    
    
    public Mixer add(Mixer s) {
    	
    	s = (Mixer)powertoolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, motor_rating, drum_size) " +
    								"VALUES (%s, %s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								s.getMotorRating(),
    								s.getDrumSize());
    	
    	execute(sql.toString()); 

    	return s;
    }
    
    
	
}
