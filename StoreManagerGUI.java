import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;

public class StoreManagerGUI extends JFrame {
	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public StoreManagerGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 217);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblStoreManager = new JLabel("Store Manager Menu");
		lblStoreManager.setBounds(170, 13, 146, 16);
		contentPane.add(lblStoreManager);
		JButton btnProducts = new JButton("Products");
		btnProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StoreManagerGUI sm = new StoreManagerGUI(); 
				sm.setVisible(false); 
				ProductsGUI products = new ProductsGUI();
				products.setVisible(true);
			}
		});
		btnProducts.setBounds(232, 42, 97, 25);
		contentPane.add(btnProducts);
		JButton btnStock= new JButton("Stock");
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StockGUI stock = new StockGUI();
				stock.setVisible(true);
			}
		});
		btnStock.setBounds(123, 80, 97, 25);
		contentPane.add(btnStock);
		JButton btnCustomers = new JButton("Customers");
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StoreManagerGUI sm = new StoreManagerGUI(); 
				sm.setVisible(false); 
				CustomersGUI customers = new CustomersGUI();
				customers.setVisible(true);
			}
		});
		btnCustomers.setBounds(232, 80, 97, 25);
		contentPane.add(btnCustomers);
		JButton btnPurchaseInformation = new JButton("Reports");
		btnPurchaseInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReportGUI pi = new ReportGUI();
				pi.setVisible(true);
			}
		});
		btnPurchaseInformation.setBounds(123, 42, 97, 25);
		contentPane.add(btnPurchaseInformation);
	}
}

