import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class DataLayer implements DataLayerInterface {
	Connection conn = SQLConnection.dbConnector();
	Connection conn2 = SQLConnection.dbConnector2();
	private static String secretKey = "boooooooooom!!!!";
	private static String salt = "ssshhhhhhhhhhh!!!!";
	
	private String decryptPassword(String password) {
		try
	    {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
	        SecretKey tmp = factory.generateSecret(spec);
	        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
	        return new String(cipher.doFinal(Base64.getDecoder().decode(password)));
	    } 
	    catch (Exception e) {
	        System.out.println("Error while decrypting: " + e.toString());
	    }
	    return null;
	}
	
	public boolean loginStoreManager(String username, String password) {
		String query = "select * from storemanagers where username=? ";
		PreparedStatement pst;
		boolean validation = false; 
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, username );
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {	
				String encryptedPassword = rs.getString("Password");
				String decryptedPassword = decryptPassword(encryptedPassword);
				System.out.println("Decrypted password is " + decryptedPassword); 
				System.out.println("Password is " + password); 
				if(decryptedPassword.equals(password)) {
					validation = true; 
				}			
			}
			System.out.println("Validation is " + Boolean.toString(validation)); 
			rs.close();
			pst.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return validation; 
	}
	
	public boolean loginNormalEmployee(String username, String password) {
		String query = "select * from employees where username=? ";
		PreparedStatement pst;
		boolean validation = false; 
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, username );
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {	
				String encryptedPassword = rs.getString("Password");
				String decryptedPassword = decryptPassword(encryptedPassword);
				System.out.println("Decrypted password is " + decryptedPassword); 
				System.out.println("Password is " + password); 
				if(decryptedPassword.equals(password)) {
					validation = true; 
				}	
			}
			System.out.println("Validation is " + Boolean.toString(validation)); 
			rs.close();
			pst.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return validation; 
	}
	
	public ArrayList<String> getProducts(String sale_offer) {
		ArrayList<String> products = new ArrayList<>(); 
		try {
			String query = "select * from products where sale_offer=? ";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, sale_offer );
			ResultSet rs = pst.executeQuery();
			int count = 0; 
			while(rs.next()) {
				count = count + 1; 
				String product_id = rs.getString("product_id");
				String productName = rs.getString("product_name");
				String saleOffer = rs.getString("sale_offer");
				int product_price = rs.getInt("product_price");
				int quantity = rs.getInt("quantity");
				products.add("Product Id: " + product_id + " Product Name: " +  productName + " Price: " + product_price + " Quantity: " + quantity + " Sale Offer: " + saleOffer);
			}
			rs.close();
			pst.close(); 
		}
		
		catch(Exception pe) {
			JOptionPane.showMessageDialog(null, pe); 
		}
		return products; 
	}
	
	public void updatePrice(String price, String product_id) {
		try {
			String sql = "UPDATE products SET product_price = ? WHERE product_id = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(price));
			ps.setInt(2, Integer.parseInt(product_id));
			ps.executeUpdate();
			ps.close(); 
		}
		catch(Exception pe) {
			System.out.println(pe); 
		}
	}
	public boolean giveLoyalityCard() {
		boolean flag = false;
		try {
			String query = "SELECT NumberOfProductsBrought FROM customers  ";
			PreparedStatement ps2 = conn.prepareStatement(query);
			ResultSet rset = ps2.executeQuery();
			while(rset.next()) {
				int num = rset.getInt(1);	
				if(num >= 50) {
					String query2 = "UPDATE customers SET LoyaltyCard = ?  ";
					PreparedStatement ps = conn.prepareStatement(query2);
					ps.setString(1, "50% Loyalty Card");		
					ps.executeUpdate();
					ps.close(); 
					flag = true;
				}
				else {
					flag = false;
				}
			}
			rset.close();
			ps2.close();
		}
		catch(Exception pe) {
			System.out.println(pe); 
		}
		return flag; 
	}
	
	public boolean giveFinancialApproval() {
		boolean flag = false;
		try {
			String sql = "SELECT credit FROM customers ";
			PreparedStatement ps2 = conn.prepareStatement(sql);
			ResultSet rset = ps2.executeQuery();
			String credit = "";
			while(rset.next()) {
				credit = rset.getString(1);
				if(credit.equals("Good")) {
					String query = "UPDATE customers SET finance_approval = ? ";
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setString(1, "Yes");
					ps.executeUpdate();
					ps.close(); 
					flag = true;
				}
				else {
					String query = "UPDATE customers SET finance_approval = ? ";
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setString(1, "No");
					ps.executeUpdate();
					ps.close();
					flag = false;	
				}
			}
			rset.close();
			ps2.close();
		}
		catch(Exception pe) {
			System.out.println(pe); 
		}
		return flag;
	}
	
	public void setPurchaseInformation(){
		try {
			String sql3 = "SELECT * FROM purchase_information";
			PreparedStatement ps3 = conn2.prepareStatement(sql3);
			ResultSet rs2 = ps3.executeQuery();
			while(rs2.next()) {
				int purchase_id = rs2.getInt("purchase_id");
				String sql2 = "DELETE from purchase_information WHERE purchase_id = ?";
				PreparedStatement ps2 = conn2.prepareStatement(sql2);
				ps2.setInt(1, purchase_id);
				ps2.execute();
				ps2.close();
			}
			ps3.close();
			rs2.close();
			String sql = "SELECT * FROM products";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String sql2 = "INSERT INTO purchase_information(phone_number, product_id, product_name, product_quantity) VALUES (?,?,?,?)";
				int phoneNumber = rs.getInt("phone_number");
				int product_id = rs.getInt("product_id");
				String productName = rs.getString("product_name");
				int quantity = rs.getInt("quantity");
				PreparedStatement ps2 = conn2.prepareStatement(sql2);
				ps2.setInt(1,phoneNumber);
				ps2.setInt(2, product_id);
				ps2.setString(3, productName);
				ps2.setInt(4, quantity);
				ps2.execute();
				ps2.close(); 
			}
			rs.close();
			ps.close(); 
		}
		catch(Exception pe) {
			System.out.println(pe); 
		}
		
	}
	
	public ArrayList<String> getPurchasesInformation() {
		ArrayList<String> purchases = new ArrayList<String>(); 
		try {
			String sql = "SELECT * FROM purchases";
			PreparedStatement ps = conn2.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int purchaseId = rs.getInt("purchase_id");
				int phoneNumber = rs.getInt("phone_number");
				String productName = rs.getString("product_name");
				int quantity = rs.getInt("product_quantity");
				int total_paid = rs.getInt("total_paid");
				purchases.add("Purchase Id: " + purchaseId + " Phone Number: " + phoneNumber +  " Product Name " + productName + " Quantity: " + quantity + " Total Paid: " + total_paid);
			}
			rs.close();
			ps.close(); 
		}
		catch(Exception pe) {
			System.out.println(pe); 
		}
		
		return purchases; 
	}
	
	public int totalProfit() {
		int total_profit = 0; 
		try {
			String sql = "SELECT * FROM purchases";
			PreparedStatement ps = conn2.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int total_paid = rs.getInt("total_paid");
				total_profit += total_paid; 			
			}
			rs.close();
			ps.close(); 
		}
		catch(Exception pe) {
			System.out.println(pe); 
		}
		return total_profit; 
	}
	
	public int number_of_products_brought() {
		int number_of_products_brought = 0; 
		try {
			String sql = "SELECT * FROM purchases";
			PreparedStatement ps = conn2.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int quantity = rs.getInt("product_quantity");
				number_of_products_brought += quantity; 
			}
			rs.close();
			ps.close(); 	
		}
		catch(Exception pe) {
			System.out.println(pe); 
		}	
		return number_of_products_brought;
	}
	
	public int number_of_customers() {
		int number_of_customers = 0; 
		try {
			String sql = "SELECT phone_number FROM purchases";
			PreparedStatement ps = conn2.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				number_of_customers++; 
			}
			rs.close();
			ps.close(); 
		}
		catch(Exception pe) {
			System.out.println(pe); 
		}
		return number_of_customers;
	}
	
	public ArrayList<String> monitorStock() {
		ArrayList<String> products = new ArrayList<>(); 
		try {
			String query = "select * from products";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				int quantity = rs.getInt("quantity");
				if(quantity < 5) {
					String productName = rs.getString("product_name");
					String productId = rs.getString("product_id");
					products.add(productId);
					products.add(productName);
					products.add(Integer.toString(quantity));
				}
			}
			rs.close();
			pst.close();
			
		}
		catch(Exception pe) {
			JOptionPane.showMessageDialog(null, pe); 
		}
		
		return products; 
	}
	
	public ArrayList<String> sendEmailAddresses() {
		ArrayList<String> emailAddresses = new ArrayList<String>(); 
		try {
			String query = "select * from storemanagers";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				
				String email_address = rs.getString("Email_Address");
				emailAddresses.add(email_address);
			}
			rs.close();
			pst.close();	
		}
		catch(Exception pe) {
			JOptionPane.showMessageDialog(null, pe); 
		}
		return emailAddresses;
	}
	
	public ArrayList<String> monitorStock2() {
		ArrayList<String> products = new ArrayList<>(); 
		try {
			String query = "select * from products";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				int quantity = rs.getInt("quantity");
				String productName = rs.getString("product_name");
				String productId = rs.getString("product_id");	
				products.add("Product: " + productName + "(" + productId + ") " + "Quantity: " + quantity);
			}
			rs.close();
			pst.close();
		}
		catch(Exception pe) {
			JOptionPane.showMessageDialog(null, pe); 
		}
		return products; 
	}
	
	private static String encryptPassword( String password) {
		try {
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
	        SecretKey tmp = factory.generateSecret(spec);
	        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");    
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
	        return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes("UTF-8")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	

	public void registerStoreManager(String password, String username, String email_address){
		
		String query = " insert into storemanagers(username, password, email_address) " + "values(?,?,?) ";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(query);
			String encryptedPassword = encryptPassword(password); 
			pst.setString(1, username );
			pst.setString(2, encryptedPassword);
			pst.setString(3,  email_address);
			pst.execute(); 
			pst.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean validateUsername(String username) {
		boolean notExist = false;  
		String query = "select * from storemanagers where username=?  ";
		PreparedStatement pst;
		int count = 0; 
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, username );
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				count = count + 1; 
			}
			if(count >= 1) {
				notExist = false;
			}
			else {
				notExist = true; 
			}
			rs.close();
			pst.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return notExist;
		
	}
	
	public boolean validateEmailAddress(String emailAddress) {
		boolean notExist = false;  
		String query = "select * from storemanagers where email_address=?  ";
		PreparedStatement pst;
		int count = 0; 
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, emailAddress );
			ResultSet rs = pst.executeQuery();	
			while(rs.next()) {
				count = count + 1; 
			}
			if(count >= 1) {
				notExist = false;
			}
			else {
				notExist = true;
			}
			rs.close();
			pst.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notExist;
	}
	
	public void registerNormalEmployee(String password, String username, String email_address){
		
		String query = " insert into employees(username, password, email_address) " + "values(?,?,?) ";
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(query);
			String encryptedPassword = encryptPassword(password); 
			pst.setString(1, username );
			pst.setString(2, encryptedPassword);
			pst.setString(3,  email_address);
			pst.execute(); 
			pst.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean validateUsername2(String username) {
		boolean notExist = false;  
		String query = "select * from employees where username=?  ";
		PreparedStatement pst;
		int count = 0; 
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, username );
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				count = count + 1; 
			}
			if(count >= 1) {
				notExist = false;
			}
			else {
				notExist = true; 
			}
			rs.close();
			pst.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return notExist;
		
	}
	
	public boolean validateEmailAddress2(String emailAddress) {
		boolean notExist = false;  
		String query = "select * from employees where email_address=?  ";
		PreparedStatement pst;
		int count = 0; 
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, emailAddress );
			ResultSet rs = pst.executeQuery();	
			while(rs.next()) {
				count = count + 1; 
			}
			if(count >= 1) {
				notExist = false;
			}
			else {
				notExist = true;
			}
			rs.close();
			pst.close(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notExist;
	}
	
	public ArrayList<String> showCustomers() {
		ArrayList<String> customers = new ArrayList<>(); 
		try {
			String query = "select * from customers";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				int phoneNumber = rs.getInt("phone_number");
				int number_of_products_brought = rs.getInt("NumberOfProductsBrought");
				String LoyaltyCard = rs.getString("LoyaltyCard");
				String financeApproval = rs.getString("finance_approval");
				String credit = rs.getString("Credit");
				customers.add("Phone Number : " + phoneNumber + " Num of Purchases: " + number_of_products_brought + " LoyaltyCard: " + LoyaltyCard + " Finance Approval: " + financeApproval + " Credit: " + credit);
				
			}
			rs.close();
			pst.close(); 
		}
		catch(Exception pe) {
			JOptionPane.showMessageDialog(null, pe); 
		}
		
		return customers; 
	}
}
