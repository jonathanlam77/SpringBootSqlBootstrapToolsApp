package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Tool;
import com.toolsapp.model.Wheelbarrow;

@Repository
@DependsOn(value = { "toolRepository" })
public class WheelbarrowRepository extends SqlRepository {

    @Autowired
    ToolRepository toolRepository;
	
	@Override
	protected String getCreateTableSql() {
    	return new StringBuffer()	
		.append("CREATE TABLE ")
		.append(getTableName())
		.append("(")
		.append("tool_number 	INT(8) NOT NULL PRIMARY KEY,")
		.append("handle_material	VARCHAR(64),")
		.append("bin_volume		FLOAT(10,2),")
		.append("bin_material	VARCHAR(64),")
		.append("wheel_count		INT(10),")
		.append("CONSTRAINT fk_Wheelbarrow_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number)")
		.append(");")
		.toString();		
	}

	@Override
	protected String getTableName() {
		return "Wheelbarrow";
	}
	
    public Wheelbarrow get(Tool t) {
    	
    	String sql = String.format("SELECT handle_material, bin_volume, bin_material, wheel_count FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Wheelbarrow> l = query(sql, (rs, rowNum) -> {
    													Wheelbarrow p = new Wheelbarrow(t);
    													p.setHandleMaterial(rs.getString("handle_material"));
    													p.setBinVolume(rs.getDouble("bin_volume"));
    													p.setBinMaterial(rs.getString("bin_material"));
    													p.setWheelCount(rs.getInt("wheel_count"));
 										     			return p;
									        		});
        
    	return (l.size() > 0) ? l.get(0) : null;
    }
	
	
    public Wheelbarrow add(Wheelbarrow s) {
    	
    	s = (Wheelbarrow)toolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, handle_material, bin_volume,  bin_material, wheel_count) " +
    								"VALUES (%s, %s, %s, %s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								toSqlStr(s.getHandleMaterial()),
    								s.getBinVolume(),
    								toSqlStr(s.getBinMaterial()),
    								s.getWheelCount()
    								);
    	
    	execute(sql.toString()); 

    	return s;
    }
	
	
}
