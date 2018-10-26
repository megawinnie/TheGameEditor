package application.controllers;

import java.io.File;
import application.core.AlertManager;
import application.data.Project;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class NewProjectFrameController {
	
	private Stage dialogStage;
	private Project project;
	private boolean isCreateClicked = false;
	@FXML
	private TextField projectNameTextField;
	@FXML
	private TextField projectFolderTextField;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public boolean isCreateClicked() {
		return this.isCreateClicked;
	}
	
	@FXML
	private void handleChooseFolderButton() {
		DirectoryChooser folderChooser = new DirectoryChooser();
		File file = folderChooser.showDialog(this.dialogStage);
		if(file != null) {
			this.projectFolderTextField.setText(file.getPath());
		}
	}
	
	@FXML
	private void handleCreateButton() {
		if(this.isInputValid()) {
			this.project.setName(projectNameTextField.getText());
			this.project.setProjectFilePath(projectFolderTextField.getText() + "\\" + projectNameTextField.getText());
			this.isCreateClicked = true;
			this.dialogStage.close();
		}
	}
	
	@FXML
	private void handleCancelButton() {
		this.dialogStage.close();
	}
	
	private boolean isInputValid() {
		String errMessage = "";
		
		if(this.projectNameTextField == null || this.projectNameTextField.getText().length() == 0) {
			errMessage += "Не указано наименование проекта.\n";
		}
		if(this.projectFolderTextField == null || this.projectFolderTextField.getText().length() == 0) {
			errMessage += "Не указан путь к папке проекта.\n";
		}
		if(!this.isProjectNameValid()) {
			errMessage += "Наименование проекта не должно содержать следующих символов:\n . / \\ , : * & \" < > [ ] { } и символ пробела";
		}
		if(this.isProjectExists()) {
			errMessage += "Проект с таким наименованием уже существует в указанной папке проекта.";
		}
		
		if(errMessage.length() == 0) {
			return true;
		} else {
			AlertManager.alert("Ошибка заполнения полей", errMessage, AlertType.ERROR);
			return false;
		}
	}
	
	private boolean isProjectExists() {
		File file = new File(this.projectFolderTextField.getText() + "\\" + this.projectNameTextField.getText());
		if(file.exists() && file.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isProjectNameValid() {
		if(this.projectNameTextField.getText().contains(".") || 
				this.projectNameTextField.getText().contains(" ") || 
				this.projectNameTextField.getText().contains("/") || 
				this.projectNameTextField.getText().contains("\\") ||
				this.projectNameTextField.getText().contains(",") || 
				this.projectNameTextField.getText().contains(":") || 
				this.projectNameTextField.getText().contains("*") || 
				this.projectNameTextField.getText().contains("?") || 
				this.projectNameTextField.getText().contains("\"") || 
				this.projectNameTextField.getText().contains("<") || 
				this.projectNameTextField.getText().contains(">") || 
				this.projectNameTextField.getText().contains("|") ||
				this.projectNameTextField.getText().contains("[") || 
				this.projectNameTextField.getText().contains("]") || 
				this.projectNameTextField.getText().contains("{") || 
				this.projectNameTextField.getText().contains("}")) {
			return false;
		} else {
			return true;
		}
	}
}
