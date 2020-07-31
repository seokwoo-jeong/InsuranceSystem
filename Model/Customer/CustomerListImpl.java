package Customer;

import java.util.Vector;

public class CustomerListImpl {

	private Vector<Customer> CustomerVector;
	public CustomerListImpl() {
		this.CustomerVector = new Vector<Customer>();
	}
	
	public void CustomerVector() {
		this.CustomerVector = new Vector<Customer>();
	}
	
	public Vector<Customer> getCustomerVector() {
		return this.CustomerVector;
	}
	
	public boolean add(Customer Customer) {
		this.CustomerVector.add(Customer);
		return true;
	}

//	public boolean CheckSubscriptionStatus(int CustomerID) {
//		for (int i = 0; i < this.CustomerVector.size(); i++) {
//			Customer Targetcustomer = this.CustomerVector.get(i);
//			if ((Targetcustomer.getCustomerID() == CustomerID) && (Targetcustomer.isSubscriptionStatus() == true)) {
//		
//				return true;
//			}
//		}
//		return false;
//	}

	public boolean delete(int CustomerID) {
		for(Customer customer : this.CustomerVector){
			if (customer.getCustomerID() == CustomerID) {
				CustomerVector.remove(customer);
				return true;
			}
		}
		return false;
	}

	public Customer search(int CustomerID) {
		      for(Customer customer : this.CustomerVector){
		         if (customer.getCustomerID() == CustomerID) {
		            return customer;
		         }
		      }
		      return new Customer();
	}

//	public boolean SubscriptionApprovalOfNonSubscriber(int CustomerID) {
//		for(Customer customer : this.CustomerVector){
//			if (customer.getCustomerID() == CustomerID) {
//				customer.setSubscriptionStatus(true);
//				return true;
//			}
//		}
//		return false;
//	}
}