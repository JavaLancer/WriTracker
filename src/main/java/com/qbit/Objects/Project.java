package com.qbit.Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project implements Serializable {
//    private static final long serialVersionUID = 1322923328352154553L;

    private String projectTitle;
    private int projectType;
    private Date projectDeadline;
    private int wordGoal;
    private boolean isPostSocMedia;
    private int currentWords;
    private int interval;
    private int wordsTillDate;
    private String reward1000;
    private String reward2000;
    private String reward5000;
    private String reward10000;
    private String rewardCompletion;
    private String rewardMilestone;
    private String penalty;
    private List<Integer> wordCountList;
	private int avgWordsPerPage;

    public List<Integer> getWordCountList() {
        if (wordCountList == null) {
            wordCountList = new ArrayList<>();
        }
        return wordCountList;
    }

    public void setWordCountList(List<Integer> wordCountList) {
        this.wordCountList = wordCountList;
    }

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

    public int getInterval() {
		return interval;
	}

    public void setInterval(int interval) {
		this.interval = interval;
	}

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

    public int getWordsTillDate() {
		return wordsTillDate;
	}

	public void setWordsTillDate(int wordsRemain) {
		this.wordsTillDate = wordsRemain;
	}

	public int getAvgWordsPerPage() {
		return avgWordsPerPage;
	}

	public void setAvgWordsPerPage(int avgWordsPerPage) {
		this.avgWordsPerPage = avgWordsPerPage;
	}
}
