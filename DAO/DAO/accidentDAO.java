package DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Vector;

import Accident.Accident;

public class accidentDAO extends DAO implements accidentDAOinterface{

	
	public int showAllAccidentID() {
		 int index = 0;
		 this.sql = "select accidentID from accident where payInsurancePremium = false ";
		 Vector<Integer> accidentIDVector = new Vector<Integer>();
		 try {
			 this.connect = getConnection();
			 this.statement = this.connect.prepareStatement(sql);
			 this.resultSet = this.statement.executeQuery();
			 while (this.resultSet.next()) {
				 accidentIDVector.add(this.resultSet.getInt("accidentID"));
			 }

			 if(accidentIDVector.size() == 0) {
				 System.out.println("처리할 사고가 존재하지 않습니다.");

			 }else {
				 System.out.print("(사고ID:");
				 for (int i = 0; i < accidentIDVector.size(); i++) {
					 System.out.print(accidentIDVector.get(i) + " ");
				 }
				 System.out.print(")" + "\n");
				 index = 1;
			 }
		 } catch (SQLException e) {
			throw new RuntimeException("InsuranceDAO.showAllInsuranceID :" + e.getMessage());
		} finally {
			closeConnection(this.connect);
		}
		 return index;
	}
	
	 public int getAccidentID(int accidentID) {
		  this.sql = "select accidentID from Accident where accidentID = ?";
		  try {
			  this.connect = getConnection();
			  this.statement = this.connect.prepareStatement(sql);
			  this.statement.setInt(1, accidentID);
			  this.resultSet = this.statement.executeQuery();
			  if (this.resultSet.next()) {
				  accidentID = this.resultSet.getInt("accidentID");
			  } else {
				  // accidentID 존재하지 않을시 -1반환
				  accidentID = -1;
			  }
		  } catch (SQLException e) {
			  throw new RuntimeException("InsuranceDAO.getAccidentID :" + e.getMessage());
		  } finally {
			  closeConnection(this.connect);
		  }
		  return accidentID;
	  }
	 

	  public Accident findAccident(int accidentID) {
		  Accident accident = new Accident();
		  this.sql = "select * from Accident where accidentID = ?";
		  try {
			  this.connect = getConnection();
			  this.statement = this.connect.prepareStatement(sql);
			  this.statement.setInt(1, accidentID);
			  this.resultSet = this.statement.executeQuery();
			  if (this.resultSet.next()) {
				  accident.setAccidentID(this.resultSet.getInt("accidentID"));
				  accident.setAccidentDate(String.valueOf(this.resultSet.getDate("accidentDate")));
				  accident.setAccidentCause(this.resultSet.getString("accidentCause"));
				  accident.setAccidentLocation(this.resultSet.getString("accidentLocation"));
				  accident.setAccidentTime(String.valueOf(this.resultSet.getTime("accidentTime")));
				  accident.setExpertOpinion(this.resultSet.getString("expertOpinion"));
				  accident.setInsurancePremiumCause(this.resultSet.getString("insurancePremiumCause"));
				  accident.setInsurancePremium(this.resultSet.getInt("insurancePremium"));
				  accident.setPayInsurancePremium(this.resultSet.getBoolean("payInsurancePremium"));
				  accident.setCustomerID(this.resultSet.getInt("customerID"));
				  accident.setInsuranceID(this.resultSet.getInt("insuranceID"));
			  }
		  } catch (SQLException e) {
			  throw new RuntimeException("InsuranceDAO.findAccident :" + e.getMessage());
		  } finally {
			  closeConnection(connect);
		  }
		  return accident;
	  }
	  
	  public Accident insertAccident(Accident accident) {
			this.sql = "insert into Accident values(?,?,?,?,?,?,null,0,false,?,?)";

			try {
				this.connect = getConnection();
				this.statement = this.connect.prepareStatement(this.sql);

				this.statement.setInt(1, accident.getAccidentID());
				this.statement.setDate(2, Date.valueOf(accident.getAccidentDate()));
				this.statement.setString(3, accident.getAccidentCause());
				this.statement.setString(4, accident.getAccidentLocation());
				this.statement.setTime(5, Time.valueOf(accident.getAccidentTime()));
				this.statement.setString(6, accident.getExpertOpinion());
				this.statement.setInt(7, accident.getCustomerID());
				this.statement.setInt(8, accident.getInsuranceID());
				this.statement.executeUpdate();

			} catch (SQLException e) {
				throw new RuntimeException("InsuranceDAO.insertAccident :" + e.getMessage());
			} finally {
				closeConnection(this.connect);
			}
			return accident;
		}

		public void insertInsurancePayment(Accident accident, int accidentID) {
			this.sql = "update Accident set insurancePremiumCause = ?, insurancePremium= ? where accidentID = ?";

			try {
				this.connect = getConnection();
				this.statement = this.connect.prepareStatement(this.sql);

				this.statement.setString(1, accident.getInsurancePremiumCause());
				this.statement.setInt(2, accident.getInsurancePremium());
				this.statement.setInt(3, accidentID);
				this.statement.executeUpdate();

			} catch (SQLException e) {
				throw new RuntimeException("InsuranceDAO.insertInsurancePaymet :" + e.getMessage());
			} finally {
				closeConnection(this.connect);
			}
		}
		
		public void updatePayInsurancePremium(Accident accident) {
			this.sql = "update Accident set PayInsurancePremium = ? where accidentID = ?";
			try {
				this.connect = getConnection();
				this.statement = this.connect.prepareStatement(this.sql);
				this.statement.setBoolean(1, accident.isPayInsurancePremium());
				this.statement.setInt(2, accident.getAccidentID());
				this.statement.executeUpdate();

			} catch (SQLException e) {
				throw new RuntimeException("InsuranceDAO.updatePayInsurancePremium :" + e.getMessage());
			} finally {
				closeConnection(this.connect);
			}
		}
}
