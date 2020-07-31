package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import Customer.Customer;
import Insurance.Insurance;

public class subscriptionDAO extends DAO implements subscriptionDAOinterface{

	 public Vector<Integer> showAcceptanceAprove(int subscriptionStauts) {
         this.sql = "select insuranceID, customerID from subscription where subscriptionStatus = ?;";
            
            
         Vector<Integer> IDvector = new Vector<Integer>();
           try {
                  this.connect = getConnection();
                  this.statement = this.connect.prepareStatement(sql);
                  this.statement.setInt(1, subscriptionStauts);
                  this.resultSet= this.statement.executeQuery();
    
                  while(this.resultSet.next()) {
                     IDvector.add(this.resultSet.getInt("insuranceID"));
                     IDvector.add(this.resultSet.getInt("customerID"));         
                  }            
               }catch(SQLException e) {
                  throw new RuntimeException("InsuranceDAO.showAcceptanceAprove :" + e.getMessage());
               }finally {
                  closeConnection(this.connect);
               }
           return IDvector;
              
      }
	 
	 public int showAllCustomerID() {
		  int index = 0;
         this.sql = "select distinct(customerID),distinct(subscription.insuranceID), insuranceType" + 
               " from subscription" + 
               " join insurance on subscription.insuranceid = subscription.insuranceid" + 
               " where subscriptionstatus = 1;";
         Vector<Integer> fireVector = new Vector<Integer>();
         Vector<Integer> carVector = new Vector<Integer>();
         Vector<Integer> actualCostVector = new Vector<Integer>();
         try {
            this.connect = getConnection();
            this.statement = this.connect.prepareStatement(sql);
            this.resultSet= this.statement.executeQuery();
            while(this.resultSet.next()) {
               if(Insurance.InsuranceType.valueOf(this.resultSet.getString("insuranceType")).equals(Insurance.InsuranceType.Fire)) {
                  fireVector.add(this.resultSet.getInt("customerID"));
               }else if(Insurance.InsuranceType.valueOf(this.resultSet.getString("insuranceType")).equals(Insurance.InsuranceType.Car)) {
                  carVector.add(this.resultSet.getInt("customerID"));      
               }else if(Insurance.InsuranceType.valueOf(this.resultSet.getString("insuranceType")).equals(Insurance.InsuranceType.ActualCost)) {
                  actualCostVector.add(this.resultSet.getInt("customerID"));
               }
            }
            
            if(fireVector.size() == 0 && actualCostVector.size() == 0 && carVector.size() == 0) {
           	 System.out.println("현재 가입된 고객이 없습니다");
            }else {
           	 System.out.print("(화재보험 고객:");
           	 for(int i=0; i<fireVector.size(); i++) {

           		 System.out.print(fireVector.get(i) + " ");
           	 }
           	 System.out.print(") (실비보험 고객:");
           	 for(int i=0; i<actualCostVector.size(); i++) {
           		 System.out.print(actualCostVector.get(i) + " ");
           	 }
           	 System.out.print(") (자동차보험 고객:");
           	 for(int i=0; i<carVector.size(); i++) {
           		 System.out.print(carVector.get(i) + " ");
           	 }
           	 System.out.print(")" + "\n");
           	 index = 1;
            }
         }catch(SQLException e) {
       	  throw new RuntimeException("InsuranceDAO.showAllCustomerID :" + e.getMessage());
         }finally {
       	  closeConnection(this.connect);
         }
         return index;
	  }
	 
	   public void deleteSubscription(Customer customer, int insuranceID) {
           this.sql = "delete from subscription where customerID= ? and insuranceID = ?";
            try {
                 this.connect = getConnection();
                 this.statement = this.connect.prepareStatement(this.sql);
                
                 this.statement.setInt(1, customer.getCustomerID());
                 this.statement.setInt(2, insuranceID);
                 this.statement.executeUpdate();
                 

              }catch(SQLException e) {
                 throw new RuntimeException("InsuranceDAO.deleteSubscription :" + e.getMessage());
              }finally {
                 closeConnection(this.connect);
              }
           }
	   
	   public void updateSubscriptionStatus(Customer customer, int insuranceID) {
	         this.sql = "update subscription set subscriptionStatus = ? where customerID = ? and insuranceID = ?";

	         try {
	            this.connect = getConnection();
	            this.statement = this.connect.prepareStatement(this.sql);
	            this.statement.setBoolean(1, customer.isSubscriptionStatus());
	            this.statement.setInt(2, customer.getCustomerID());
	            this.statement.setInt(3, insuranceID);
	            this.statement.executeUpdate();
	            

	         }catch(SQLException e) {
	            throw new RuntimeException("InsuranceDAO.updateSubscriptionStatus :" + e.getMessage());
	         }finally {
	            closeConnection(this.connect);
	         }
	      }
	   
	   public boolean insertSubscription(int insuranceID, int customerID) {
		      this.sql = "insert into Subscription values(?, ?, null, false)";

		      try {
		         this.connect = this.getConnection();
		         this.statement = connect.prepareStatement(sql);
		         this.statement.setInt(1, insuranceID);
		         this.statement.setInt(2, customerID);
		         statement.execute();
		         return true;
		      } catch (SQLException e) {
		         //throw new RuntimeException("InsuranceDAO.insertSubscription :" + e.getMessage());
		         System.err.println("insertSubscription PK 정보 중복");
		      } finally {
		         closeConnection(connect);
		      }
		      return false;
		   }
		   
		   public void showAllSubscription() {
		      this.sql = "select insuranceID, customerID from Subscription where subscriptionStatus = true & contractID is null";
		      try {
		         this.connect = this.getConnection();
		         PreparedStatement statement = connect.prepareStatement(this.sql);
		         ResultSet resultSet = statement.executeQuery();

		      while (resultSet.next()) {
		            int insuranceID = resultSet.getInt("insuranceID");
		            int customerID = resultSet.getInt("customerID");
		            System.out.println("보험ID :" + insuranceID + "  고객ID: " + customerID );
		         }

		      } catch (SQLException e) {
		         throw new RuntimeException("InsuranceDAO.showAllSubscription :" + e.getMessage());
		      } finally {
		         closeConnection(connect);
		      }
		   }

}
