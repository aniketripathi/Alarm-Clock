package windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.SpringLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class AlarmMessage extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AlarmMessage dialog = new AlarmMessage();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AlarmMessage() {
		setType(Type.UTILITY);
		setTitle("Alarm");
		setBounds(100, 100, 450, 265);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(45, 154, 287, 76);
			contentPanel.add(buttonPane);
			{
				JButton stopButton = new JButton("STOP");
				stopButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				});
				buttonPane.setLayout(new MigLayout("", "[287px]", "[76px]"));
				stopButton.setActionCommand("OK");
				buttonPane.add(stopButton, "cell 0 0,grow");
				getRootPane().setDefaultButton(stopButton);
			}
			{
				JButton cancelButton = new JButton("Extend 5 Minutes");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton, "cell 0 0,grow");
			}
		}
		
		JLabel lblNewLabel = new JLabel("<html>Alarm Message<html>");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Century Schoolbook", Font.PLAIN, 20));
		lblNewLabel.setBounds(34, 34, 322, 76);
		contentPanel.add(lblNewLabel);
	}
}
