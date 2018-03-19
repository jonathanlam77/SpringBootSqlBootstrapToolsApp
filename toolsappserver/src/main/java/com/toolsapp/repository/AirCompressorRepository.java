package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.AirCompressor;
import com.toolsapp.model.PowerTool;

@Repository
@DependsOn(value = { "powertoolRepository", "toolRepository" })
public class AirCompressorRepository extends SqlRepository {

    @Autowired	
    PowertoolRepository powertoolRepository;	
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
    			.append("CREATE TABLE ")
    			.append(getTableName())
    			.append("(")
    			.append("tool_number	INT(8) PRIMARY KEY,")
    			.append("tank_size			INT(10) NOT NULL,")
    			.append("pressure_rating		FLOAT(10,2) NOT NULL,")
    			.append("CONSTRAINT fk_AirCompressor_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES PowerTool(tool_number)")
    			.append(");")
    			.toString();
	}
    
	@Override
	protected String getTableName() {
		return "AirCompressor";
	}
	
	
    public AirCompressor get(PowerTool t) {
    	
    	String sql = String.format("SELECT tank_size, pressure_rating FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<AirCompressor> tools = query(sql, (rs, rowNum) -> {
    									AirCompressor s = new AirCompressor(t);
    									s.setTankSize(rs.getInt("tank_size"));
    									s.setPressureRating(rs.getDouble("pressure_rating"));
						     			return s;
					        		});
        
    	return (tools.size() > 0) ? tools.get(0) : null;
    }    
    
    public AirCompressor add(AirCompressor s) {
    	
    	s = (AirCompressor)powertoolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, tank_size, pressure_rating) " +
    								"VALUES (%s, %s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								s.getTankSize(),
    								s.getPressureRating());
    	
    	execute(sql.toString()); 

    	return s;
    }
    
    
	
}
