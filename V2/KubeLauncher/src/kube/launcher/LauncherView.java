package kube.launcher;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileOutputStream;
import javafx.util.Duration;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.LaunchException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundRepeat;



public class LauncherView extends Application {
	
	public static final int WIDTH = 512;
	public static final int HEIGHT = 256;
	
	Properties prop = new Properties();
	
	public static LauncherView instance;
	
	public CustomProgressBar progressBar;
	
	public MediaPlayer audioPlayer;
	
	public javafx.scene.control.Label infoLabel;
	
	public Stage primaryStage;
	
	
	
	private CustomAnchorPane backPane;
	private CustomAnchorPane videoPane;
	private MediaView videoViewer;
	private CustomAnchorPane fadePane;
	private ImageView logoView;
	private TextField nameField;
	private TextField passField;
	private CustomAnchorPane optionButton;
	
	private javafx.scene.control.Label passLabel;
	private javafx.scene.control.Label nameLabel;
	private javafx.scene.control.Label quitLabel;
	private javafx.scene.control.Label minimizeLabel;
	private javafx.scene.control.Label soundLabel;
	private CustomAnchorPane playButtonPane;
	private CustomAnchorPane playButton;
	private javafx.scene.control.Label playLabel;
	private CustomAnchorPane optionPane;
	private javafx.scene.control.Label optionTitleLabel;
	private javafx.scene.control.Label optionSoundLabel;
	private javafx.scene.control.Label optionSoundLabelPercentage;
	private Slider soundSlider;
	private ChoiceBox<String> ramChoice ;
	private javafx.scene.control.Label ramChoiceLabel;
	private javafx.scene.control.Label backLabel;
	
	private Timer timer;
	public TimerTask task;
	public Slider getSoundSlider() {
		return soundSlider;
	}
	
	public String getRamChoice() {
		return ramChoice.getSelectionModel().getSelectedItem().replace(" ", "").replace("o", "");
	}
	
	private void hideAll() {
		nameField.setDisable(true);
		passField.setDisable(true);
		playButtonPane.pane.setVisible(false);
		optionButton.pane.setVisible(false);
		
	}
	
	private void showAll() {
		Platform.runLater(()->nameField.setDisable(false));
		Platform.runLater(()->passField.setDisable(false));
		Platform.runLater(()->progressBar.hide());
		Platform.runLater(()->playButtonPane.pane.setVisible(true));
		Platform.runLater(()->optionButton.pane.setVisible(true));
	}
	
	public static void main(String[] args) {
        
		
		
		launch(args);
        
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	instance = this;
    	
    	
    	
    	
    	// Properties
    	
		try {
			prop = new Properties();
			prop.load(getClass().getResourceAsStream("/kube/launcher/resources/config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		
    	
    	
    	
    	//Launcher Initialisation
    	
    	Launcher.setLauncherView(this);
    	
    	// Load custom font
    	
    	Font customFont = Font.loadFont(getClass().getResourceAsStream("/kube/launcher/resources/Digital Dare.ttf"), 16);

    	
    	// Windows
    	
    	primaryStage.setTitle("Launcher");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(new Image("/kube/launcher/resources/Logo v1.0_partial.png"));
        
        // Back Pane
       
        backPane = new CustomAnchorPane();
        backPane.setBackgroundImage("/kube/launcher/resources/Back.png", BackgroundRepeat.REPEAT, 32);
        
        // Video Pane
        
        videoPane = new CustomAnchorPane();
        videoPane.setAllAnchors(8);
        backPane.getChildren().add(videoPane.pane);
        
        
        // Video Player
        
       
        Media videoMedia = new Media(getClass().getResource("/kube/launcher/resources/cinematic.mp4").toString());
        MediaPlayer videoPlayer = new MediaPlayer(videoMedia);
        videoPlayer.setAutoPlay(true);
        videoPlayer.setVolume(0); 
        videoPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        videoViewer = new MediaView(videoPlayer);
        videoViewer.setPreserveRatio(false);
        
        videoViewer.fitWidthProperty().bind(videoPane.pane.widthProperty());
        videoViewer.fitHeightProperty().bind(videoPane.pane.heightProperty());
        
        videoPane.getChildren().add(videoViewer);
       
        // Fade Pane
        
        fadePane = new CustomAnchorPane();
        fadePane.setBackgroundColor(.7, 0, 0, 0);
        fadePane.setAllAnchors(0);
        fadePane.setParent(videoPane.pane);
        
        // Music player
        
        Media audioMedia = new Media(getClass().getResource("/kube/launcher/resources/sound.mp3").toString());
        audioPlayer = new MediaPlayer(audioMedia);
        audioPlayer.setAutoPlay(true);
        audioPlayer.setVolume(0); 
        audioPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        
            
		// Logo
		
		logoView = new ImageView();
		logoView.setImage(new Image(getClass().getResource("/kube/launcher/resources/KubeLogo.png").toString(), 256, 80, false, false));
		logoView.relocate(128, 20);
		fadePane.getChildren().add(logoView);
		
		// Input Name
		
		nameField = new TextField();
		nameField.relocate(225, 110);
		fadePane.getChildren().add(nameField);
		
		// Name Label
		
		nameLabel = new javafx.scene.control.Label("Username : ");
		nameLabel.setFont(customFont);
		nameLabel.setStyle("-fx-text-fill: #FFFFFF");
		nameLabel.relocate(100, 110);
		fadePane.getChildren().addAll(nameLabel);
		
		// Input Pass
		
		passField = new PasswordField();
		passField.relocate(225, 145);
		fadePane.getChildren().add(passField);
		
		// Pass Label
		
		passLabel = new javafx.scene.control.Label("Password : ");
		passLabel.setFont(customFont);
		passLabel.setStyle("-fx-text-fill: #FFFFFF");
		passLabel.relocate(100, 145);
		fadePane.getChildren().addAll(passLabel);
		
		// Quit Label
		
		quitLabel = new javafx.scene.control.Label("X");
		quitLabel.setFont(customFont);
		quitLabel.setStyle("-fx-text-fill: #A00000");
		quitLabel.relocate(480, 0);
		fadePane.getChildren().addAll(quitLabel);
		
		quitLabel.setOnMouseEntered(e->quitLabel.setStyle(" -fx-text-fill: #FF0000; -fx-cursor:hand;"));
		quitLabel.setOnMouseExited(e->quitLabel.setStyle(" -fx-text-fill: #A00000; -fx-cursor:normal;"));
		quitLabel.setOnMouseClicked(e->{
			Timeline timeline = new Timeline();
            KeyFrame key = new KeyFrame(Duration.millis(500),
                           new KeyValue (primaryStage.getScene().getRoot().opacityProperty(), 0));
            KeyFrame key2 = new KeyFrame(Duration.millis(500),
                    		new KeyValue (getSoundSlider().valueProperty(), 0));
            timeline.getKeyFrames().add(key);
            timeline.getKeyFrames().add(key2);  
            timeline.setOnFinished((ae) -> System.exit(1)); 
            timeline.play();	
		});
		
		// Reduce Label
		
		minimizeLabel = new javafx.scene.control.Label("-");
		minimizeLabel.setFont(customFont);
		minimizeLabel.setStyle("-fx-text-fill: #AAAAAA");
		minimizeLabel.relocate(460, 0);
		fadePane.getChildren().addAll(minimizeLabel);
		
		minimizeLabel.setOnMouseEntered(e->minimizeLabel.setStyle(" -fx-text-fill: #DDDDDD; -fx-cursor:hand;"));
		minimizeLabel.setOnMouseExited(e->minimizeLabel.setStyle(" -fx-text-fill: #AAAAAA; -fx-cursor:normal;"));
		minimizeLabel.setOnMouseClicked(e->primaryStage.setIconified(true));
		
		// Option Button
		
		optionButton = new CustomAnchorPane();
		optionButton.setBackgroundImage("/kube/launcher/resources/SettingsButton.png", BackgroundRepeat.ROUND, 20);
		optionButton.setSize(20, 20);
		optionButton.setPosition(355, 185);
		fadePane.getChildren().add(optionButton.pane);
		
		optionButton.pane.setOnMouseEntered(e->
		{
			optionButton.setBackgroundImage("/kube/launcher/resources/SettingsButton_selected.png", BackgroundRepeat.ROUND, 20);
			optionButton.pane.setStyle(optionButton.pane.getStyle() + "; -fx-cursor:hand;");
		});
		
		optionButton.pane.setOnMouseExited(e->
		{
			optionButton.setBackgroundImage("/kube/launcher/resources/SettingsButton.png", BackgroundRepeat.ROUND, 20);
			optionButton.pane.setStyle(optionButton.pane.getStyle() + "; -fx-cursor:normal;");
		});
		
		optionButton.pane.setOnMouseClicked(e->
		{
			optionPane.show();
			quitLabel.toFront();
			minimizeLabel.toFront();
			
			Timeline timeline = new Timeline();
			KeyFrame key = new KeyFrame(Duration.millis(500),
                    new KeyValue (optionPane.pane.opacityProperty(), 1));
			timeline.getKeyFrames().add(key);
	        timeline.play();
			
		});
		
		
		// Progressbar
		
		progressBar = new CustomProgressBar(WIDTH - 42, 16, 2, "/kube/launcher/resources/Lava.gif", customFont, "/kube/launcher/resources/Back.png");
		progressBar.setPosition(10, HEIGHT - 58);
		progressBar.hide();
		fadePane.getChildren().add(progressBar.getFXProgressBar());
		
		// Info Label
		
		infoLabel = new javafx.scene.control.Label("");
		infoLabel.setFont(Font.getDefault());
		infoLabel.setStyle("-fx-text-fill: #FFFFFF");
		infoLabel.relocate(10, HEIGHT - 35);
		fadePane.getChildren().addAll(infoLabel);
		
		// Sound Informations
		// Sappheiros
		
		soundLabel = new javafx.scene.control.Label("Sound by Sappheiros");
		soundLabel.setFont(Font.getDefault());
		soundLabel.setStyle("-fx-text-fill: #FFFFFF");
		soundLabel.relocate(WIDTH - 140, HEIGHT - 35);
		fadePane.getChildren().addAll(soundLabel);
		
		// Play Button
		
		playButtonPane = new CustomAnchorPane();
		playButtonPane.setPosition(250, 180);
		playButtonPane.setBackgroundImage("/kube/launcher/resources/Back.png", BackgroundRepeat.REPEAT, 32);
		playButtonPane.setSize(96, 32);
		fadePane.getChildren().add(playButtonPane.pane);
		
		playButton = new CustomAnchorPane();
		playButton.setBackgroundColor(.9, 0, 0, 0);
		playButton.setAllAnchors(2);
		playButtonPane.getChildren().add(playButton.pane);
		
		playLabel = new javafx.scene.control.Label("Jouer");
		playLabel.setFont(customFont);
		playLabel.setStyle("-fx-text-fill: #FFFFFF");
		playLabel.relocate(playButton.pane.getWidth() / 2 + 15, playButton.pane.getHeight() / 2 + 5);
		playButton.getChildren().addAll(playLabel);
		
		playButton.pane.setOnMouseEntered(e->
		{
			playButton.pane.setStyle("-fx-background-color:rgba(0, 0, 0, 0.3);"
					+ "-fx-cursor:hand;");
		});
		playButton.pane.setOnMouseExited(e->
		{
			playButton.pane.setStyle("-fx-background-color:rgba(0, 0, 0, 0.9);"
					+ "-fx-cursor:normal;");
		});
		
		playButton.pane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) 
			{
				hideAll();
				if (nameField.getText().replaceAll(" ", "").length() == 0 || passField.getText().length() == 0)
				{
					new CustomAlert(AlertType.ERROR, "Erreur", "Erreur dans la saisie", "Des champs sont vides !");
					showAll();
					return;
				}
				
				Thread t = new Thread()
				{
					@Override
					public void run()
					{
						try
						{
							Launcher.auth(nameField.getText(), passField.getText());
						}
						catch (AuthenticationException e)
						{
							Platform.runLater(()->new CustomAlert(AlertType.ERROR, "Erreur", "Impossible de se connecter", e.getErrorModel().getErrorMessage()));
							Platform.runLater(()->infoLabel.setText(""));
							showAll();
							
							return;
						}

						//saver.set("username", usernameField.getText());

						try
						{
							Launcher.update();
						}
						catch (Exception e)
						{
							Launcher.interruptThread();
							Platform.runLater(()->new CustomAlert(AlertType.ERROR, "Erreur", "Mise à jour impossible", e.getStackTrace().toString()));
							Platform.runLater(()->infoLabel.setText(""));
							showAll();
							return;
						}

						try
						{
							Launcher.launch();
						}
						catch (LaunchException e)
						{
							Launcher.interruptThread();
							Platform.runLater(()->new CustomAlert(AlertType.ERROR, "Erreur", "Impossible de lancer le jeu", e.getStackTrace().toString()));
							Platform.runLater(()->infoLabel.setText(""));
							showAll();
							return;
						}
					}
				};
				t.start();
				
			}
			
			
		}); 
		
		// Option Pane
		
		optionPane = new CustomAnchorPane();
		optionPane.setAllAnchors(0);
		optionPane.setBackgroundColor(1, 0, 0, 0);
		optionPane.hide();
		optionPane.pane.setOpacity(0);
		fadePane.getChildren().add(optionPane.pane);
		
		// Option Title Label
		
		optionTitleLabel = new javafx.scene.control.Label("Options");
		optionTitleLabel.setFont(customFont);
		optionTitleLabel.setStyle("-fx-text-fill: #FFFFFF");
		optionTitleLabel.relocate(220, 10);
		optionPane.getChildren().addAll(optionTitleLabel);
		
		// Sound Label
		
		optionSoundLabel = new javafx.scene.control.Label("Volume : ");
		optionSoundLabel.setFont(customFont);
		optionSoundLabel.setStyle("-fx-text-fill: #FFFFFF;");
		optionSoundLabel.relocate(130, 80);
		optionPane.getChildren().addAll(optionSoundLabel);
		
		// Sound Label Percentage
		
		optionSoundLabelPercentage = new javafx.scene.control.Label(String.valueOf((int)(audioPlayer.getVolume() * 100)) + "%");
		optionSoundLabelPercentage.setFont(customFont);
		optionSoundLabelPercentage.setStyle("-fx-text-fill: #FFFFFF;");
		optionSoundLabelPercentage.relocate(380, 80);
		optionPane.getChildren().addAll(optionSoundLabelPercentage);
		
		// Sound Slider
		
		soundSlider = new Slider();
		soundSlider.relocate(220, 82);
		soundSlider.setMax(100);
		soundSlider.setMin(0);
		
		optionPane.getChildren().add(soundSlider);
		
		audioPlayer.volumeProperty().bind(Bindings.divide(soundSlider.valueProperty(), 100));
		optionSoundLabelPercentage.textProperty().bind(Bindings.format("%.0f%%", soundSlider.valueProperty()));
		
		soundSlider.setOnMouseReleased(e->
		{
			prop.setProperty("soundLevel", String.valueOf((int)getSoundSlider().getValue()));
			try 
			{
				prop.store(new FileOutputStream(getClass().getResource("/kube/launcher/resources/config.properties").getFile()), null);
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
		});
		
		
		// Choice Ram
		
		ramChoice = new ChoiceBox<String> (FXCollections.observableArrayList("2 Go", "3 Go", "4 Go", "5 Go", "6 Go", "7 Go", "8 Go"));
		ramChoice.relocate(225, 115);
		ramChoice.getSelectionModel().select(Integer.parseInt(prop.getProperty("ramSelection")));
		
		ramChoice.setOnAction(e->
		{
			prop.setProperty("ramSelection", String.valueOf(ramChoice.getSelectionModel().getSelectedIndex()));
			try 
			{
				prop.store(new FileOutputStream(getClass().getResource("/kube/launcher/resources/config.properties").getFile()), null);
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
		});
		
		optionPane.getChildren().add(ramChoice);
		
		// Choice Ram Label
		
		ramChoiceLabel = new javafx.scene.control.Label("Memoire : ");
		ramChoiceLabel.setFont(customFont);
		ramChoiceLabel.setStyle("-fx-text-fill: #FFFFFF;");
		ramChoiceLabel.relocate(122, 118);
		optionPane.getChildren().addAll(ramChoiceLabel);
		
		// Back Label
		
		backLabel = new javafx.scene.control.Label("<<");
		backLabel.setFont(customFont);
		backLabel.setStyle("-fx-text-fill: #AAAAAA;");
		backLabel.relocate(10, HEIGHT - 40);
		
		
		optionPane.getChildren().addAll(backLabel);
		
		backLabel.setOnMouseEntered(e->{
			backLabel.setStyle("-fx-text-fill: #FFFFFF;"
					+ "-fx-cursor:hand;");
		});
		
		backLabel.setOnMouseExited(e->{
			backLabel.setStyle("-fx-text-fill: #AAAAAA;"
					+ "-fx-cursor:normal;");
		});
		
		
		backLabel.setOnMouseClicked(e->{
			Timeline timeline = new Timeline();
			KeyFrame key = new KeyFrame(Duration.millis(500),
                    new KeyValue (optionPane.pane.opacityProperty(), 0));
			timeline.getKeyFrames().add(key);
			timeline.setOnFinished(ea->optionPane.hide());
	        timeline.play();
		});
		
		
        primaryStage.setScene(new Scene(backPane.pane, WIDTH, HEIGHT, javafx.scene.paint.Color.TRANSPARENT));
        this.primaryStage = primaryStage;
       
        // FadeIn
        
        primaryStage.getScene().getRoot().setOpacity(0);
        primaryStage.show();
        Timeline timeline = new Timeline();
        KeyFrame key = new KeyFrame(Duration.millis(2000),
                       new KeyValue (primaryStage.getScene().getRoot().opacityProperty(), 1));
        KeyFrame key2 = new KeyFrame(Duration.millis(2000),
        				new KeyValue (soundSlider.valueProperty(), Integer.parseInt(prop.getProperty("soundLevel"))));
        
        timeline.getKeyFrames().add(key);
        timeline.getKeyFrames().add(key2);
        timeline.play();
        
        // Timer inactivity
        timer = new Timer();
        task = null;
        primaryStage.getScene().setOnMouseMoved(e->{
        	Timeline t = new Timeline();
        	KeyFrame k = new KeyFrame(Duration.millis(500),
                    new KeyValue (fadePane.pane.opacityProperty(), 1));
        	t.getKeyFrames().add(k);
        	t.play();
        	primaryStage.getScene().setCursor(Cursor.DEFAULT);
        	
        	if (task != null)
        		task.cancel();
        	task = new TimerTask() {
                public void run() {
                	Timeline t = new Timeline();
                	KeyFrame key = new KeyFrame(Duration.millis(500),
                            new KeyValue (fadePane.pane.opacityProperty(), 0));
                	t.getKeyFrames().add(key);
                	
                	t.play();
                	primaryStage.getScene().setCursor(Cursor.NONE);
                	
                	task.cancel();
                }
        	};
        	
            timer.schedule(task, 10000);
        });
        
    }


    
}
