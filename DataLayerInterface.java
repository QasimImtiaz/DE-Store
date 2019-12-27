import java.util.ArrayList;

public interface DataLayerInterface {
	public boolean loginStoreManager(String username, String password);
	public boolean loginNormalEmployee(String username, String password);
	public ArrayList<String> getProducts(String sale_offer);
	public void updatePrice(String price, String product_id);
	public boolean giveLoyalityCard();
	public boolean giveFinancialApproval();
	public void setPurchaseInformation();
	public ArrayList<String> getPurchasesInformation();
	public ArrayList<String> monitorStock();
	public ArrayList<String> sendEmailAddresses();
	public ArrayList<String> monitorStock2();
	public int totalProfit();
	public int number_of_products_brought();
	public int number_of_customers();
	public void registerStoreManager(String password, String username, String email_address);
	public boolean validateUsername(String username);
	public boolean validateEmailAddress(String emailAddress);
	public ArrayList<String> showCustomers();
	public void registerNormalEmployee(String password, String username, String email_address);
	public boolean validateUsername2(String username);
	public boolean validateEmailAddress2(String emailAddress);
}
