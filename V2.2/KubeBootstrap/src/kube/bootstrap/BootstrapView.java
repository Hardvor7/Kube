package kube.bootstrap;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import kube.utils.CustomAnchorPane;
import kube.utils.CustomProgressBar;

public class BootstrapView extends Application {
	
	private static final int WIDTH = 320;
	private static final int HEIGHT = 100;
	
	private CustomProgressBar progressbar;
	private CustomAnchorPane backPane;
	private ImageView logoView;
	private Label loadLabel;
	
	
	private Stage primaryStage;
	
	public CustomProgressBar getProgressbar() {
		return progressbar;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	
	
	public static void main(String[] args)
	{
		if (args.length >= 1)
		{
			if (args[0].equals("local"))
			{
				Bootstrap.setServerAddress("192.168.1.25");
			}
		}
		
		launch("");

	}
	
	
	@Override
    public void start(Stage primaryStage) {
		
		Bootstrap.setBootstrapView(this);
		this.primaryStage = primaryStage;
    	
		Font customFont = Font.loadFont(getClass().getResourceAsStream("/kube/bootstrap/resources/Digital Dare.ttf"), 16);
		
    	// Windows
    	
    	primaryStage.setTitle("Kube Bootstrap");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image("/kube/bootstrap/resources/Logo v1.0_partial.png"));
		
        // Back Pane
        
        backPane = new CustomAnchorPane();
        backPane.setBackgroundColor(1, 0, 0, 0);
        
        // Logo
        
        logoView = new ImageView();
		logoView.setImage(new Image(getClass().getResource("/kube/bootstrap/resources/Bootstrap.png").toString(), 320, 100, false, false));
		logoView.relocate(0, 0);
		backPane.getChildren().add(logoView);
        
		
		loadLabel = new javafx.scene.control.Label("Chargement...");
		loadLabel.setFont(customFont);
		loadLabel.setStyle("-fx-text-fill: #FFFFFF");
		loadLabel.relocate(WIDTH - 250, HEIGHT - 20);
		backPane.getChildren().add(loadLabel);
		
		// Progressbar
		
		//progressbar = new CustomProgressBar(WIDTH - 42, 16, 2, "/kube/bootstrap/resources/Lava.gif", customFont, "/kube/bootstrap/resources/Back.png");
		//progressbar.setPosition(16, HEIGHT - 40);
		
		//frontPane.getChildren().add(progressbar.getFXProgressBar());
		
		
        primaryStage.setScene(new Scene(backPane.getFXPane(), WIDTH, HEIGHT, javafx.scene.paint.Color.TRANSPARENT));
       
        // FadeIn
        
        primaryStage.getScene().getRoot().setOpacity(0);
        primaryStage.show();
        Timeline timeline = new Timeline();
        KeyFrame key = new KeyFrame(Duration.millis(500),
                       new KeyValue (primaryStage.getScene().getRoot().opacityProperty(), 1));
        timeline.getKeyFrames().add(key);
        timeline.play();
        
        
        
        
        // 
        
        
		Thread t = new Thread(){
			
			@Override
			public void run() {
				Bootstrap.update();

				Bootstrap.launch();
			}
			
		};
		t.start();
		
	}
	
}
