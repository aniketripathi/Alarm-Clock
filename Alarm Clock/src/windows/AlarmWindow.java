package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import others.Config;
import others.SetAlarm;
import others.TrayHandler;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Calendar;


/**
 * This class is the parent class. This class creates the main application window. This window contains 3
 * basic parts. First one includes the alarm slider and the alarm time. Alarm Slider is used to set the alarm.
 * Alarm time is in 24 hour format. Second part includes setting or stopping of alarm. Setting of alarm is 
 * done by SetAlam class. The third part includes three options - Exit button to exit the application, Minimize
 * button to move the application to system tray. Tray handling is done by TrayHandler class. Third button is
 * the Settings button which opens a  new window created by Settings class. It handles user dependent settings.
 * @author Aniket
 *
 */
public class AlarmWindow {

	public static boolean isAlarmSet;
	private static JFrame frmAlarm;
	private static JButton btnSettings;
	private static JSlider minuteSlider;
	private static JSlider hourSlider;
	private static JLabel labelMinuteSD;
	private static JLabel labelMinuteFD;
	private static JLabel labelHourSD;
	private static JLabel labelHourFD;
	private static JLabel labeTimeSeparator;
	private static JButton btnMinimize;
	private static JButton btnExit;
	private static JButton btnStop;
	private static JButton btnSet;
	private static JPanel panel;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlarmWindow window = new AlarmWindow();
					window.frmAlarm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Default Constructor. It initialization of window is done by initialize() method.
	 */
	public AlarmWindow() {
		initialize();
	}

	
	
	/**
	 * This method initializes all the basic components of the window. It includes sliders, alarm time, buttons,
	 * panel, labels and icons. The action performed by components is also handled within this class using
	 * ActionListener. Closing this window will result in termination of application will minimizing will
	 * just move this window to system tray.
	 */
	private void initialize() {
		
		/**
		 * Extracting the previous saved alarm time
		 */
		final Config config = new Config();
		int hours = config.getTimeHours();
		int minutes = config.getTimeMinutes();
		isAlarmSet = config.getAlarmStatus();

		/*
		 *Adding Frame 
		 */
		frmAlarm = new JFrame();
		
		
		//setting icons
		try{
			 frmAlarm.setIconImage(new ImageIcon(AlarmMessage.class.getResource("/main/resources/icons/alarm.png")).getImage());
			 }
			 catch(NullPointerException e){
				 frmAlarm.setIconImage(null);
			 }
			
		frmAlarm.setResizable(false);
		frmAlarm.setTitle("ALARM");
		frmAlarm.setBounds(100, 100, 322, 264);
		frmAlarm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAlarm.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		
		panel = new JPanel();
		frmAlarm.getContentPane().add(panel);
		panel.setLayout(null);
		
		btnSet = new JButton("SET");
		btnSet.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
						Calendar calendar = Calendar.getInstance();
			// Find the set hour time and system hour time
			int shour = calendar.get(Calendar.HOUR_OF_DAY);
			int hours = hourSlider.getValue();
			
			// Find the set minute time and system minute time
			int minutes = minuteSlider.getValue();
			int sminutes = calendar.get(Calendar.MINUTE);
			
			int alarmMinutes = (hours - shour)*60 + (minutes - sminutes);
			if(alarmMinutes <= 0)  JOptionPane.showMessageDialog(null, "The time has already passed", "Set Alarm in Past?", JOptionPane.ERROR_MESSAGE);
			else {
				isAlarmSet = true;
				config.setTimeSetting(hours, minutes);
				config.setAlarmStatus(isAlarmSet); 
			SetAlarm.scheduleTask(alarmMinutes,calendar.get(Calendar.SECOND));
			isAlarmSet = true;
			AlarmWindow.updateWindow(config);
			}
			
			}
		});
		btnSet.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSet.setBounds(64, 146, 82, 29);
		panel.add(btnSet);
		btnSet.setEnabled(!isAlarmSet);
		
		btnStop = new JButton("STOP");
		btnStop.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStop.setBounds(168, 146, 90, 29);
		panel.add(btnStop);
        btnStop.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			isAlarmSet = false;
			config.setAlarmStatus(isAlarmSet);
			AlarmWindow.updateWindow(config);
			SetAlarm.cancelTask();
					
			}
		});
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(31, 196, 53, 28);
		panel.add(btnExit);
		btnExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(config.getAlarmStatus())		Warning.main(null);
				else
				{
					config.setAlarmStatus(false); 		// turn off the alarm
					SetAlarm.cancelTask();
					System.exit(1);	
			}}
		});
	

		btnMinimize = new JButton("Minimize");
		btnMinimize.setBounds(94, 196, 76, 28);
		panel.add(btnMinimize);
		btnMinimize.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				AlarmWindow.updateWindow(config);
				TrayHandler.moveToTray();
				frmAlarm.setVisible(false);

				
			}
		
		});
		
	

		labeTimeSeparator = new JLabel(":");
		labeTimeSeparator.setFont(new Font("Tahoma", Font.PLAIN, 30));
		labeTimeSeparator.setBounds(143, 34, 17, 66);
		panel.add(labeTimeSeparator);
		
		
		labelHourFD = new JLabel("0");
		labelHourFD.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 35));
		labelHourFD.setHorizontalAlignment(SwingConstants.CENTER);
		labelHourFD.setBounds(54, 48, 46, 52);
		panel.add(labelHourFD);
		labelHourFD.setText(new Integer(hours/10).toString());
		labelHourFD.setName("hourSecondDigit");
		
		labelHourSD = new JLabel("0");
		labelHourSD.setHorizontalAlignment(SwingConstants.CENTER);
		labelHourSD.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 35));
		labelHourSD.setBounds(100, 48, 46, 52);
		panel.add(labelHourSD);
		labelHourSD.setText(new Integer(hours%10).toString());
		labelHourSD.setName("hourFirstDigit");
		
		labelMinuteFD = new JLabel("0");
		labelMinuteFD.setHorizontalAlignment(SwingConstants.CENTER);
		labelMinuteFD.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 35));
		labelMinuteFD.setBounds(156, 48, 46, 52);
		panel.add(labelMinuteFD);
		labelMinuteFD.setText(new Integer(minutes/10).toString());
		labelMinuteFD.setName("minuteSecondDigit");
		
		labelMinuteSD = new JLabel("0");
		labelMinuteSD.setHorizontalAlignment(SwingConstants.CENTER);
		labelMinuteSD.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 35));
		labelMinuteSD.setBounds(200, 48, 46, 52);
		panel.add(labelMinuteSD);
		labelMinuteSD.setText(new Integer(minutes%10).toString());
		labelMinuteSD.setName("minuteFirstDigit");
		
		final int MAX_HOURS = 23;
		final int MIN_HOURS = 00;
		hourSlider = new JSlider(MIN_HOURS, MAX_HOURS, hours);
		
		/** Modifying value of labels(hours time) when hourSlider is moved **/
		
		hourSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
			
				int hours = hourSlider.getValue();
				labelHourSD.setText(new Integer(hours%10).toString());		
				labelHourFD.setText(new Integer(hours/10).toString());
				
				
			}
		});
		hourSlider.setName("hourSlider");
		hourSlider.setOrientation(SwingConstants.VERTICAL);
		hourSlider.setBounds(21, 28, 23, 147);
		panel.add(hourSlider);
		hourSlider.setEnabled(!isAlarmSet);
		
	
		
		final int MAX_MINUTES = 59;
		final int MIN_MINUTES = 00;
		minuteSlider = new JSlider(MIN_MINUTES, MAX_MINUTES, minutes);
		minuteSlider.setName("minuteSlider");
		minuteSlider.setOrientation(SwingConstants.VERTICAL);
		minuteSlider.setBounds(268, 28, 23, 147);
		panel.add(minuteSlider);
		hourSlider.setEnabled(!isAlarmSet);
		
		/** Modifying value of labels(minute time) when hourSlider is moved **/
		
		minuteSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
			
				int minutes = minuteSlider.getValue();
				labelMinuteSD.setText(new Integer(minutes%10).toString());		
				labelMinuteFD.setText(new Integer(minutes/10).toString());
				
				
			}
		});
		
		
		
		btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Settings.getInstance() == null)
				Settings.main(null);
				else {
					Settings sett = (Settings)Settings.getInstance();
					sett.setToFocus();
				}
					
			
			}
		});
		btnSettings.setBounds(198, 197, 73, 28);
		panel.add(btnSettings);
		
		
		frmAlarm.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				/**
				 * Update Window on focus gain
				 */
				AlarmWindow.updateWindow(config);
			}
		});
		
		
	}
	
	
	/**
	 * This method will restore the window if hidden. Used to restore window from system tray.
	 */
	public static void restore(){
		frmAlarm.setVisible(true);
		frmAlarm.requestFocus();
	}
	
	
	/**
	 * This method will is used to update the windows components. This method is required as some of the
	 * components like slider and set button are disabled to prevent multiple setting of alarm. To handle
	 * enable-disabling of these components after different operations this method is required
	 * @param config saved setting of the alarm clock application
	 */
	private static void updateWindow(Config config){
		btnSet.setEnabled(!isAlarmSet);
		hourSlider.setEnabled(!isAlarmSet);
		minuteSlider.setEnabled(!isAlarmSet);
		
	}
	
	
	}
