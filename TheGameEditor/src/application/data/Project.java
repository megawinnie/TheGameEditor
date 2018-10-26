package application.data;

import java.io.Serializable;

public class Project implements Serializable {
		
	private String name;
	private String projectFilePath;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getProjectFilePath() {
		return this.projectFilePath;
	}
	
	public void setProjectFilePath(String projectFilePath) {
		this.projectFilePath = projectFilePath;
	}
	
}
