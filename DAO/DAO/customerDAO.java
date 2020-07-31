package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import Customer.ActualCost;
import Customer.Building;
import Customer.Car;
import Customer.Customer;
import Customer.PersonalInformation;
import Insurance.Insurance;

public class customerDAO extends DAO implements customerDAOinterface{
	
	
	public Insurance.InsuranceType getInsuranceType(int insuranceID) {
		  this.sql = "select insuranceType from insurance where insuranceID = ?";
		  Insurance.InsuranceType insuranceType = null;
		  try {
			  this.connect = getConnection();
			  this.statement = this.connect.prepareStatement(sql);
			  this.statement.setInt(1, insuranceID);
			  this.resultSet = this.statement.executeQuery();

			  if (this.resultSet.next()) {
				  insuranceType = Insurance.InsuranceType.valueOf(this.resultSet.getString("insuranceType"));
			  }

		  } catch (SQLException e) {
			  throw new RuntimeException("InsuranceDAO.getInsuranceType :" + e.getMessage());
		  } finally {
			  closeConnection(this.connect);
		  }
		  return insuranceType;
	  }

	public PersonalInformation findPersonalInformation(Customer customer, int insuranceID) {
        PersonalInformation personalInformation = null;
        
        switch(getInsuranceType(insuranceID)) {
        case Fire:
           personalInformation = new Building();
           break;
        case Car:
           personalInformation = new Car();
           break;
        case ActualCost:
           personalInformation = new ActualCost();
           break;
        }
        
        this.sql = "select * from personalInformation where PcustomerID = ?";
        try {
           this.connect = getConnection();
           this.statement = this.connect.prepareStatement(sql);
           this.statement.setInt(1, customer.getCustomerID());
           this.resultSet= this.statement.executeQuery();
           if(this.resultSet.next()) {
              personalInformation.setAccidentHistory(this.resultSet.getString("accidentHistory"));
              personalInformation.setAccountNumber(this.resultSet.getInt("accountNumber"));
              personalInformation.setGender(this.resultSet.getBoolean("gender"));
              personalInformation.setJob(PersonalInformation.Job.valueOf(this.resultSet.getString("job")));
              personalInformation.setProperty(this.resultSet.getInt("property"));
              personalInformation.setResidentRegistrationNumber(this.resultSet.getString("residentRegistrationNumber"));
           }
           customer.setPersonalInformation(personalInformation);
        }catch(SQLException e) {
           throw new RuntimeException("InsuranceDAO.findPersonalInformation :" + e.getMessage());
        }finally {
           closeConnection(connect);
        }
        return personalInformation;
     }
	
	public void findBuildingCustomer(Building building, int customerID) {
		this.sql = "select * from Building where BcustomerID = ?";
		try {
			this.connect = getConnection();
			this.statement = this.connect.prepareStatement(sql);
			this.statement.setInt(1, customerID);
			this.resultSet = this.statement.executeQuery();
			if (this.resultSet.next()) {
				building.setBuildingAddress(this.resultSet.getString("buildingAddress"));
				building.setBuildingPrice(this.resultSet.getLong("buildingPrice"));
				building.setBuildingScale(this.resultSet.getString("buildingScale"));
			}
		} catch (SQLException e) {
			throw new RuntimeException("InsuranceDAO.findBuildingCustomer :" + e.getMessage());
		} finally {
			closeConnection(connect);
		}
	}

	public void findCarCustomer(Car car, int customerID) {
		this.sql = "select * from Car where CcustomerID = ?";
		try {
			this.connect = getConnection();
			this.statement = this.connect.prepareStatement(sql);
			this.statement.setInt(1, customerID);
			this.resultSet = this.statement.executeQuery();
			if (this.resultSet.next()) {
				car.setCarNumber(this.resultSet.getString("carNumber"));
				car.setCarType(Car.CarType.valueOf(this.resultSet.getString("carType")));
				car.setDrivingCareer(this.resultSet.getInt("drivingCareer"));
				car.setLicenseType(Car.LicenseType.valueOf(this.resultSet.getString("licenseType")));
			}
		} catch (SQLException e) {
			throw new RuntimeException("InsuranceDAO.findCarCustomer :" + e.getMessage());
		} finally {
			closeConnection(connect);
		}

	}

	public void findActualCostCustomer(ActualCost actualCost, int customerID) {
		this.sql = "select * from ActualCost where AcustomerID = ?";
		String[] famillyHistoryArr = null;
		HashMap<String, String> famillyHistory = new HashMap<String, String>();
		try {
			this.connect = getConnection();
			this.statement = this.connect.prepareStatement(this.sql);
			this.statement.setInt(1, customerID);
			this.resultSet = this.statement.executeQuery();

			if (this.resultSet.next()) {
				actualCost.setBloodType(ActualCost.BloodType.valueOf(this.resultSet.getString("bloodType")));
				actualCost.setDiseaseHistory(ActualCost.DiseaseHistory.valueOf(this.resultSet.getString("diseaseHistory")));

				famillyHistoryArr = this.resultSet.getString("familyHistory").split(":");
				famillyHistory.put(famillyHistoryArr[0], famillyHistoryArr[1]);
				actualCost.setFamilyHistory(famillyHistory);
			}
		} catch (SQLException e) {
			throw new RuntimeException("InsuranceDAO.findActualCostCustomer :" + e.getMessage());
		} finally {
			closeConnection(this.connect);
		}

	}
	
	public void insertCustomer(Customer customer) {
	      this.sql = "insert into Customer values(?, ?, ?)";
	      try {
	         this.connect = this.getConnection();
	         this.statement = connect.prepareStatement(sql);
	         this.statement.setInt(1, customer.getCustomerID());
	         this.statement.setString(2, customer.getPhoneNum());
	         this.statement.setString(3, customer.getCustomerName());
	         statement.execute();

	      } catch (SQLException e) {
	         throw new RuntimeException("InsuranceDAO.insertCustomer :" + e.getMessage());
	      } finally {
	         closeConnection(connect);
	      }
	   }

	   public void insertPersonalInformation(PersonalInformation personalInformation, int CustomerID) {
	      this.sql = "insert into PersonalInformation values(?, ?, ?, ?, ?, ?, ?)";

	      try {
	         this.connect = this.getConnection();
	         this.statement = connect.prepareStatement(sql);
	         this.statement.setInt(1, CustomerID);
	         this.statement.setString(2, personalInformation.getAccidentHistory());
	         this.statement.setInt(3, personalInformation.getAccountNumber());
	         this.statement.setString(4, personalInformation.getResidentRegistrationNumber());
	         this.statement.setBoolean(5, personalInformation.getGender());
	         this.statement.setString(6, personalInformation.getJob().toString());
	         this.statement.setInt(7, personalInformation.getProperty());
	         statement.execute();

	      } catch (SQLException e) {
	         throw new RuntimeException("InsuranceDAO.insertPersonalInformation :" + e.getMessage());
	      } finally {
	         closeConnection(connect);
	      }
	   }

	   public void insertBuilding(Building building, int customerID) {
	      this.sql = "insert into Building values(?, ?, ?, ?)";

	      try {
	         this.connect = this.getConnection();
	         this.statement = connect.prepareStatement(sql);
	         this.statement.setInt(1, customerID);
	         this.statement.setLong(2, building.getBuildingPrice());
	         this.statement.setString(3, building.getBuildingAddress());
	         this.statement.setString(4, building.getBuildingScale());
	         statement.execute();

	      } catch (SQLException e) {
	         throw new RuntimeException("InsuranceDAO.insertBuilding :" + e.getMessage());
	      } finally {
	         closeConnection(connect);
	      }
	   }

	   public void insertActualCost(ActualCost actualCost, int customerID) {
	      this.sql = "insert into ActualCost values(?, ?, ?, ?)";
	      HashMap<String, String> hash = actualCost.getFamilyHistory();
	      String Key = null;
	      String value = null;

	      Set set = hash.entrySet();
	      Iterator iterator = set.iterator();

	      while (iterator.hasNext()) {
	         Map.Entry entry = (Map.Entry) iterator.next();
	         Key = (String) entry.getKey();
	         value = (String) entry.getValue();
	      }

	      try {
	         this.connect = this.getConnection();
	         this.statement = connect.prepareStatement(sql);
	         this.statement.setInt(1, customerID);
	         this.statement.setString(2, actualCost.getBloodType().toString());
	         this.statement.setString(3, Key + ":" + value);
	         this.statement.setString(4, actualCost.getDiseaseHistory().toString());
	         statement.execute();

	      } catch (SQLException e) {
	         throw new RuntimeException("InsuranceDAO.insertActualCost :" + e.getMessage());
	      } finally {
	         closeConnection(connect);
	      }
	   }

	   public void insertCar(Car car, int customerID) {
	      this.sql = "insert into Car values(?, ?, ?, ?, ?)";

	      try {
	         this.connect = this.getConnection();
	         this.statement = connect.prepareStatement(sql);
	         this.statement.setInt(1, customerID);
	         this.statement.setString(2, car.getCarNumber());
	         this.statement.setString(3, car.getCarType().toString());
	         this.statement.setInt(4, car.getDrivingCareer());
	         this.statement.setString(5, car.getLicenseType().toString());
	         statement.execute();

	      } catch (SQLException e) {
	         throw new RuntimeException("InsuranceDAO.insertCar :" + e.getMessage());
	      } finally {
	         closeConnection(connect);
	      }
	   }
	   public void updatePersonalInformation(int customerID, int accountNumber) {
		      this.sql = "update PersonalInformation set accountNumber = ? where PcustomerID = ?";

		      try {
		         this.connect = getConnection();
		         this.statement = this.connect.prepareStatement(this.sql);
		         this.statement.setInt(1, accountNumber);
		         this.statement.setInt(2, customerID);
		         this.statement.executeUpdate();

		      } catch (SQLException e) {
		         throw new RuntimeException("InsuranceDAO.updatePersonalInformation :" + e.getMessage());
		      } finally {
		         closeConnection(this.connect);
		      }
		   }
	   
	   public Car getCar(int customerID) {
		      this.sql = "select * from Car where CcustomerID = ?";
		      Car car = new Car();
		      
		      try {
		         this.connect = this.getConnection();
		         PreparedStatement statement = connect.prepareStatement(this.sql);
		         
		         statement.setInt(1, customerID);
		         ResultSet resultSet = statement.executeQuery();

		      if (resultSet.next()) {
		         car.setCarNumber(resultSet.getString("carNumber"));
		         car.setCarType(Car.CarType.valueOf(resultSet.getString("carType")));
		         car.setDrivingCareer(resultSet.getInt("drivingCareer"));
		         car.setLicenseType(Car.LicenseType.valueOf(resultSet.getString("licenseType")));
		         }
		         
		      } catch (SQLException e) {
		         throw new RuntimeException("InsuranceDAO.getInsurance :" + e.getMessage());
		      } finally {
		         closeConnection(connect);
		      }
		      return car;
		   }

		   public float getBuildingPrice(int customerID) {
		      this.sql = "select buildingPrice from Building where BcustomerID = ?";
		      float buildingPrice = 0;
		      
		      try {
		         this.connect = this.getConnection();
		         PreparedStatement statement = connect.prepareStatement(this.sql);
		         
		         statement.setInt(1, customerID);
		         ResultSet resultSet = statement.executeQuery();

		      if (resultSet.next()) {
		         buildingPrice = resultSet.getLong("buildingPrice");      
		         }
		         
		      } catch (SQLException e) {
		         throw new RuntimeException("InsuranceDAO.getFire :" + e.getMessage());
		      } finally {
		         closeConnection(connect);
		      }
		      return buildingPrice;
		   }

		   public ActualCost getActualCost(int customerID) {
		      this.sql = "select * from ActualCost where AcustomerID = ?";
		      ActualCost actualCost = new ActualCost();
		      String[] famillyHistoryArr = null;
		      HashMap<String, String> famillyHistory = new HashMap<String, String>();
		      
		      try {
		         this.connect = this.getConnection();
		         PreparedStatement statement = connect.prepareStatement(this.sql);
		         
		         statement.setInt(1, customerID);
		         ResultSet resultSet = statement.executeQuery();

		      if (resultSet.next()) {
		         actualCost.setBloodType(ActualCost.BloodType.valueOf(resultSet.getString("bloodType")));
		         actualCost.setDiseaseHistory(ActualCost.DiseaseHistory.valueOf(this.resultSet.getString("diseaseHistory")));

		         famillyHistoryArr = this.resultSet.getString("familyHistory").split(":");
		         famillyHistory.put(famillyHistoryArr[0], famillyHistoryArr[1]);
		         actualCost.setFamilyHistory(famillyHistory);
		         }
		         
		      } catch (SQLException e) {
		         throw new RuntimeException("InsuranceDAO.getActualCost :" + e.getMessage());
		      } finally {
		         closeConnection(connect);
		      }
		      return actualCost;
		   }

		   public PersonalInformation findPersonalInformation2(int customerID) {
		        PersonalInformation personalInformation = new PersonalInformation();
		        
		        this.sql = "select * from PersonalInformation where PcustomerID = ?";
		        try {
		           this.connect = getConnection();
		           this.statement = this.connect.prepareStatement(sql);
		           this.statement.setInt(1, customerID);
		           this.resultSet= this.statement.executeQuery();
		           if(this.resultSet.next()) {
		              personalInformation.setAccidentHistory(this.resultSet.getString("accidentHistory"));
		              personalInformation.setAccountNumber(this.resultSet.getInt("accountNumber"));
		              personalInformation.setGender(this.resultSet.getBoolean("gender"));
		              personalInformation.setJob(PersonalInformation.Job.valueOf(this.resultSet.getString("job")));
		              personalInformation.setProperty(this.resultSet.getInt("property"));
		              personalInformation.setResidentRegistrationNumber(this.resultSet.getString("residentRegistrationNumber"));
		           }
		        }catch(SQLException e) {
		           throw new RuntimeException("InsuranceDAO.findPersonalInformation :" + e.getMessage());
		        }finally {
		           closeConnection(connect);
		        }
		        return personalInformation;
		     }
}
