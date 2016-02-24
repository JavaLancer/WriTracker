package com.qbit.Assignment;

import com.qbit.Objects.Project;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class RewardMgmt {
	
	private int projID;
	private Project project;
	
	public RewardMgmt(int projID){
		this.projID = projID;
		//get the Project
		String configPath = System.getenv("WRITRACK_HOME");
		if(configPath==null)
			configPath="C:\\Config";
		ObjectInputStream ois;
    	//
		try {
			FileInputStream fin = new FileInputStream(configPath+"\\project"+projID+".ser");
			ois = new ObjectInputStream(fin);
			project = (Project) ois.readObject();
			System.out.println("Proj Selected Title"+project.getProjectTitle());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
