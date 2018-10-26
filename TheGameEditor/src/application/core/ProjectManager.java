package application.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import application.data.Project;
import javafx.scene.control.Alert.AlertType;

public class ProjectManager {
	
	private Project currentProject;
	private boolean isCurrentProjectSaved;
	private ProjectStatusListener projectStatusListener;
	
	public ProjectManager() {
		
	}

	public Project getCurrentProject() {
		return this.currentProject;
	}	
	
	public boolean isCurrentProjectSaved() {
		return this.isCurrentProjectSaved;
	}
	
	//project status listener
	public void setProjectStatusListener(ProjectStatusListener projectStatusListener) {
		this.projectStatusListener = projectStatusListener;
	}
	
	private void setCurrentProject(Project project) {
		this.currentProject = project;
		if(this.projectStatusListener != null) {
			this.projectStatusListener.onSetProject();
		}
	}
	
	public void setCurrentProjectSavedFlag(boolean b) {
		this.isCurrentProjectSaved = b;
		if(b) {
			this.projectStatusListener.onRiseProjectSavedFlag();
		} else {
			this.projectStatusListener.onFallProjectSavedFlag();
		}
	}
	
	//methods for create, open and save project
	
	public void createNewProject(Project project) {
		if(project != null) {
			File file =  new File(project.getProjectFilePath());
			if(file.mkdir() && this.saveProjectFile(project)) {
				this.setCurrentProject(project);
				this.setCurrentProjectSavedFlag(true);
				AlertManager.alert("Создание нового проекта", "Проект успешно создан." , AlertType.INFORMATION);
			} else {
				AlertManager.alert("Ошибка создания папки проекта", "Не удалось создать папку проекта.", AlertType.ERROR);
			}
		}
	}
	
	public void saveProject() {
		if(this.currentProject != null && this.saveProjectFile(this.currentProject)) {
			this.setCurrentProjectSavedFlag(true);
			AlertManager.alert("Сохранение проекта", "Проект успешно сохранен." , AlertType.INFORMATION);
		}
	}
	
	private boolean saveProjectFile(Project project) {
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(project.getProjectFilePath() + "\\" + project.getName() + ".gpf"))) {
			out.writeObject(project);			
			return true;
		} catch (IOException e) {
			AlertManager.alert("Ошибка сохранения файла проекта", e.getMessage(), AlertType.ERROR);
			return false;
		}
	}
	
	public void openProjectFile(Project project) {
		this.setCurrentProject(project);
		this.setCurrentProjectSavedFlag(true);
	}
	
}
