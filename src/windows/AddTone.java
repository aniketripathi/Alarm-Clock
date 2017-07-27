package windows;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Toolkit;


/**
 * This class creates a dialog box that displays the procedure to add the tone to the application.
 * AddTone dialog box is an informative dialog box.
 * 
 * @author Aniket
 *
 */
public class AddTone extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8437983874015512227L;
	private final JPanel contentPanel = new JPanel();
	private JLabel informationText;

	
	/**
	 * This method will launch the dialog box
	 */
	public static void main(String[] args) {
		try {
			AddTone dialog = new AddTone();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Default Constructor. It will create the dialog box with its default settings.
	 */
	public AddTone() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// setting icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(AddTone.class.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		
		setTitle("Add Tone");
		setBounds(100, 100, 352, 137);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JButton okButton = new JButton("OK");
			okButton.setBounds(235, 76, 59, 23);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				dispose();
				}
			});
			contentPanel.setLayout(null);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		
		informationText = new JLabel();
		informationText.setFont(new Font("Tahoma", Font.PLAIN, 13));
		informationText.setBounds(10, 11, 324, 50);
		informationText.setBackground(SystemColor.activeCaptionBorder);
		informationText.setText("<html>To add the tone just copy the tone into the folder Audio in source directory. The files must be of .wav format. The files will be automatically added.<html>");
		contentPanel.add(informationText);
	}
}
