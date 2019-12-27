import java.util.ArrayList;

import javax.mail.Message;
import javax.mail.Session;

public interface ApplicationLayerInterface {
	public boolean loginStoreManager(String username, String password);
	public boolean loginNormalEmployee(String username, String password);
	public ArrayList<String> getProducts(String sale_offer);
	public void updatePrice(String price, String product_id);
	public boolean giveLoyalityCard();
	public boolean giveFinancialApproval();
	public void setPurchaseInformation();
	public ArrayList<String> getPurchasesInformation();
	public void sendEmail();
	public ArrayList<String> monitorStock(); 
	public ArrayList<Integer> produceReport();
	public void registerStoreManager(String password, String username, String email_address);
	public void registerNormalEmployee(String password, String username, String email_address);
	public boolean validateUsername2(String username);
	public boolean validateEmailAddress2(String emailAddress); 
	public ArrayList<String> showCustomers();
	public ArrayList<String> produceMessages();
}
