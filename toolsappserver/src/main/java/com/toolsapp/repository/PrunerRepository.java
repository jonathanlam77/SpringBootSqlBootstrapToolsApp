package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Pruner;
import com.toolsapp.model.Tool;

@Repository
@DependsOn(value = { "toolRepository" })
public class PrunerRepository extends SqlRepository {

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
		.append("blade_material	VARCHAR(64),")
		.append("blade_length	FLOAT(10,2),")
		.append("CONSTRAINT fk_Pruner_tool_number_PowerTool_tool_number FOREIGN KEY (tool_number) REFERENCES Tool(tool_number)")
		.append(");")
		.toString();		
	}

	@Override
	protected String getTableName() {
		return "Pruner";
	}

    public Pruner get(Tool t) {
    	
    	String sql = String.format("SELECT handle_material, blade_material, blade_length FROM %s WHERE tool_number=%s", getTableName(), t.getToolNumber());
    	
    	List<Pruner> l = query(sql, (rs, rowNum) -> {
    													Pruner p = new Pruner(t);
    													p.setHandleMaterial(rs.getString("handle_material"));
    													p.setBladeMaterial(rs.getString("blade_material"));
    													p.setBladeLength(rs.getDouble("blade_length"));
										     			return p;
									        		});
        
    	return (l.size() > 0) ? l.get(0) : null;
    }
	
	
    public Pruner add(Pruner s) {
    	
    	s = (Pruner)toolRepository.add(s);
    	
    	String sql = String.format("INSERT INTO %s (tool_number, handle_material, blade_material, blade_length) " +
    								"VALUES (%s, %s, %s, %s)",
    								getTableName(),
    								s.getToolNumber(),
    								toSqlStr(s.getHandleMaterial()),
    								toSqlStr(s.getBladeMaterial()),
    								s.getBladeLength()
    								);
    	
    	execute(sql.toString()); 

    	return s;
    }

}
