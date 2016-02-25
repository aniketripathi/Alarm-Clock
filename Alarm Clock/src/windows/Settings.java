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

/**
 * This class creates a window used to change the user dependent settings of the application. This includes the alarm message and the
 * alarm tone. The alarm tone must be in *.wav format to be played. All settings are handled by Config class which deals with saving and
 * loading of settings.
 *
 * @author Aniket
 *
 */
public class Settings extends JFrame implements ActionListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7764463873463374178L;
	Config config;
	private JPanel contentPane;
	private JTextField txtTimesUp;
	private static Settings frame;

	/**
	 * Launch the Settings Window.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			

			public void run() {
				try {
					frame = new Settings();
					frame.setVisible(true);
					frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Method used to check whether the files in the folder is of *.wav type. Used as a filter method.
	 * @param fileName  name of file that needs to be checked
	 * @return true if file is of *.wav type and false for all other types
	 */
	private boolean acceptFile(String fileName){
		 return fileName.toLowerCase().endsWith(".wav");
	}
	
	
	/**
	 * Method used to add files from /main/resource/audio folder to the comboBox, adds only *.wav files. The files are first
	 * checked using acceptFile() method.
	 * @param comboBox  comboBox where files needs to be added
	 */
	private void addFiles(JComboBox<String> comboBox) {
		File folder;
		try {
		 URL url = Settings.class.getResource("/main/resources/audio");
		  folder = new File( url.toURI());
		}
		catch(URISyntaxException e){
		folder = null;	
		}
		if(folder != null){
		String files[] =folder.list();
		for(String file:files){
			if(acceptFile(file)) {
				comboBox.addItem(file);
			}
		}
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
		boolean includeMessage = config.getIncludeMessageStatus();
		String alarmMessage = config.getAlarmMessage();
		
	/**
	 * Setting Attributes of the Settings Window
	 */
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Settings");
		setType(Type.UTILITY);
		setBounds(100, 100, 357, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * Apply Button
		 */
		JButton btnApply = new JButton("OK");
		btnApply.setMinimumSize(new Dimension(69, 23));
		btnApply.setMaximumSize(new Dimension(69, 23));
		btnApply.setPreferredSize(new Dimension(69, 23));
		btnApply.setBounds(161, 153, 70, 23);
		contentPane.add(btnApply);
		btnApply.addActionListener(this);
		btnApply.setActionCommand("0");
		/**
		 * Cancel Button 
		 */
		btnApply.setActionCommand("1");
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setMinimumSize(new Dimension(69, 23));
		btnCancel.setMaximumSize(new Dimension(69, 23));
		btnCancel.setPreferredSize(new Dimension(69, 23));
		contentPane.add(btnCancel);
		btnCancel.addActionListener(this);
		btnCancel.setBounds(277, 153, 70, 23);
		
		
		
		/** ComboBox 
		 *  
		 */
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(90, 8, 167, 22);
		contentPane.add(comboBox);
		addFiles(comboBox); 			//adding filenames
		comboBox.setSelectedItem(fileName); 		// Applying previous settings 
		btnApply.setActionCommand("2");
		btnApply.setActionCommand("3");
		
		
		
		/**
		 * Alarm Tone Label of ComboBx
		 */
		JLabel lblAlarmTone = new JLabel("Alarm Tone");
		lblAlarmTone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAlarmTone.setBounds(10, 11, 70, 14);
		contentPane.add(lblAlarmTone);
		
		/**
		 * Include Alarm Message CheckBox
		 */
		final JCheckBox chckbxIncludeAlarmMessage = new JCheckBox("Include Alarm Message");
		chckbxIncludeAlarmMessage.setBounds(20, 46, 155, 23);
		contentPane.add(chckbxIncludeAlarmMessage);
		chckbxIncludeAlarmMessage.setSelected(includeMessage); 			// Applying previous settings
		chckbxIncludeAlarmMessage.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				txtTimesUp.setEditable(chckbxIncludeAlarmMessage.isSelected());
			}
		});
		
		/**
		 * Add Tone Button
		 */
		JButton btnAddTone = new JButton("Add Tone");
		btnAddTone.setBorder(UIManager.getBorder("Button.border"));
		btnAddTone.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddTone.setIconTextGap(0);
		contentPane.add(btnAddTone);
		btnAddTone.addActionListener(this);
		btnAddTone.setBounds(30, 137, 91, 34);
		btnApply.setActionCommand("5");
		
		/**
		 * 
		 * Alarm Message TextField
		 */
		txtTimesUp = new JTextField();
		txtTimesUp.setEnabled(chckbxIncludeAlarmMessage.isSelected());  // check if text Box is selected
		txtTimesUp.setFont(new Font("Microsoft New Tai Lue", Font.PLAIN, 20));
		txtTimesUp.setText(alarmMessage);		// Applying previous settings
		txtTimesUp.setForeground(Color.BLACK);
		txtTimesUp.setSelectionColor(Color.WHITE);
		txtTimesUp.setBackground(Color.WHITE);
		txtTimesUp.setBounds(20, 82, 315, 44);
		contentPane.add(txtTimesUp);
		txtTimesUp.setColumns(10);
		
	}
	
	
	
	
	/**
	 * Method to describe various actions of various components
	 * @param e It is the ActionEvent object.
	 */
	
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e){
		JButton button = null;
		try {
		 button = (JButton)e.getSource();
		}
		catch(Exception notConvertible){
			
		}
		String buttonText = button.getText();
		/**
		 * Comparison done using their textValues 
		 */
		if(buttonText.equals("OK")) {
		// We find the parent and then extract information from the respective components of parent	
		
			JPanel parent = (JPanel) button.getParent();
		String fileName = ((JComboBox<String>)parent.getComponent(2)).getSelectedItem().toString();
		boolean includeMessage =   ((JCheckBox)parent.getComponent(4)).isSelected();
		String AlarmMessage = ((JTextField)parent.getComponent(6)).getText();
		config.setSettings(fileName, includeMessage, AlarmMessage);
		frame.close();
		}
		
		else
		if(buttonText.equals("Cancel")) {
			frame.close();
				
			}
		else
		if(buttonText.equals("Add Tone")) {
			AddTone.main(null);;		
				}	
		
	}
	/**
	 * Closes and disposes the window
	 */
	private void close(){
		this.dispose();
	}
	
	
	/**
	 * To give the present instance of window.
	 * @return The present instance of Settings window. In case of absence of instance, null is returned which is default value.
	 */
	public static Settings getInstance(){
	return frame;
	}
	
	/**
	 * To give the present window focus or attention to use
	 */
	public void setToFocus(){
	frame.setVisible(true);	
	frame.requestFocus();
	}
	
	
	
}
