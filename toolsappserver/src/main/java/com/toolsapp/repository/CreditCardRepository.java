package com.toolsapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.toolsapp.model.CreditCard;

@Repository
public class CreditCardRepository extends SqlRepository {
	
    protected String getCreateTableSql() {
    	return new StringBuffer()
		.append("CREATE TABLE ")
		.append(getTableName())
		.append("(")
		.append("number 	BIGINT(16) NOT NULL PRIMARY KEY,")
		.append("name		VARCHAR(256) NOT NULL,")
		.append("month		INT(2) NOT NULL,")
		.append("year		INT(2) NOT NULL,")
		.append("cvc		INT(3) NOT NULL")
		.append(");")
		.toString();
    }
    
    protected String getTableName() {
    	return "CreditCard";
    }
    
    protected String getAllAttrForSqlQuery () {
    	return "number, name, month, year, cvc";
    }

    protected CreditCard getCreditCard(ResultSet rs) throws SQLException {
    	CreditCard cc = new CreditCard();
    	cc.setNumber(rs.getLong("number"));
    	cc.setName(rs.getString("name"));
    	cc.setMonth(rs.getInt("month"));
    	cc.setYear(rs.getInt("year"));
    	cc.setCvc(rs.getInt("cvc"));
    	return cc;
    }
    
    public CreditCard get(long number) {
    	String sql = String.format("SELECT number, name, month, year, cvc FROM %s WHERE number=%s", getTableName(), number);

        List<CreditCard> numbers = query(sql,
     		(rs, rowNum) -> new CreditCard(rs.getLong("number"), rs.getString("name"), rs.getInt("month"), rs.getInt("year"), rs.getInt("cvc")) );
        
        if (numbers.size() > 0) {
            return numbers.get(0);        	
        } else {
        	return null;
        }
    }
    
    public void add(CreditCard c) {
    	String sql = String.format("INSERT INTO %s (number, name, month, year, cvc) VALUES (%s, '%s', %s, %s, %s)", 
    						getTableName(), c.getNumber(), c.getName(), c.getMonth(), c.getYear(), c.getCvc());
    	
    	execute(sql.toString());    				
    }
    
    
}
