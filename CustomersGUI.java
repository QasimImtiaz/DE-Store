import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomersGUI extends JFrame {
	private JPanel contentPane;
	static ApplicationLayer appLayer;
	/**
	 * Create the frame.
	 */
	public CustomersGUI() {
		DataLayer dataLayer = new DataLayer();
		appLayer = new ApplicationLayer(dataLayer);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 909, 385);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblCustomers = new JLabel("Customers ");
		lblCustomers.setBounds(395, 18, 103, 16);
		contentPane.add(lblCustomers);
		DefaultListModel<String> model = new DefaultListModel<>(); 
		JList<String> lstCustomers = new JList<>(model);
		lstCustomers.setBounds(12, 47, 840, 226);
		contentPane.add(lstCustomers);
		boolean flag = appLayer.giveLoyalityCard();
		boolean flag2 = appLayer.giveFinancialApproval();	
		JButton btnShowCustomers = new JButton("Show Customers");
		btnShowCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.clear(); 
				ArrayList<String> customers = appLayer.showCustomers();
				for(String customer : customers) {
					model.addElement(customer);
				}
			}
		});
		btnShowCustomers.setBounds(367, 299, 157, 25);
		contentPane.add(btnShowCustomers);
	}
}
