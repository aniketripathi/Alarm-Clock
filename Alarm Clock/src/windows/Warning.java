package windows;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import others.Config;
import others.SetAlarm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * This class creates a dialog box which is used to show a warning dialog box. The warning is to prevent the user
 * from exiting the application in case if he has set the alarm.
 * @author Aniket 
 *
 */
public class Warning extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -64223848668541418L;
	private final JPanel contentPanel = new JPanel();
	static Warning dialog;

	/**
	 * Create and launch the dialog box
	 */
	public static void main(String[] args) {
		try {
			dialog = new Warning();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Default Constructor. Set the basic properties of the dialog.
	 */
	public Warning() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Warning.class.getResource("/javax/swing/plaf/metal/icons/ocean/warning.png")));
		setTitle("Exit");
		setType(Type.POPUP);
		setBounds(100, 100, 352, 137);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblYouHaveSet = new JLabel("<html>You have set the alarm. The alarm won't work if you exit the application.Are you sure you want to exit?<html>");
			lblYouHaveSet.setBounds(10, 11, 310, 44);
			lblYouHaveSet.setFont(new Font("Tahoma", Font.PLAIN, 13));
			contentPanel.add(lblYouHaveSet);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 78, 342, 37);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(216, 5, 49, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						Config config = new Config();
						config.setAlarmStatus(false); 		// turn off the alarm
						SetAlarm.cancelTask();
						System.exit(1);	
						
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setBounds(270, 5, 67, 23);
				cancelButton.setMinimumSize(new Dimension(69, 23));
				cancelButton.setMaximumSize(new Dimension(69, 23));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						dialog.dispose();
						
					}
				});
				
			}
		}
	}

}
