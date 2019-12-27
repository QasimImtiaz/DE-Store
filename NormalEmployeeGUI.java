import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NormalEmployeeGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public NormalEmployeeGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 384, 144);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		JLabel lblNormalEmployee = new JLabel("Employee Menu");
		lblNormalEmployee.setBounds(108, 13, 158, 16);
		contentPane.add(lblNormalEmployee);
		JButton btnCustomers = new JButton("Customers");
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CustomersGUI customers = new CustomersGUI();
				customers.setVisible(true);
			}
		});
		btnCustomers.setBounds(26, 42, 97, 25);
		contentPane.add(btnCustomers);
		JButton btnPurchaseInformation = new JButton("Report");
		btnPurchaseInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReportGUI pi = new ReportGUI();
				pi.setVisible(true);
			}
		});
		btnPurchaseInformation.setBounds(26, 42, 97, 25);
		contentPane.add(btnPurchaseInformation);
		JButton btnStock = new JButton("Stock");
		btnStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StockGUI sg = new StockGUI(); 
				sg.setVisible(true);
			}
		});
		btnStock.setBounds(143, 42, 97, 25);
		contentPane.add(btnStock);
	}
}
