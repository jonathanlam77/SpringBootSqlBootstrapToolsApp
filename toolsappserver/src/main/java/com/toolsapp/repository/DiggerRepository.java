package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Digger;
import com.toolsapp.model.Tool;

@Repository
@DependsOn(value = { "toolRepository" })
public class DiggerRepository extends SqlRepository {

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
		.append("blade_width		FLOAT(10,2),")
		.append("blade_length	FLOAT(10,2),")
		.append("CONSTRAINT fk_Digger_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number)")
		.append(");")
		.toString();		
	}

	@Override
	protected String getTableName() {
		return "Digger";
	}

    public Digger get(Tool t) {
    	
    	String sql = String.format("SELECT handle_material, blade_width, blade_length FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Digger> l = query(sql, (rs, rowNum) -> {
    													Digger p = new Digger(t);
    													p.setHandleMaterial(rs.getString("handle_material"));
    													p.setBladeWidth(rs.getDouble("blade_width"));
    													p.setBladeLength(rs.getDouble("blade_length"));
										     			return p;
									        		});
        
    	return (l.size() > 0) ? l.get(0) : null;
    }
	
	
    public Digger add(Digger s) {
    	
    	s = (Digger)toolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, handle_material, blade_width, blade_length) " +
    								"VALUES (%s, %s, %s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								toSqlStr(s.getHandleMaterial()),
    								s.getBladeWidth(),
    								s.getBladeLength()
    								);
    	
    	execute(sql.toString()); 

    	return s;
    }

}
