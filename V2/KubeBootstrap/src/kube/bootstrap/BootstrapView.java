package kube.bootstrap;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import kube.bootstrap.CustomAnchorPane;

public class BootstrapView extends Application {
	
	private static final int WIDTH = 256;
	private static final int HEIGHT = 256;
	
	private CustomProgressBar progressbar;
	private CustomAnchorPane backPane;
	private CustomAnchorPane frontPane;
	private ImageView logoView;
	
	public CustomProgressBar getProgressbar() {
		return progressbar;
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
		
		// Load custom font
    	
    	Font customFont = Font.loadFont(getClass().getResourceAsStream("/kube/bootstrap/resources/Digital Dare.ttf"), 16);

    	
    	// Windows
    	
    	primaryStage.setTitle("Kube Bootstrap");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image("/kube/bootstrap/resources/Logo v1.0_partial.png"));
		
        // Back Pane
        
        backPane = new CustomAnchorPane();
        backPane.setBackgroundImage("/kube/bootstrap/resources/Back.png", BackgroundRepeat.REPEAT, 32);
        
        // front Pane
        
        frontPane = new CustomAnchorPane();
        frontPane.setAllAnchors(4);
        frontPane.setBackgroundColor(0.9, 16, 16, 16);
        backPane.getChildren().add(frontPane.pane);
        
        // Logo
        
        logoView = new ImageView();
		logoView.setImage(new Image(getClass().getResource("/kube/bootstrap/resources/Logo v1.0_partial.png").toString(), 256, 256, false, false));
		logoView.relocate(0, 0);
		frontPane.getChildren().add(logoView);
        
		
		// Progressbar
		
		progressbar = new CustomProgressBar(WIDTH - 42, 16, 2, "/kube/bootstrap/resources/Lava.gif", customFont, "/kube/bootstrap/resources/Back.png");
		progressbar.setPosition(16, HEIGHT - 40);
		
		frontPane.getChildren().add(progressbar.getFXProgressBar());
		
		
        primaryStage.setScene(new Scene(backPane.pane, WIDTH, HEIGHT, javafx.scene.paint.Color.TRANSPARENT));
       
        // FadeIn
        
        primaryStage.getScene().getRoot().setOpacity(0);
        primaryStage.show();
        Timeline timeline = new Timeline();
        KeyFrame key = new KeyFrame(Duration.millis(2000),
                       new KeyValue (primaryStage.getScene().getRoot().opacityProperty(), 1));
        timeline.getKeyFrames().add(key);
        timeline.play();
		
		routine();
	}
	
	
	
	private void routine() {

		Bootstrap.update();

		Bootstrap.launch();
		
	}
	
}
