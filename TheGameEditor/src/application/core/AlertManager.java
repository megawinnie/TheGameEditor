package application.core;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class AlertManager {
	public static boolean alert(String title, String message, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		if (type == AlertType.CONFIRMATION) {
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				return true;
			} else {
				return false;
			}
		} else {
			alert.showAndWait();
			return true;
		}
	}
}
