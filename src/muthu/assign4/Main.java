package muthu.assign4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main extends JFrame {
	
	private PhonesUsage phonesUsage,phones ;
	private Map<String,PhonesUsage> dataValues = new LinkedHashMap<String, PhonesUsage>();
	private JPanel mainPanel; 
	private String names;
	private String key1;

	/**
	 * constructor defines the structure of the layout
	 */
	public Main(){

		
		fetchData();
		JPanel mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(1200,800));
		mainPanel.setBackground(Color.WHITE);
		mainPanel.add(phonesUsage);
		getContentPane().setBackground(Color.WHITE); 
		
		setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(mainPanel);
		pack();
		setVisible(true);
	}	
	/**
	 * this method fetches the data from the text file
	 */
	public void fetchData(){
		phonesUsage = new PhonesUsage();
		
		
	    try {
	    	phonesUsage.displayData("Resource/phones.txt");
	    	
	    } catch (IOException e) {
	        System.err.println(e);
	        System.exit(1);
	    }
	    
	    
	}	
	/**
	 * Main method to instantiate the constructor
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {

		new Main();

	}
}
