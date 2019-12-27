import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;


public class ReportGUI extends JFrame {

	private JPanel contentPane;
	static ApplicationLayer appLayer;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public ReportGUI() {
		DataLayer dataLayer = new DataLayer();
		appLayer = new ApplicationLayer(dataLayer);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPurchaseInformation = new JLabel("Report");
		lblPurchaseInformation.setBounds(182, 34, 191, 16);
		contentPane.add(lblPurchaseInformation);
		DefaultListModel<String> model = new DefaultListModel<>(); 
		JList<String> lstPurchaseInfomation = new JList<>(model);
		lstPurchaseInfomation.setBounds(24, 63, 398, 219);
		contentPane.add(lstPurchaseInfomation);
		
		JButton btnPurchaseInformation = new JButton("Show Purchases Information");
		btnPurchaseInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.clear();
				
				ArrayList<String> products = appLayer.getPurchasesInformation();
				for(String product : products) {
					model.addElement(product);
				}
			}
		});
		btnPurchaseInformation.setBounds(24, 307, 218, 25);
		contentPane.add(btnPurchaseInformation);
		
		JButton btnProduceReport = new JButton("Produce Report");
		DefaultListModel<String> model2 = new DefaultListModel<>();
		btnProduceReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Report report = new Report("Report", "Summary of Company Performance");
				report.pack(); 
				report.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				report.setVisible(true);
				//model2.clear(); 
				//ArrayList<Integer> report2 = appLayer.produceReport();
				//for(int content : report2) {
					//model2.addElement(Integer.toString(content));
				//}
				
				
			}
		});
		btnProduceReport.setBounds(268, 307, 145, 25);
		contentPane.add(btnProduceReport);
		
		
	}
}
