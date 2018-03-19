package com.toolsapp.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.toolsapp.model.PhoneNumber;

@Repository
public class PhoneNumberRepository extends SqlRepository {

    protected String getCreateTableSql() {
    	return new StringBuffer()	
		.append("CREATE TABLE ")
		.append(getTableName())
		.append("(")
		.append("phone_number 	BIGINT(14),")
		.append("area_code		INT(3) NOT NULL,")
		.append("number			INT(7) NOT NULL,")
		.append("extension		INT(4),")
		.append("is_primary		TINYINT(1) NOT NULL,")
		.append("UNIQUE uk_PhoneNumber(area_code, number, extension),")
		.append("PRIMARY KEY (phone_number)")
		.append(");")
		.toString();			
    }
    
    protected String getTableName() {
    	return "PhoneNumber";
    }
    
	private String getPhoneNumberId(int areacode, int number, int ext) {
    	if (ext != 0) {
    		return Integer.toString(areacode)+Integer.toString(number)+Integer.toString(ext);
    	} else {
    		return Integer.toString(areacode)+Integer.toString(number);
    	}
	}
    
    public String add(PhoneNumber num) {
    	
    	String phoneNumberId= getPhoneNumberId(num.getAreaCode(), num.getNumber(), num.getExtension());
    	
    	StringBuffer sql = 
    			new StringBuffer()
    				.append("INSERT INTO ")
    				.append(getTableName());
    	
    	int extension = num.getExtension();
    	
    	if (extension != 0) {
			sql.append("(phone_number, area_code, number, extension, is_primary) ");
    	} else {
			sql.append("(phone_number, area_code, number, is_primary) ");
    	}

    	sql
    	.append("VALUES (")
    	.append(phoneNumberId)
    	.append(",")
    	.append(Integer.toString(num.getAreaCode()))
    	.append(",")
    	.append(Integer.toString(num.getNumber()))
    	.append(",");
    
    	if (extension != 0) {
			sql
			.append(Integer.toString(extension))
	    	.append(",");
    	} 
    				
    	sql			
    	.append(num.isPrimary() ? "1":"0")
    	.append(")");
    	
    	execute(sql.toString());    	
    	
    	return phoneNumberId;
    }
		    
    public PhoneNumber get(long phoneNumberId) {
    	
    	String sql = String.format("SELECT phone_number, area_code, number, extension, is_primary FROM %s WHERE phone_number=%s", getTableName(), phoneNumberId);
        List<PhoneNumber> numbers = query(sql, 
        	(rs, rowNum) -> 
        		new PhoneNumber(rs.getLong("phone_number"), rs.getInt("area_code"), rs.getInt("number"), rs.getInt("extension"), rs.getBoolean("is_primary")) );
        
        if (numbers.size() > 0) {
            return numbers.get(0);        	
        } else {
        	return null;
        }
    }
    
    public PhoneNumber get(int areacode, int number, int ext) {
    	return get(Long.parseLong(getPhoneNumberId(areacode, number, ext)));
    }
        
}
