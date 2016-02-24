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
	private boolean hidePreferencesOnStart;
	private int ShowCount;  // Values 0 = no show 1= Total Words 2=Session Words
	private boolean isActivated;
    private Date firstSaveDate;
	private String fbAccessToken;
	private String twAccessToken = "";
    private String twAccessSecret = "";

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
	public boolean isLaunchStartUp() {
		return isLaunchStartUp;
	}
	public void setLaunchStartUp(boolean isLaunchStartUp) {
		this.isLaunchStartUp = isLaunchStartUp;
	}
	public boolean isHidePreferencesOnStart() {
		return hidePreferencesOnStart;
	}

	public void setHidePreferencesOnStart(boolean isMinimized) {
		this.hidePreferencesOnStart = isMinimized;
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

    public String getFbAccessToken() {
        return fbAccessToken;
    }

    public void setFbAccessToken(String fbAccessToken) {
        this.fbAccessToken = fbAccessToken;
    }

    public String getTwAccessToken() {
        return twAccessToken;
    }

    public void setTwAccessToken(String twAccessToken) {
        this.twAccessToken = twAccessToken;
    }

    public String getTwAccessSecret() {
        return twAccessSecret;
    }

    public void setTwAccessSecret(String twAccessSecret) {
        this.twAccessSecret = twAccessSecret;
    }
}
