package application;
	
import java.io.IOException;

import application.controllers.MainFrameController;
import application.controllers.NewProjectFrameController;
import application.core.AlertManager;
import application.core.ProjectManager;
import application.core.ProjectStatusListener;
import application.data.Project;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	private final String applicationName = "TheGameEditor 0.1";
	private ProjectManager projectManager;
	private MainFrameController mainFrameController;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setMinWidth(800);
		this.primaryStage.setMinHeight(600);
		this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				exit();
				event.consume();
			}
		});
		this.primaryStage.setTitle(this.applicationName);
		this.showMainFrame();
		this.projectManager = new ProjectManager();
		this.projectManager.setProjectStatusListener(new ProjectStatusListener() {
			
			@Override
			public void onSetProject() {
				setTitle(projectManager.getCurrentProject().getName());
				mainFrameController.blockProjectMenu(false);
			}
			
			@Override
			public void onRiseProjectSavedFlag() {
				setTitle(projectManager.getCurrentProject().getName());
			}
			
			@Override
			public void onFallProjectSavedFlag() {
				setTitle("*" + projectManager.getCurrentProject().getName());
			}
		});
	}
	
	private void showMainFrame() {	
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/application/fxml/MainFrame.fxml"));
			this.rootLayout = (BorderPane) loader.load();
			
			this.mainFrameController = loader.getController();
			this.mainFrameController.setMain(this);
			
			Scene scene = new Scene(this.rootLayout);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch (IOException e) {
			AlertManager.alert("Ошибка открытия формы", e.getMessage(), AlertType.ERROR);
		}
	}
	
	public boolean showCreateNewProjectFrame(Project project) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/application/fxml/NewProjectFrame.fxml"));
			AnchorPane frame = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Новый проект");
			dialogStage.setResizable(false);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.primaryStage);
			Scene scene = new Scene(frame);
			dialogStage.setScene(scene);			
			
			NewProjectFrameController controller = loader.getController();			
			controller.setDialogStage(dialogStage);
			controller.setProject(project);
			
			dialogStage.showAndWait();
			return controller.isCreateClicked();
		} catch (IOException e) {
			AlertManager.alert("FXML loader", "Ошибка загрузки формы.\n" + e.getMessage(), AlertType.ERROR);
			return false;
		}
	}
	
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	public ProjectManager getProjectManager() {
		return this.projectManager;
	}
	
	private void setTitle(String title) {
		this.primaryStage.setTitle(this.applicationName + " - " + title);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void exit() {
		if(AlertManager.alert("Закрытие приложения", "Вы уверены, что хотите закрыть приложение?", AlertType.CONFIRMATION)) {
			Platform.exit();
			System.exit(0);
		}
	}
}
