/**
 * 
 */
package others;

import java.util.prefs.Preferences;

/**
 * This class is used for handling of settings. Most of the settings are user dependent but all are not
 * user controlled i.e. directly controlled by user at different situation. It saves and loads different
 * settings. This class uses Preferences class for that purpose. 
 * @author Aniket
 * 
 */
public class Config {
	
    private Preferences preferences;
	private final String ID1 = "FILE";				 // name of audio file
	private final String ID3 = "INCLUDEMESSAGE";	   //  whether to include alarm message or not
	private final String ID4 = "ALARMTEXT";				// saves the alarm text
	private final String ID5 = "HOURS";					// hour time of alarm
	private final String ID6 = "MINUTES";				// minutes time of alarm
	private final String ID7 = "ALARMSTATUS";			// whether alarm was on or off
	
	/**
	 * Parameterized Constructor. Save the defined settings.
	 * @param file name of the audio file
	 * @param includeMessage stores whether to include alarm message
	 * 
	 */
	public Config(String file, boolean includeMessage, String AlarmMessage){
		preferences = Preferences.userNodeForPackage(this.getClass());
		if(file != null)			preferences.put(ID1, file);
										preferences.putBoolean(ID3, includeMessage);
	    if(AlarmMessage != null)		preferences.put(ID4, AlarmMessage);
	}
	
	
	
	/**
	 * Empty constructor. 
	 */
	public Config(){
		preferences = Preferences.userNodeForPackage(this.getClass());
		
	}
	
	
	/**
	 * Constructor to set alarm time. Saves only alarm time.
	 * @param hours  hour part of alarm time
	 * @param minutes minutes part of alarm time
	 */
	public Config(int hours, int minutes){
		preferences = Preferences.userNodeForPackage(this.getClass());
		preferences.putInt(ID5, hours);
		preferences.putInt(ID6, minutes);
	}
	
	
	
	/**
	 * Method to set the settings to default values. By default include message is true and
	 * alarm Message is Time's Up!
	 */
	public void setDefault(){
		preferences.putBoolean(ID3, true);
	    preferences.put(ID4, "Time's Up!");
	}
	
	
	/**
	 * Method that saves the gives setting
	 * @param file name of the audio file
	 * @param includeMessage stores whether to include alarm message
	 */
	public void setSettings(String file, boolean includeMessage, String AlarmMessage ){
		if(file != null)		preferences.put(ID1, file);
									preferences.putBoolean(ID3, includeMessage);
		if(AlarmMessage != null)	preferences.put(ID4, AlarmMessage);
	}
	
	
	/**
	 * To save the alarm time
	 * @param hours  save hour part of time
	 * @param minutes save minute part of time
	 */
	public void setTimeSetting(int hours, int minutes){
		preferences.putInt(ID5, hours);
		preferences.putInt(ID6, minutes);
	}
	
	
	/**
	 * Saves the Alarm Status
	 * @param alarmStatus  present alarm status to be saved
	 */
	public void setAlarmStatus(boolean alarmStatus){
		preferences.putBoolean(ID7, alarmStatus);
	}

	
	/**
	 * Returns name of audio file previously saved.
	 * @return name of audio file
	 */
	public String getFile(){
		return preferences.get(ID1, null);
	}
	
	
	/**
	 * Returns whether to include alarm message or not as previously saved.
	 * @return boolean value regarding the status of including message
	 */
	public boolean getIncludeMessageStatus(){
		return preferences.getBoolean(ID3, true);
	}
	
	/**
	 * Returns the Alarm Message as previously saved.
	 * @return Alarm Message if any message exists else returns default message Time's Up!
	 */
	public String getAlarmMessage(){
		return preferences.get(ID4, "Time's Up!");
	}
	
	/**
	 * Returns Hour part of saved alarm time as previously saved.
	 * @return
	 */
	public int getTimeHours(){
		return preferences.getInt(ID5, 10);
	}
	
	/**
	 * Returns Minute part of alarm time as previously saved.
	 * @return
	 */
	public int getTimeMinutes(){
		return preferences.getInt(ID6, 45);
	}
	
	/**
	 * Returns Alarm status i.e. whether alarm is set or not as previously saved.
	 * @return
	 */
	public boolean getAlarmStatus(){
		return preferences.getBoolean(ID7, false);
	}
	
	
}
