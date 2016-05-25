package muthu.assign4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * This program reads a textfile phones.txt and shows a graphical representation of the data set
 * @author Kavitha
 *
 */
public class PhonesUsage extends JPanel {

	private float noOfPhones;
	private float population;
	public float perHundered;
	public Map<String, Float> bars = new LinkedHashMap<String, Float>();
	public Map<Color, String> coloredBars;
	public Map<Color, Float> coloredValues = new LinkedHashMap<Color, Float>();

	private String names, intVals, countryName;  
	private int nameWidth ,j, i,height;
	public ArrayList countriesArray = new ArrayList();
	private float k;
	private JComboBox<String> countriesNames;
	private PhonesUsage phonesDetails;
	private Map<String, PhonesUsage> dataValues = new LinkedHashMap<String, PhonesUsage>();


	/**
	 * 
	 * @param noOfPhones - number of phones in a country
	 * @param population - population of the country
	 * @param perHundered - phones per hundred in the country
	 * the three float values are passed
	 * noOfPhones and population values are read from a file and phones/100 value is computed
	 */
	public PhonesUsage(float noOfPhones, float population, float perHundered) {

		this.noOfPhones = noOfPhones;
		this.population = population;
		this.perHundered = perHundered;

	}

	/**
	 * construct that defines the panel layout 
	 */
	public PhonesUsage() {

		this.setPreferredSize(new Dimension(1200, 700));
		this.setAlignmentX(TOP_ALIGNMENT);
		this.setBackground(Color.white);
		this.setLayout(null);
	}
	/**
	 * 
	 * @param fromTxtFile - the text file being read
	 * @param toTxtFile - text file to which the data is written to
	 * @throws IOException
	 */
	public static void readWriteFile(File fromTxtFile, File toTxtFile)
			throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fromTxtFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(toTxtFile));

		
		String line = null;
		while ((line = reader.readLine()) != null) {		//while loop to read data continues until there is no data
			writer.write(line);
			writer.newLine();
		}

		reader.close();										//files are closed
		writer.close();
	}

	/**
	 * 
	 * @param txtFileName - file to be written, in this case it is the console
	 * @throws IOException
	 */
	public void displayData(String txtFileName) throws IOException {
		Scanner text = null;
		try {
			text = new Scanner(new BufferedReader(new FileReader(txtFileName)));
			text.useDelimiter("[,\\s]+");

			while (text.hasNext()) {

				countryName = text.next();

				countriesArray.add(countryName);			//country names are added to a array list 
				noOfPhones = text.nextFloat();				//no.of phones in the country is parsed and read
				population = text.nextFloat();				//population in the country is parsed and read
				perHundered = noOfPhones / population * 100; //phones per 100 population is computed

				
				System.out.println("Country: " + countryName + "     "
						+ "Phones/100: " + perHundered);

				bars.put(countryName, perHundered);		//hash map to store country name and phones/100

				

			}
			
			displayBars();

		} finally {
			if (text != null)						//close file where there is no data
				text.close();
		}

	}

/**
 * this method is used to generate random colors for the bars in the graph
 */
	public void displayBars() {

		coloredBars = new LinkedHashMap<Color, String>();
		for (i = 0; i < countriesArray.size(); i++) {

			Random rand = new Random();

			float r = rand.nextFloat();
			float g = rand.nextFloat() / 2f;
			float b = rand.nextFloat() / 2f;

			Color randomColor = new Color(r, g, b);
			randomColor.brighter();
			coloredBars.put(randomColor, countriesArray.get(i).toString()); //hash map to store color and country names
		}

		addBar();
		
	}

	/**
	 * this method defines the hash map, which contains colors and phones/100 population value
	 */
	public void addBar() {
		for (Color key : coloredBars.keySet()) {

			String value = coloredBars.get(key);

			coloredValues.put(key, bars.get(value));	//hash map that contains color and phones/100 population value.

		}

	}

	/**
	 * paint method to draw the bar graphs, label the graph,x-axis and y-axis
	 */
	@Override
	protected void paintComponent(Graphics g) {
	
		String title = "Visual representation of phone usage in the following countries";
		
		// longest bar in the graph is calculated
		Dimension d = getSize();
		int Width = d.width - 25;
		int Height = d.height;
		int barWidth = Width / coloredValues.size();

		//max and min values are determined
		float max = Float.MIN_VALUE;
		float min = Float.MAX_VALUE;
		for (Float dataValues : coloredValues.values()) {
			max = Math.max(max, dataValues);
			min = Math.min(min, dataValues);
		}
		// x-axis y-axis are drawn
		g.setColor(Color.RED);
		g.fillRect(25, 20, 1, 1199);
		g.drawLine(0, d.height - 25, d.width, d.height - 25);
		g.drawString("0", 5, d.height - 28);
		g.setColor(Color.GRAY);
		g.drawLine(0,  d.height - 175, d.width, d.height - 175);
		g.drawString("50",5, d.height - 178);
		g.drawLine(0,  d.height - 325, d.width, d.height - 325);
		g.drawString("100", 5, d.height - 328);
		g.drawLine(0,  d.height - 475, d.width, d.height - 475);
		g.drawString("150", 5, d.height - 478);
		g.drawLine(0,  d.height - 615, d.width, d.height - 615);
		g.drawString("200", 5, d.height - 618);
			
		
		//height of the bar is determined based the phones/100 value
		int width = (getWidth() / coloredValues.size()) - 50;
		int x = 28;
		for (Color color : coloredValues.keySet()) {
			float perHundered = coloredValues.get(color);
			height = (int) ((getHeight() - 80) * ((double) perHundered / max));
			g.setColor(color);
			g.fillRect(x, getHeight() - height, width, height - 25);
			g.setColor(Color.black);
			g.drawRect(x, getHeight() - height, width, height - 25);
			x += (width + 45);

		}
		//Font and format patterns for the title
		Font titleFont = new Font("Serif", Font.BOLD, 20);
		FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
		int titleWidth = titleFontMetrics.stringWidth(title);
		int y = titleFontMetrics.getAscent();
		int x1 = (Width - titleWidth) / 2;
		g.setFont(titleFont);
		g.drawString(title, x1, y);
		g.setColor(Color.BLACK);

		//Font and format pattern for country names
		Font labelFont = new Font("SansSerif", Font.BOLD, 15);
		FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

		if (countriesArray == null || countriesArray.size() == 0)
			return;

		int top = titleFontMetrics.getHeight();
		int bottom = labelFontMetrics.getHeight();
		if (max == min)
			return;
		double scale = (Height - top - bottom) / (max - min);
		y = Height - labelFontMetrics.getDescent();
		g.setFont(labelFont);

		//the country names are spaced out accordingly and drawn
		for (j = 0; j < coloredValues.size(); j++) {

			nameWidth = labelFontMetrics.stringWidth(countriesArray.get(j)
					.toString());
			x = j * barWidth + (barWidth - nameWidth - 50);
			g.drawString(countriesArray.get(j).toString(), x, y);
			
		}
		
	}
		

}
