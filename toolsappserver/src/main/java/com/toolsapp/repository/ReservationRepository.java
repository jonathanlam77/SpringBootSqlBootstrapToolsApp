package com.toolsapp.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.toolsapp.model.CreditCard;
import com.toolsapp.model.Reservation;
import com.toolsapp.model.ToolReservation;

@Repository
@DependsOn(value = { "customerRepository", "clerkRepository" })
public class ReservationRepository extends SqlRepository {

	protected static final String TABLE_NAME = "Reservation";
	
	protected String getCreateTableSql() {
    	return new StringBuffer()	
		.append("CREATE TABLE ")
		.append(getTableName())
		.append("(")
		.append("reservation_id 	INT(8) NOT NULL AUTO_INCREMENT PRIMARY KEY,")
		.append("customer			VARCHAR(64),")
		.append("pickup_clerk		VARCHAR(64),")
		.append("dropoff_clerk		VARCHAR(64),")
		.append("credit_number		BIGINT(16),")
		.append("start_date			DATE NOT NULL,")
		.append("end_date			DATE NOT NULL,")
		.append("CONSTRAINT fk_Reservation_customer_Customer_user_name FOREIGN KEY (customer) REFERENCES Customer(user_name),")
		.append("CONSTRAINT fk_Reservation_pickup_clerk_Clerk_user_name FOREIGN KEY (pickup_clerk) REFERENCES Clerk(user_name),")
		.append("CONSTRAINT fk_Reservation_dropoff_clerk_Clerk_user_name FOREIGN KEY (dropoff_clerk) REFERENCES Clerk(user_name)")
		.append(");")
		.toString();
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}
	
	private Reservation get(ResultSet rs) throws SQLException {
		return new Reservation(
				rs.getInt("reservation_id"), 
				rs.getString("customer"), 
				rs.getString("pickup_clerk"), 
				rs.getString("dropoff_clerk"), 
				rs.getLong("credit_number"), 
				rs.getDate("start_date"), 
				rs.getDate("end_date") 
				);
	}

    public Reservation get(int reservation_id) {
    	
    	String sql = String.format("SELECT reservation_id, customer, pickup_clerk, dropoff_clerk, credit_number, start_date, end_date FROM %s WHERE reservation_id=%s", getTableName(), reservation_id);

        List<Reservation> reservations = query(sql, (rs, rowNum) -> get(rs) );
        
        if (reservations.size() > 0) {
            return reservations.get(0);        	
        } else {
        	return null;
        }

    }
	
    public List<Reservation> getAll(String customer, boolean pickup, boolean dropoff) {
    	
		StringBuffer sql =  new StringBuffer()	
				.append("SELECT reservation_id, customer, pickup_clerk, dropoff_clerk, credit_number, start_date, end_date FROM ")
				.append(getTableName());

		if (customer != null) {

			sql.append(" WHERE customer='" + customer +"'");
			
		} else {

			if (pickup && !dropoff) {
				// Needs to be picked up
				sql.append(" WHERE pickup_clerk IS NULL AND dropoff_clerk IS NULL");
			} else if (!pickup && dropoff) {
				// Needs to be dropped off
				sql.append(" WHERE pickup_clerk IS NOT NULL AND dropoff_clerk IS NULL");
			} else if (pickup && dropoff) {
				sql.append(" WHERE pickup_clerk IS NULL AND dropoff_clerk IS NULL");
			} else {
				// Pickup and dropoff are false  
				// Already picked up and dropped off
				sql.append(" WHERE pickup_clerk IS NOT NULL AND dropoff_clerk IS NOT NULL");
			}
			
		}
				
		return query(sql.toString(), (rs, rowNum) -> {
		        							return get(rs);
		        						});
    }
    
    
    public Reservation add(Reservation r) {
    	
    	String sql = String.format("INSERT INTO %s (customer, pickup_clerk, dropoff_clerk, credit_number, start_date, end_date) " +
    								"VALUES (%s,%s,%s,%s,%s,%s)",
    								getTableName(),
    								toSqlStr(r.getCustomer()),
    								toSqlStr(r.getPickupClerk()),
    								toSqlStr(r.getDropoffClerk()),
    								r.getCreditCardNumber(),
    								toSqlStr(r.getStartDate().toString()),
    								toSqlStr(r.getEndDate().toString()));
    	execute(sql); 

    	List<Reservation> reservations = query("SELECT LAST_INSERT_ID()",
								     		(rs, rowNum) -> {
								     			r.setReservationId(rs.getInt("LAST_INSERT_ID()"));
								     			return r;
								     		});
    	
        if (reservations.size() > 0) {
            return reservations.get(0);        	
        } else {
        	return null;
        }
    }
    
    public void pickup(int reservationId, String clerk) {
    	String sql = String.format("UPDATE %s SET pickup_clerk='%s' WHERE reservation_id=%s", getTableName(), clerk, reservationId);
    	execute(sql); 
    }
    
    public void dropoff(int reservationId, String clerk) {
    	String sql = String.format("UPDATE %s SET dropoff_clerk='%s' WHERE reservation_id=%s", getTableName(), clerk, reservationId);
    	execute(sql); 
    }
        
    public void updateCreditCard(int reservationId, CreditCard cc) {
    	
    	String credit_card_number = NULL;
		credit_card_number = Long.toString(cc.getNumber());
    	
    	String sql = String.format("UPDATE %s SET credit_number=%s WHERE reservation_id=%s", getTableName(), credit_card_number, reservationId);
    	execute(sql); 
    }
    
    
}
