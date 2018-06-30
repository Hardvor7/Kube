package kube.launcher;

import javafx.scene.control.Alert;

public class CustomAlert extends Alert{
	
	public CustomAlert(AlertType alertType, String title, String subject, String stackTrace) 
	{
		super(alertType);
		this.setTitle(title);
		this.setHeaderText(subject);
		this.setContentText(stackTrace);
		this.showAndWait();
	}
	
	// FAST CUSTOM ALERT
	
	public CustomAlert(String msg)
	{
		super(AlertType.INFORMATION);
		this.setContentText(msg);
		this.showAndWait();
	}

}
