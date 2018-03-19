package com.toolsapp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.Clerk;
import com.toolsapp.model.ClerkReport;
import com.toolsapp.model.CreditCard;
import com.toolsapp.model.Customer;
import com.toolsapp.model.CustomerReport;
import com.toolsapp.model.PhoneNumber;

@Repository
@DependsOn(value = { "phoneNumberRepository", "creditCardRepository" })
public class CustomerRepository extends SqlRepository {

	@Autowired
	CreditCardRepository creditCardRepository;

	@Autowired
	PhoneNumberRepository phoneNumberRepository;
	
	protected String getCreateTableSql() {
    	return new StringBuffer()	
		.append("CREATE TABLE ")
		.append(getTableName())
		.append("(")
		.append("user_name 		VARCHAR(64) PRIMARY KEY,")
		.append("email			tinytext NOT NULL,")
		.append("password		VARCHAR(64) NOT NULL,")
		.append("first_name		VARCHAR(64) NOT NULL,")
		.append("mid_name		VARCHAR(64),")
		.append("last_name		VARCHAR(64) NOT NULL,")
		.append("cell		 	BIGINT(14),")
		.append("home		 	BIGINT(14),")
		.append("work		 	BIGINT(14),")
		.append("credit_card_number		BIGINT(16),")
		.append("street			VARCHAR(256) NOT NULL,")				
		.append("city			VARCHAR(256) NOT NULL,")				
		.append("state			VARCHAR(2) NOT NULL,")				
		.append("zip				INT(5) NOT NULL,")				
		.append("CONSTRAINT fk_Customer_cell_PhoneNumber_phone_number FOREIGN KEY (cell) 		REFERENCES PhoneNumber(phone_number),")				
		.append("CONSTRAINT fk_Customer_home_PhoneNumber_phone_number FOREIGN KEY (home) 		REFERENCES PhoneNumber(phone_number),")
		.append("CONSTRAINT fk_Customer_work_PhoneNumber_phone_number FOREIGN KEY (work) 	REFERENCES PhoneNumber(phone_number),")
		.append("CONSTRAINT fk_Customer_credit_card_number_CreditCard_number FOREIGN KEY (credit_card_number) REFERENCES CreditCard(number)")
		.append(");")
		.toString();
    }
    
    protected String getTableName() {
    	return "Customer";
    }
    
    public Customer get(String username) {
    	String sql = String.format(
    				"SELECT user_name, email, password, first_name, mid_name, last_name, cell, home, work, credit_card_number, street, city, state, zip, "
    				+ creditCardRepository.getAllAttrForSqlQuery() + 
    				" FROM %s INNER JOIN %s ON %s.credit_card_number = %s.number WHERE user_name='%s'", 
    				getTableName(), 
    				creditCardRepository.getTableName(),
    				getTableName(), 
    				creditCardRepository.getTableName(),
    				username);

		List<Customer> customers = query(sql,
				(rs, rowNum) -> {
					
					PhoneNumber cell = null;
					long cellPhoneNumberId = rs.getLong("cell");
					if (cellPhoneNumberId != 0) {
						cell = phoneNumberRepository.get(cellPhoneNumberId);
					}
					
					PhoneNumber home = null;
					long homePhoneNumberId = rs.getLong("home");
					if (homePhoneNumberId != 0) {
						home = phoneNumberRepository.get(homePhoneNumberId);
					}

					PhoneNumber work = null;
					long workPhoneNumberId = rs.getLong("work");
					if (workPhoneNumberId != 0) {
						work = phoneNumberRepository.get(workPhoneNumberId);
					}

					return new Customer(
							rs.getString("user_name"), 
							rs.getString("email"), 
							rs.getString("password"), 
							rs.getString("first_name"), 
							rs.getString("mid_name"), 
							rs.getString("last_name"), 
							cell, 
							home, 
							work, 
							creditCardRepository.getCreditCard(rs),
							rs.getString("street"), 
							rs.getString("city"), 
							rs.getString("state"), 
							rs.getString("zip")
							);					
				});
		
		if (customers.size() > 0) {
		    return customers.get(0);        	
		} else {
			return null;
		}
    }
    
    public void add(Customer c) {
    	    	
    	String cellId = NULL;
    	PhoneNumber cell = c.getCell();
    	if (cell != null) {
    		cellId = phoneNumberRepository.add(cell);
    	}
    	
    	String homeId = NULL;
    	PhoneNumber home = c.getHome();
    	if (home != null) {
    		homeId = phoneNumberRepository.add(home);
    	}
    	
    	String workId = NULL;
    	PhoneNumber work = c.getWork();
    	if (work != null) {
    		workId = phoneNumberRepository.add(work);
    	}

    	String credit_card_number = NULL;
    	CreditCard cc = c.getCreditCard();
    	if (cc != null) {
    		credit_card_number = Long.toString(cc.getNumber());
    		creditCardRepository.add(cc);
    	}
    	    	
    	String sql = String.format("INSERT INTO %s (user_name, email, password, first_name, mid_name, last_name, cell, home, work, credit_card_number, street, city, state, zip) " +
    								"VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)",
    								getTableName(),
    								toSqlStr(c.getUsername()),
    								toSqlStr(c.getEmail()),
    								toSqlStr(c.getPassword()),
    								toSqlStr(c.getFirstName()),
    								toSqlStr(c.getMidName()),
    								toSqlStr(c.getLastName()),
    								cellId,
    								homeId,
    								workId,
    								credit_card_number,
    								toSqlStr(c.getStreet()),
    								toSqlStr(c.getCity()),
    								toSqlStr(c.getState()),
    								c.getZip());

    	execute(sql.toString());    				
    }
 
    public List<CustomerReport> report() {
    	
    	StringBuffer sql = new StringBuffer()
    			.append("SELECT customer, num_tools_rented, num_reservations, user_name, email, password, first_name, mid_name, last_name, cell, home, work, credit_card_number, street, city, state, zip ")
    			.append("FROM ")
    			.append("	( ")
    			.append("	SELECT * ")
    			.append("	FROM ")
    			.append("		(SELECT customer , count(*) AS num_tools_rented FROM ")
    			.append("		Reservation NATURAL JOIN ReservedTools ")
    			.append("       WHERE MONTH(start_date) =  MONTH(NOW()) AND YEAR(start_date) = YEAR(NOW()) ")
    			.append("		GROUP BY customer ) AS CustomerNumToolsRented ")
    			.append("	NATURAL JOIN ")
    			.append("		(SELECT customer, count(*) as num_reservations ")
    			.append("		FROM ")
    			.append("			(SELECT customer, reservation_id ")
    			.append("			FROM Reservation NATURAL JOIN ReservedTools ")
    			.append("           WHERE MONTH(start_date) =  MONTH(NOW()) AND YEAR(start_date) = YEAR(NOW()) ")
    			.append("			GROUP BY customer, reservation_id) AS CustomerReservations ")
    			.append("		GROUP BY customer) AS CustomerNumReservations ")
    			.append("	) AS CustomerNumToolsReservations ")
    			.append("INNER JOIN CUSTOMER ")
    			.append("ON Customer.user_name = CustomerNumToolsReservations.customer ")
    			.append("ORDER BY num_tools_rented DESC, last_name ");
 
    	List<CustomerReport> customers = query(sql.toString(),
    			(rs, rowNum) -> {
    				Customer customer = get(rs.getString("user_name"));

    				CustomerReport cr = new CustomerReport();
    				cr.setCustomer(customer); 
    				cr.setTotalReservations(rs.getInt("num_reservations"));
    				cr.setTotalToolsRented(rs.getInt("num_tools_rented"));
    				return cr;
    			});

    	return customers;
    }

    public void updateCreditCard(String customer, CreditCard cc) {
    	
    	String credit_card_number = NULL;
		credit_card_number = Long.toString(cc.getNumber());
    	String sql = String.format("UPDATE %s SET credit_card_number=%s WHERE user_name=%s", getTableName(), credit_card_number, toSqlStr(customer));
    	execute(sql); 
    }

    
}