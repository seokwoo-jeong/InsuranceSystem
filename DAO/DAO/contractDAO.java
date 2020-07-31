package DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import Contract.Contract;
import Contract.ContractListImpl;
import Customer.Customer;
import Customer.CustomerListImpl;

public class contractDAO extends DAO implements contractDAOinterface{
	public void createContract(Contract contract) {
	      this.sql = "insert into Contract values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	      java.sql.Date sqlDate1 = new java.sql.Date(contract.getContractExpirationDate().getTime());
	      java.sql.Date sqlDate2 = new java.sql.Date(contract.getPaymentDate().getTime());
	      java.sql.Date sqlDate3 = new java.sql.Date(contract.getPersonalInformationRetentionPeriod().getTime());

	      try {
	         this.connect = this.getConnection();
	         this.statement = connect.prepareStatement(sql);
	         this.statement.setInt(1, contract.getContractID());
	         this.statement.setDate(2, sqlDate1);
	         this.statement.setDate(3, sqlDate2);
	         this.statement.setInt(4, contract.getPaymentPeriod());
	         this.statement.setDate(5, sqlDate3);
	         this.statement.setString(6, contract.getPaymentType().toString());
	         this.statement.setBoolean(7, contract.getPaymentStatus());
	         this.statement.setInt(8, contract.getPaymentAmount());
	         this.statement.setInt(9, contract.getCustomerID());
	         this.statement.executeUpdate();

	      } catch (SQLException e) {
	         throw new RuntimeException("InsuranceDAO.createContract :" + e.getMessage());
	      } finally {
	         closeConnection(connect);
	      }
	   }

	   public Contract searchContract(int cusomerID) {
	      this.sql = "select * from Contract where customerID = ?";
	      Contract contract = new Contract();

	      try {
	         this.connect = this.getConnection();
	         this.statement = connect.prepareStatement(this.sql);

	         this.statement.setInt(1, cusomerID);
	         this.resultSet = this.statement.executeQuery();

	         if (this.resultSet.next()) {
	            contract.setContractID(resultSet.getInt("contractID"));
	            contract.setContractExpirationDate(resultSet.getDate("contarctExplrationDate"));
	            contract.setPaymentDate(resultSet.getDate("paymentDate"));
	            contract.setPaymentPeriod(resultSet.getInt("paymentPeriod"));
	            contract.setPersonalInformationRetentionPeriod(resultSet.getDate("personalInformationRetentionPeriod"));
	            contract.setPaymentType(Contract.PaymentType.valueOf(resultSet.getString("paymentType")));
	            contract.setPaymentStatus(resultSet.getBoolean("paymentStatus"));
	            contract.setPaymentAmount(resultSet.getInt("paymentAmount"));
	            contract.setCustomerID(resultSet.getInt("customerID"));
	         } else {
	            System.out.println("해당하는 계약 존재 하지 않음");
	         }
	      } catch (SQLException e) {
	         throw new RuntimeException("InsuranceDAO.searchContract :" + e.getMessage());
	      } finally {
	         closeConnection(connect);
	      }
	      return contract;
	   }
	   public Customer findCustomer(int customerID) {
			Customer customer = new Customer();
			this.sql = "select * from Customer where customerID = ?";
			try {
				this.connect = getConnection();
				this.statement = this.connect.prepareStatement(sql);
				this.statement.setInt(1, customerID);
				this.resultSet = this.statement.executeQuery();
				if (this.resultSet.next()) {
					customer.setCustomerID(this.resultSet.getInt("customerID"));
					customer.setCustomerName(this.resultSet.getString("customerName"));
					customer.setPhoneNum(this.resultSet.getString("phoneNum"));
				}
			} catch (SQLException e) {
				throw new RuntimeException("InsuranceDAO.findAcceptance :" + e.getMessage());
			} finally {
				closeConnection(connect);
			}
			return customer;
		}

	   public void searchUnpaidCustomer() {
	      this.sql = "select customerID from Contract where paymentStatus = ?";
	      try {
	         this.connect = this.getConnection();
	         PreparedStatement statement = connect.prepareStatement(this.sql);
	         
	         statement.setBoolean(1, false);
	         ResultSet resultSet = statement.executeQuery();

	      while (resultSet.next()) {
	            int customerID = resultSet.getInt("customerID");
	            Customer customer = findCustomer(customerID);
	            Contract contract = searchContract(customerID);
	            // 계약ID, 고객 이름, 연락처, 납입일, 납입여부, 납입 방식
	            System.out.println("계약ID: " + contract.getContractID() + "  고객 이름: " + customer.getCustomerName()
	                  + "  연락처: " + customer.getPhoneNum() + "  납입일: " + contract.getPaymentDate() + "  납입여부: "
	                  + contract.getPaymentStatus() + "  납입방식: " + contract.getPaymentType());
	         }

	      } catch (SQLException e) {
	         throw new RuntimeException("InsuranceDAO.searchUnpaidCustomer :" + e.getMessage());
	      } finally {
	         closeConnection(connect);
	      }
	   }

	   public HashMap<CustomerListImpl, ContractListImpl> searchFullContractCustomer() {
	      this.sql = "select contractID, customerID from Contract where contarctExplrationDate < ?";

	      CustomerListImpl customerList = new CustomerListImpl();
	      ContractListImpl contractList = new ContractListImpl();

	      HashMap<CustomerListImpl, ContractListImpl> CustomerAndContractList = new HashMap<CustomerListImpl, ContractListImpl>();

	      Customer customer;
	      Contract contract;

	      int customerID;
	      int contractID;

	      Calendar cal = new GregorianCalendar();
	      Date nowTime = new Date(cal.getTimeInMillis());

	      try {
	         this.connect = this.getConnection();
	         PreparedStatement statement = connect.prepareStatement(this.sql);

	         statement.setDate(1, nowTime);
	         ResultSet resultSet = statement.executeQuery();

	         while (resultSet.next()) {
	            contractID = resultSet.getInt("contractID");
	            customerID = resultSet.getInt("customerID");

	            customer = findCustomer(customerID);
	            customerList.add(customer);

	            contract = searchContract(customerID);
	            contractList.add(contract);
	         }
	         CustomerAndContractList.put(customerList, contractList);

	      } catch (SQLException e) {
	         throw new RuntimeException("InsuranceDAO.searchFullContractCustomer :" + e.getMessage());
	      } finally {
	         closeConnection(connect);
	      }
	      return CustomerAndContractList;
	   }
	   

	   public void updateContract(Contract contract) {
	      this.sql = "update Contract set contarctExplrationDate = ?, paymentDate = ?, "
	            + "paymentType = ? where contractID = ?";
	      java.sql.Date sqlDate1 = new java.sql.Date(contract.getContractExpirationDate().getTime());
	      java.sql.Date sqlDate2 = new java.sql.Date(contract.getPaymentDate().getTime());
	      try {
	         this.connect = getConnection();
	         this.statement = this.connect.prepareStatement(this.sql);
	         this.statement.setDate(1, sqlDate1);
	         this.statement.setDate(2, sqlDate2);
	         this.statement.setString(3, contract.getPaymentType().toString());
	         this.statement.setInt(4, contract.getContractID());
	         this.statement.executeUpdate();

	      } catch (SQLException e) {
	         throw new RuntimeException("InsuranceDAO.updateContract :" + e.getMessage());
	      } finally {
	         closeConnection(this.connect);
	      }
	   }

}
