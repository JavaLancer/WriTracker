package com.qbit.Objects;

import java.io.Serializable;
import java.util.Date;

//This class represents the General Tab 
public class General implements Serializable{
    private static final long serialVersionUID = 1322923328352154553L;

	private String fullName;
	private String emailAddress;
	private int timeZone;
	private String fbUserName;
	private String twitUserName;
	private boolean isLaunchStartUp;
	private boolean isMinimized;
	private int ShowCount;  // Values 0 = no show 1= Total Words 2=Session Words
	private boolean isActivated;
    private Date firstSaveDate;
	private String accessToken;

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

	public int getShowCount() {
		return ShowCount;
	}
	public void setShowCount(int showCount) {
		ShowCount = showCount;
	}

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public Date getFirstSaveDate() {
        return firstSaveDate;
    }

    public void setFirstSaveDate(Date firstSaveDate) {
        this.firstSaveDate = firstSaveDate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
