package kube.utils;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomAlert{
	
	private CustomAnchorPane backPane;
	private CustomAnchorPane frontPane;
	private CustomLabel alertLabel;
	
	private CustomAnchorPane okButtonPane;
	private CustomAnchorPane okButton;
	private CustomLabel okLabel;
	
	private CustomLabel textArea;
	
	private final static double WIDTH = 400;
	private final static double HEIGHT = 215;
	
	public CustomAlert(AlertType type, String title, String text, String stack) 
	{
		
		Font customFont = Font.loadFont(getClass().getResourceAsStream("/kube/launcher/resources/Digital Dare.ttf"), 16);
		Stage primaryStage = new Stage();
		
		backPane = new CustomAnchorPane();
		backPane.setAllAnchors(0);
		backPane.setBackgroundImage("/kube/launcher/resources/bedrock.png", BackgroundRepeat.REPEAT, 32);
		
		frontPane = new CustomAnchorPane();
		frontPane.setAllAnchors(6);
		frontPane.setBackgroundColor(Color.rgb(0, 0, 0, .97));
		backPane.getChildren().add(frontPane.getFXPane());
		
		alertLabel = new CustomLabel("Erreur", Font.font(customFont.getName(), 24), Color.WHITE, 10, 10);
		frontPane.getChildren().add(alertLabel.getFXLabel());
		
		
		textArea = new CustomLabel("Intitulé : " + text + "\r\n" + "Message : " + stack , Font.getDefault(), Color.WHITE, 10, 50);
		textArea.setSize(WIDTH - 20, HEIGHT - 100);
		
		frontPane.getChildren().add(textArea.getFXLabel());
		
		
		switch(type) 
		{
		case Error:
			alertLabel.setFillColor(Color.RED);
			break;
		case Information:
			alertLabel.setFillColor(Color.ORANGE);
			break;
		default:
			alertLabel.setFillColor(Color.WHITE);
		}
		
		
		// Play Button
		
				okButtonPane = new CustomAnchorPane();
				okButtonPane.setPosition(WIDTH - 120, HEIGHT - 50);
				okButtonPane.setBackgroundImage("/kube/launcher/resources/Back.png", BackgroundRepeat.REPEAT, 32);
				okButtonPane.setSize(96, 32);
				frontPane.getChildren().add(okButtonPane.getFXPane());
				
				okButton = new CustomAnchorPane();
				okButton.setBackgroundColor(.9, 0, 0, 0);
				okButton.setAllAnchors(2);
				okButtonPane.getChildren().add(okButton.getFXPane());
				
				okLabel = new CustomLabel("Ok", customFont, Color.WHITE, okButton.getFXPane().getWidth() / 2 + 30, okButton.getFXPane().getHeight() / 2 + 5);
				okButton.getChildren().add(okLabel.getFXLabel());
				
				okButton.getFXPane().setOnMouseEntered(e->
				{
					okButton.setBackgroundColor(Color.rgb(0, 0, 0, .3));
					okButton.setCursor(Cursor.HAND);
				});
				okButton.getFXPane().setOnMouseExited(e->
				{
					okButton.setBackgroundColor(Color.rgb(0, 0, 0, .9));
					okButton.setCursor(Cursor.DEFAULT);
				});
				
				okButton.getFXPane().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
				{
					@Override
					public void handle(MouseEvent event) 
					{
						primaryStage.close();
					}
				});
		
		primaryStage.setTitle("Kube - Message");
		primaryStage.getIcons().add(new Image("/kube/launcher/resources/Logo v1.0_partial.png"));
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setScene(new Scene(backPane.getFXPane(), WIDTH, HEIGHT, javafx.scene.paint.Color.TRANSPARENT));
		primaryStage.show();
		
		
	}	
	/* public CustomAlert(AlertType alertType, String title, String subject, String stackTrace) 
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
	} */

	public enum AlertType{
		Error,
		Information,
		Success
	}
}
