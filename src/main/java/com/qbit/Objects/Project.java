package com.qbit.Objects;

import java.io.Serializable;
import java.util.Date;

//This class represents a single project tab.
//We will make 5 instances and stores those in the list.
public class Project implements Serializable {

	private String projectTitle;
	public String getProjectTitle() {
		return projectTitle;
	}
	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}
	public int getProjectType() {
		return projectType;
	}
	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}
	public Date getProjectDeadline() {
		return projectDeadline;
	}
	public void setProjectDeadline(Date projectDeadline) {
		this.projectDeadline = projectDeadline;
	}
	public int getWordGoal() {
		return wordGoal;
	}
	public void setWordGoal(int wordGoal) {
		this.wordGoal = wordGoal;
	}
	public boolean isPostSocMedia() {
		return isPostSocMedia;
	}
	public void setPostSocMedia(boolean isPostSocMedia) {
		this.isPostSocMedia = isPostSocMedia;
	}
	public int getCurrentWords() {
		return currentWords;
	}
	public void setCurrentWords(int currentWords) {
		this.currentWords = currentWords;
	}
	
	private int projectType;
	private Date projectDeadline;
	private int wordGoal;
	private boolean isPostSocMedia;
	private int currentWords;
	
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}

	private int interval;
	//rewards
	private String reward1000;
	public String getReward1000() {
		return reward1000;
	}
	public void setReward1000(String reward1000) {
		this.reward1000 = reward1000;
	}
	public String getReward2000() {
		return reward2000;
	}
	public void setReward2000(String reward2000) {
		this.reward2000 = reward2000;
	}
	public String getReward5000() {
		return reward5000;
	}
	public void setReward5000(String reward5000) {
		this.reward5000 = reward5000;
	}
	public String getReward10000() {
		return reward10000;
	}
	public void setReward10000(String reward10000) {
		this.reward10000 = reward10000;
	}
	public String getRewardCompletion() {
		return rewardCompletion;
	}
	public void setRewardCompletion(String rewardCompletion) {
		this.rewardCompletion = rewardCompletion;
	}
	public String getRewardMilestone() {
		return rewardMilestone;
	}
	public void setRewardMilestone(String rewardMilestone) {
		this.rewardMilestone = rewardMilestone;
	}
	public String getPenalty() {
		return penalty;
	}
	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	private String reward2000;
	private String reward5000;
	private String reward10000;
	private String rewardCompletion;
	private String rewardMilestone;
	private String penalty;
	public int getWordsTillDate() {
		return wordsTillDate;
	}
	public void setWordsTillDate(int wordsRemain) {
		this.wordsTillDate = wordsRemain;
	}

	private int wordsTillDate;
	
	
	
}
