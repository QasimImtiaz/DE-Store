import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

public class Report extends JFrame {
	private DataLayer dataLayer = new DataLayer();
	private ApplicationLayer appLayer = new ApplicationLayer(dataLayer);
	public Report(String appTitle, String chartTitle) {
		PieDataset dataset = createDataset(); 
		JFreeChart chart = createChart(dataset, chartTitle); 
		ChartPanel chartPanel = new ChartPanel(chart); 
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 300)); 
		setContentPane(chartPanel); 
	}
	
	private PieDataset createDataset() {
		DefaultPieDataset result = new DefaultPieDataset(); 
		ArrayList<Integer> report = appLayer.produceReport();
		result.setValue("Total Profit", report.get(0));
		result.setValue("Number of Purchases", report.get(1));
		result.setValue("Number of Customers", report.get(2));
		return result; 
	}
	
	private JFreeChart createChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false); 
		PiePlot3D plot = (PiePlot3D) chart.getPlot(); 
		plot.setStartAngle(0);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart; 
	}
}
