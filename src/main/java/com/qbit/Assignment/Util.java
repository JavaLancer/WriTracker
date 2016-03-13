package com.qbit.Assignment;

import com.qbit.Objects.Project;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Util {
	
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	public static int checkMilestoneReward(Project project){
		int durationInDays = (int)Util.getDateDiff(new Date(),project.getProjectDeadline(), TimeUnit.DAYS);
		if (durationInDays <= 0) {
				durationInDays = 1;
		}
		int intervalType = project.getInterval();
		int milestoneWordCount = 0;
		int wordsremaining = project.getWordGoal() - project.getCurrentWords();
		switch (intervalType) {
		case 0: //i.e. days
			milestoneWordCount = wordsremaining / durationInDays;
			break;
		case 1: //weekly
			milestoneWordCount = wordsremaining / (durationInDays/7);
			break;
		case 2: //monthly
			milestoneWordCount = wordsremaining / (durationInDays / 30);
			break;	
		default:
			break;
		}
		
		return milestoneWordCount;
		
	}
	
	public static void main(String args[]){
		Date date1 = new Date();
		date1.setYear(115);
		date1.setMonth(5);
		date1.setDate(1);
		
		Date date2 = new Date();
		System.out.println(getDateDiff(date1,date2, TimeUnit.DAYS));
	}
	

}
