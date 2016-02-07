package com.qbit.Objects;

import java.io.Serializable;

//This class represents the General Tab 
public class General implements Serializable{

	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public int getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(int timeZone) {
		this.timeZone = timeZone;
	}
	public String getFbUserName() {
		return fbUserName;
	}
	public void setFbUserName(String fbUserName) {
		this.fbUserName = fbUserName;
	}
	public String getTwitUserName() {
		return twitUserName;
	}
	public void setTwitUserName(String twitUserName) {
		this.twitUserName = twitUserName;
	}
	public boolean isLaunchStartUp() {
		return isLaunchStartUp;
	}
	public void setLaunchStartUp(boolean isLaunchStartUp) {
		this.isLaunchStartUp = isLaunchStartUp;
	}
	public boolean isMinimized() {
		return isMinimized;
	}
	public void setMinimized(boolean isMinimized) {
		this.isMinimized = isMinimized;
	}
	
	private String fullName;
	private String emailAddress;
	private int timeZone;
	private String fbUserName;
	private String twitUserName;
	private boolean isLaunchStartUp;
	private boolean isMinimized;
	private int ShowCount;  // Values 0 = no show 1= Total Words 2=Session Words 
	
	public int getShowCount() {
		return ShowCount;
	}
	public void setShowCount(int showCount) {
		ShowCount = showCount;
	}
	
	
}
