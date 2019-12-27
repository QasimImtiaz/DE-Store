import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JList;

public class ProductsGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtPrice;
	private JTextField txtProductId;
	/**
	 * @wbp.nonvisual location=63,-6
	 */

	static ApplicationLayer appLayer;
	public ProductsGUI() {
		DataLayer dataLayer = new DataLayer();
		appLayer = new ApplicationLayer(dataLayer);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 712, 593);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblProducts = new JLabel("Products");
		lblProducts.setBounds(299, 13, 56, 16);
		contentPane.add(lblProducts);
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setBounds(132, 83, 56, 16);
		contentPane.add(lblSearch);
		JTextArea txtSearch = new JTextArea();
		txtSearch.setBounds(201, 80, 261, 22);
		contentPane.add(txtSearch);
		JButton btnSearch = new JButton("Search");
		DefaultListModel<String> model = new DefaultListModel<>(); 
		JList<String> lstProducts = new JList<>(model);
		lstProducts.setBounds(22, 174, 604, 219);
		contentPane.add(lstProducts);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtSearch.getText() != null) {
					model.clear(); 
					ArrayList<String> products = appLayer.getProducts(txtSearch.getText());
					for(String product : products) {
						model.addElement(product);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Search Box is empty!"); 
				}
			}
		});
		btnSearch.setBounds(276, 116, 97, 25);
		contentPane.add(btnSearch);
		JLabel label = new JLabel("");
		label.setBounds(132, 148, 56, 16);
		contentPane.add(label);
		JLabel lblProductPrice = new JLabel("Product Price");
		lblProductPrice.setBounds(276, 406, 136, 16);
		contentPane.add(lblProductPrice);
		JLabel lblProductID = new JLabel("Product ID");
		lblProductID.setBounds(187, 435, 91, 16);
		contentPane.add(lblProductID);
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(382, 435, 56, 16);
		contentPane.add(lblPrice);
		txtPrice = new JTextField();
		txtPrice.setBounds(361, 473, 136, 22);
		contentPane.add(txtPrice);
		txtPrice.setColumns(10);
		txtProductId = new JTextField();
		txtProductId.setBounds(144, 473, 160, 22);
		contentPane.add(txtProductId);
		txtProductId.setColumns(10);
		JButton btnSetPrice = new JButton("Set Price");
		btnSetPrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtPrice.getText() != "" && txtProductId.getText() != "") {
					appLayer.updatePrice(txtPrice.getText(), txtProductId.getText());
					txtPrice.setText(""); 
					txtProductId.setText("");
				}
				else {
					JOptionPane.showMessageDialog(null, "The relevant textboxes are empty!"); 
				}
			}
		});
		btnSetPrice.setBounds(276, 508, 97, 25);
		contentPane.add(btnSetPrice);
	}
}
