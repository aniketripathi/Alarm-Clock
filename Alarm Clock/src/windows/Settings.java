package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import others.Config;

import javax.swing.UIManager;
import java.awt.Dimension;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.awt.Window.Type;

/**
 * This class creates a window used to change the user dependent settings of the application. This includes the alarm message and the
 * alarm tone. The alarm tone must be in *.wav format to be played. All settings are handled by Config class which deals with saving and
 * loading of settings.
 *
 * @author Aniket
 *
 */
public class Settings  {
	
	Config config;
	private JPanel contentPane;
	private JTextField txtTimesUp;
	private static JFrame frame;
	private static Settings window;
	private static  JComboBox<String> comboBox;
	private static  JCheckBox chckbxIncludeAlarmMessage;
	private static  JButton btnAddTone;
	private static  JButton btnApply;
	private static  JButton btnCancel;

	/**
	 * Launch the Settings Window.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			

			public void run() {
				
					window = new Settings();
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setVisible(true);
			}
		});
	}
	
	
	/**
	 * Method used to check whether the files in the folder is of *.wav type. Used as a filter method.
	 * @param fileName  name of file that needs to be checked
	 * @return true if file is of *.wav type and false for all other types
	 */
	private boolean acceptFile(String fileName){
		
		if(fileName != null)
			return fileName.toLowerCase().endsWith(".wav");
		
		return false;
		}
	
	
	/**
	 * Method used to add files from /main/resource/audio folder to the comboBox, adds only *.wav files. The files are first
	 * checked using acceptFile() method. This adopted is used from one of the forums of StackOverflow.
	 * @param comboBox  comboBox where files needs to be added
	 */
	private void addFiles(JComboBox<String> comboBox) {
	try{
		final String path = "main/resources/audio";
		final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

		if(jarFile.isFile()) {  									// Run with JAR file
		    final JarFile jar = new JarFile(jarFile);
		    final Enumeration<JarEntry> entries = jar.entries(); 		//gives ALL entries in jar
		    while(entries.hasMoreElements()) {
		        final String name = entries.nextElement().getName();
		        if (name.startsWith(path + "/")) { 						//filter according to the path
		            String tempname = name.substring(name.lastIndexOf("/")+1);  // extract only file name
		            if(acceptFile(name))
		            	comboBox.addItem(tempname);
		            
		        }
		    }
		    jar.close();
		} /** else { 															// Run with IDE
		    final URL url = Settings.class.getResource("/" + path);
		    if (url != null) {
		        try {
		            final File apps = new File(url.toURI());
		            for (File app : apps.listFiles()) {
		            	 String tempname = app.toString().substring(app.toString().lastIndexOf('\\')+1);  // extract only file name -use '\' as separator
				            if(acceptFile(tempname))
				            	comboBox.addItem(tempname);
		            }
		        } catch (URISyntaxException ex) {
		            // never happens
		        }
		    }
		} **/	}
	
	
	
	catch(Exception e){
		JOptionPane.showInputDialog("error" + e.getMessage() + e.getLocalizedMessage());
	}
		
	
	}
	
		
		
	

	/**
	 * Default Constructor. Sets the basic components of window. Also the settings will be loaded from the
	 * Config class. The previous settings of user will be set. In case of first time use or no setting available
	 *  default settings will be loaded as mention in Config class.
	 */
	
	public Settings() {
	config = new Config();
	/**
	 * Loading previous settings
	 */
		String fileName = config.getFileName();
		String alarmMessage = config.getAlarmMessage();
		boolean includeMessage = config.getIncludeMessageStatus();
	/**
	 * Setting Attributes of the Settings Window
	 */
		frame = new JFrame();
		frame.setType(Type.UTILITY);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Settings");
		frame.setBounds(100, 100, 357, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/**
		 * OK button
		 */
		btnApply = new JButton("OK");
		btnApply.setMinimumSize(new Dimension(69, 23));
		btnApply.setMaximumSize(new Dimension(69, 23));
		btnApply.setPreferredSize(new Dimension(69, 23));
		btnApply.setBounds(161, 153, 70, 23);
		contentPane.add(btnApply);
		btnApply.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				config.setSettings((String) comboBox.getSelectedItem(), chckbxIncludeAlarmMessage.isSelected(), txtTimesUp.getText());
				Settings.close();
				
			}
		});
		
		
		/**
		 * Cancel Button 
		 */
		
		btnCancel = new JButton("Cancel");
		btnCancel.setMinimumSize(new Dimension(69, 23));
		btnCancel.setMaximumSize(new Dimension(69, 23));
		btnCancel.setPreferredSize(new Dimension(69, 23));
		contentPane.add(btnCancel);
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Settings.close();
				
			}
		});		btnCancel.setBounds(277, 153, 70, 23);
		
		
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(90, 8, 167, 22);
		contentPane.add(comboBox);
		addFiles(comboBox);							//adding filenames
		if(fileName != null)
		comboBox.setSelectedItem(fileName); 		// Applying previous settings 
		
		
		
		
		/**
		 * Alarm Tone Label of ComboBx
		 */
		JLabel lblAlarmTone = new JLabel("Alarm Tone");
		lblAlarmTone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAlarmTone.setBounds(10, 11, 70, 14);
		contentPane.add(lblAlarmTone);
		
		chckbxIncludeAlarmMessage = new JCheckBox("Include Alarm Message");
		chckbxIncludeAlarmMessage.setBounds(21, 52, 135, 23);
		chckbxIncludeAlarmMessage.setSelected(includeMessage);
		chckbxIncludeAlarmMessage.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				txtTimesUp.setEnabled(chckbxIncludeAlarmMessage.isSelected());
			
			}
		});
		contentPane.add(chckbxIncludeAlarmMessage);
				
		
		/**
		 * Add Tone button
		 */
		btnAddTone = new JButton("Add Tone");
		btnAddTone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			AddTone.main(null);
				
			}
		});
		btnAddTone.setBorder(UIManager.getBorder("Button.border"));
		btnAddTone.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddTone.setIconTextGap(0);
		contentPane.add(btnAddTone);
		btnAddTone.setBounds(30, 137, 91, 34);
		
		
		/**
		 * 
		 * Alarm Message TextField
		 */
		txtTimesUp = new JTextField();
		txtTimesUp.setFont(new Font("Microsoft New Tai Lue", Font.PLAIN, 20));
		txtTimesUp.setText(alarmMessage);		// Applying previous settings
		txtTimesUp.setForeground(Color.BLACK);
		txtTimesUp.setSelectionColor(Color.WHITE);
		txtTimesUp.setBackground(Color.WHITE);
		txtTimesUp.setBounds(20, 82, 315, 44);
		contentPane.add(txtTimesUp);
		txtTimesUp.setColumns(10);
		txtTimesUp.setEnabled(includeMessage);
		
	}
	
	
	
	
	/**
	 * Closes and disposes the window
	 */
	private static void close(){
		frame.dispose();
	}
	
	
	/**
	 * To give the present instance of window.
	 * @return The present instance of Settings window. In case of absence of instance, null is returned which is default value.
	 */
	public static Settings getInstance(){
	return window;
	}
	
	/**
	 * To give the present window focus or attention to use
	 */
	public void setToFocus(){
	frame.setVisible(true);	
	frame.requestFocus();
	}
	
	
	
	
}
