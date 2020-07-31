package Customer;

public interface CustomerInterface {

	public boolean add(Customer Customer);

//	public boolean CheckSubscriptionStatus(int CustomerID);

	public boolean delete(int CustomerID);

	public Customer search(int CustomerID);

//	public boolean SubscriptionApprovalOfNonSubscriber(int CustomerID);

}