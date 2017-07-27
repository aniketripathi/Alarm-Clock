/**
 * 
 */
package others;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import windows.AlarmWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This class is used to move to the application to system tray and handle the corresponding operations
 * @author Aniket
 *
 */

public class TrayHandler {

/**
 * This method moves the AlarmWindow to the system tray. It will create two menu items
 * "Exit" and "Restore" for exiting the application or restoring the main window.
 */
public static void moveToTray(){
	/** Check if SystemTray is supported **/
	if(!SystemTray.isSupported()) {
		JOptionPane.showMessageDialog(null, "System Tray not supported." ,"Error", 4);
	
	}
	else {
		// Supported
		
		final SystemTray tray = SystemTray.getSystemTray();
		PopupMenu menu = new PopupMenu();
		final TrayIcon trayIcon = new TrayIcon(new ImageIcon(TrayIcon.class.getResource("/main/resources/icons/alarm.png")).getImage(),"Alarm",menu);
		
		/** Create restore MenuItem and set its behavior **/
		MenuItem restore = new MenuItem("Restore");
		restore.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				AlarmWindow.restore();
				tray.remove(trayIcon);
			}
		});
		
		/** Create exit MenuItem and set its behavior**/
		MenuItem exit = new MenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Config config = new Config();		
				config.setAlarmStatus(false);			// save that alarm is turned off
				System.exit(1);
			}
		});
		
		/** Add the MenuItems **/
		menu.add(restore);
		menu.add(exit);
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			JOptionPane.showMessageDialog(null, "System Tray not supported." ,"Fatal Error", 4);
		}
		
	}
		
	
}
	

}
