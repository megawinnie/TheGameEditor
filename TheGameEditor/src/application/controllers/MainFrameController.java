package application.controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import application.Main;
import application.core.AlertManager;
import application.data.Project;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;


public class MainFrameController {	
	
	private Main main;
	
	@FXML
	private MenuItem saveMenuItem;
	@FXML
	private MenuItem preferencesMenuItem;
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	private void handleOpenMenuItem() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Game Project files (*.gpf)", "*.gpf");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(this.main.getPrimaryStage());
		if(file != null && file.exists()) {
			try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getPath()))) {
				Project project = (Project) in.readObject();
				this.main.getProjectManager().openProjectFile(project);				
			} catch (IOException | ClassNotFoundException e) {
				AlertManager.alert("Ошибка открытия файла проекта", e.getMessage(), AlertType.ERROR);
			}
		}
	}
	
	@FXML
	private void handleNewMenuItem() {		
		Project newProject = new Project();
		if(this.main.showCreateNewProjectFrame(newProject)) {
			this.main.getProjectManager().createNewProject(newProject);
		}		
	}
	
	@FXML
	private void handleSaveMenuItem() {
		if(!this.main.getProjectManager().isCurrentProjectSaved()) {
			this.main.getProjectManager().saveProject();
		}
	}
	
	@FXML
	private void handlePreferencesMenuItem() {
		this.main.getProjectManager().setCurrentProjectSavedFlag(false);
		System.out.println(this.main.getProjectManager().getCurrentProject().getProjectFilePath());
	}
		
	@FXML
	private void handleExitMenuItem() {
		this.main.exit();
	}
	
	public void blockProjectMenu(boolean b) {
		this.saveMenuItem.setDisable(b);
		this.preferencesMenuItem.setDisable(b);
	}
	
}