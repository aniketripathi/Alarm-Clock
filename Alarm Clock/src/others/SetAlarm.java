package others;

import java.util.*;

import javax.swing.JOptionPane;

import windows.AlarmMessage;
/**
 * This class is used to handle scheduling operations of alarm. 
 * @author Aniket
 *
 */
public  class SetAlarm {

	private static Timer timer;
	
	
	/**
	 * This method will schedule the alarm after the given alarm time from present time.
	 * @param minutes  minutes after which the alarm should be closed
	 * @param seconds extra seconds which crept in when the alarm was set. It number of seconds that has passed
	 * before we set the alarm. Since our alarm must begin as soon as the given minutes is reached and not minute
	 * passed, we must subtract this value to start the alarm closest to 00 seconds of that minute.
	 */
	public static void scheduleTask(int minutes, int seconds){
		 timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
			    // run the task
				  
				  AlarmMessage.main(null);
				  JOptionPane.showConfirmDialog(null, "ReallY");
			  }
			}, ((minutes * 60)-seconds) * 1000  );	
	}
	
	/**
	 * This method extends the time. Basically it schedules a new task.
	 * @param minutes  minutes to be extended
	 */
	public static void extendTask(int minutes){
		 timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
			    // run the task
				  
				  AlarmMessage.main(null);
				  JOptionPane.showConfirmDialog(null, "ReallY");
			  }
			}, ((minutes * 60)) * 1000  );	
	}
	
	
	/**
	 * Cancel or stop the alarm task
	 */
	public static void cancelTask(){
		if(timer != null)
		timer.cancel();
	}
}
