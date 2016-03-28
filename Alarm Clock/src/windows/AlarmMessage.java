package windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import others.Config;
import others.SetAlarm;

/**
 * This class creates dialog box which displays the alarm message. It also handles the playing of the alarm
 * tone with the dialog box. The dialog box contains an option to extend the alarm to 5 minutes which is also
 * done by this class using SetAlarm class in others package.
 * @author Aniket
 *
 */
public class AlarmMessage extends JDialog {

	private static final long serialVersionUID = -1155129616187968447L;
	/**
	 * 
	 */
	private final JPanel contentPanel = new JPanel();
	private Clip clip;
	private static AlarmMessage dialog;
	
	
	/**
	 * This method will launch a new dialog box
	 */
	public static void main(String[] args) {
		try {
			dialog = new AlarmMessage();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Default Constructor. It will create the dialog box with the default settings. The default setting
	 * consists of the user dependent and user independent settings. User dependent settings includes the 
	 * alarm tone to be played and the message to be displayed. This settings are loaded from Config class
	 * which handles the different user settings. User independent settings include basic architecture of the
	 * dialog box. The extending of the alarm will be done using SetAlarm class.
	 */
	public AlarmMessage() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		
		/**
		 * Loading the default setting required
		 */
		final Config config = new Config();
		boolean includeMessage = config.getIncludeMessageStatus();
		String alarmText = config.getAlarmMessage();
		String fileName = config.getFileName();
		
		
	/**
	 * Creating the dialog box	
	 */
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				 setType(Type.POPUP);
				 
				 try{
				 setIconImage(new ImageIcon(AlarmMessage.class.getResource("/main/resources/icons/alarm.png")).getImage());
				 }
				 catch(NullPointerException e){
					 setIconImage(null);
				 }
				 
		setTitle("Alarm");
		setBounds(100, 100, 352, 245);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(21, 117, 287, 76);
			contentPanel.add(buttonPane);
			{
				JButton stopButton = new JButton("STOP");
				stopButton.setFont(new Font("Tahoma", Font.BOLD, 16));
				stopButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					config.setAlarmStatus(false);
					AlarmMessage.close(clip);
					System.exit(1);
					
					}
				});
				buttonPane.setLayout(new MigLayout("", "[287px]", "[76px]"));
				stopButton.setActionCommand("OK");
				buttonPane.add(stopButton, "cell 0 0,grow");
				getRootPane().setDefaultButton(stopButton);
			}
			{
				/**
				 * Extend button to extend alarm
				 */
				JButton extendButton = new JButton("Extend 5 Minutes");
				extendButton.setFont(new Font("Tahoma", Font.BOLD, 16));
				extendButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					 SetAlarm.extendTask(5);
					 AlarmMessage.close(clip);
						
					}
				});
				
				buttonPane.add(extendButton, "cell 0 0,grow");
			}
		}
		
		/**
		 * Only include message if allowed in settings
		 */
		if(includeMessage)  alarmText = "<html>" + alarmText + "<html>";
		else				alarmText = "";
			
		JLabel lblNewLabel = new JLabel(alarmText);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Century Schoolbook", Font.PLAIN, 30));
		lblNewLabel.setBounds(10, 11, 315, 84);
		contentPanel.add(lblNewLabel);
	
	
		/**
		 * Playing the audio file
		 */

		
	try{
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(AlarmMessage.class.getResource("/main/resources/audio/"+fileName));
		 clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	catch( Exception soundException){
		// Do nothing if sound can't be played
	}
	
	
	}
	
	
	
	
	/**
	 * This method will shut down the alarm tone and close the dialog box. Here closing refers to disposing
	 * of dialog box.
	 */
	private static void close(Clip clip){
		clip.stop();
		clip.close();
		dialog.dispose();
		AlarmWindow.restore();
	
	}
	
}
