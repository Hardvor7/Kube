package kube.launcher;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javafx.util.Duration;
import kube.utils.CustomAlert;
import kube.utils.CustomAnchorPane;
import kube.utils.CustomAnchorPane.AnchorType;
import kube.utils.CustomLabel;
import kube.utils.CustomProgressBar;

import java.util.Properties;

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
import javafx.scene.control.CheckBox;
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
	
	public CustomProgressBar progressBar;
	
	public MediaPlayer audioPlayer;
	
	public CustomLabel infoLabel;
	
	public Stage primaryStage;
	
	private CustomAnchorPane backPane;
	private CustomAnchorPane videoPane;
	private MediaView videoViewer;
	private CustomAnchorPane fadePane;
	private ImageView logoView;
	private TextField nameField;
	private TextField passField;
	private CustomAnchorPane optionButton;
	
	private CustomLabel passLabel;
	private CustomLabel nameLabel;
	private CustomLabel quitLabel;
	private CustomLabel minimizeLabel;
	private CustomLabel soundLabel;
	private CustomAnchorPane playButtonPane;
	private CustomAnchorPane playButton;
	private CustomLabel playLabel;
	private CustomAnchorPane optionPane;
	private CustomLabel optionTitleLabel;
	private CustomLabel optionSoundLabel;
	private CustomLabel optionSoundLabelPercentage;
	private Slider soundSlider;
	private ChoiceBox<String> ramChoice ;
	private CustomLabel ramChoiceLabel;
	private CustomLabel backLabel;
	private CustomLabel loadLabel;
	private CustomAnchorPane loadfadePane;
	private CustomAnchorPane loadPane;
	
	private CheckBox localBox;
	
	public Slider getSoundSlider() 
	{
		return soundSlider;
	}
	
	public String getRamChoice() 
	{
		return ramChoice.getSelectionModel().getSelectedItem().replace(" ", "").replace("o", "");
	}
	
	private void hidefadePane() 
	{
		nameField.setDisable(true);
		passField.setDisable(true);
		
		Timeline t = new Timeline();
		
		KeyFrame frames[] = new KeyFrame[] {
			new KeyFrame(Duration.millis(125), new KeyValue (fadePane.getFXPane().opacityProperty(), 0)),
			new KeyFrame(Duration.millis(125), new KeyValue (loadPane.getFXPane().opacityProperty(), 1))
		};
		t.setOnFinished(ea->fadePane.hide());
		
		t.getKeyFrames().addAll(frames);
		t.play();
		
	}
	
	private void showfadePane() 
	{
		Platform.runLater(()->{
			nameField.setDisable(false);
			passField.setDisable(false);
			
			Timeline t = new Timeline();
			fadePane.show();
			KeyFrame frames[] = new KeyFrame[] {
					new KeyFrame(Duration.millis(125), new KeyValue (fadePane.getFXPane().opacityProperty(), 1)),
					new KeyFrame(Duration.millis(125), new KeyValue (loadPane.getFXPane().opacityProperty(), 0))
				};
			t.setOnFinished(ea->fadePane.getFXPane().toFront());
			t.getKeyFrames().addAll(frames);
        	t.play();

			
		});

	}
	
	public static void main(String[] args) { launch(args); }
    
    @Override
    public void start(Stage primaryStage) 
    {
    	
    	// Properties
    	
		try 
		{
			prop = new Properties();
			prop.load(new FileInputStream(new File("launcher.properties")));
		} 
		catch (Exception e) 
		{
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
        backPane.getChildren().add(videoPane.getFXPane());       
        
        // Video Player
        
        Media videoMedia = new Media(getClass().getResource("/kube/launcher/resources/cinematic.mp4").toString());
        MediaPlayer videoPlayer = new MediaPlayer(videoMedia);
        videoPlayer.setAutoPlay(true);
        videoPlayer.setVolume(0); 
        videoPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        videoViewer = new MediaView(videoPlayer);
        videoViewer.setPreserveRatio(false);
        
        videoViewer.fitWidthProperty().bind(videoPane.getFXPane().widthProperty());
        videoViewer.fitHeightProperty().bind(videoPane.getFXPane().heightProperty());
        
        videoPane.getChildren().add(videoViewer);
        
        // Fade Pane
        
        fadePane = new CustomAnchorPane();
        fadePane.setBackgroundColor(.7, 0, 0, 0);
        fadePane.setAllAnchors(0);
        fadePane.setParent(videoPane.getFXPane());

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
		
		nameLabel = new CustomLabel("Username : ", customFont, Color.WHITE, 100, 110);
		fadePane.getChildren().add(nameLabel.getFXLabel());

		// Input Pass
		
		passField = new PasswordField();
		passField.relocate(225, 145);
		fadePane.getChildren().add(passField);

		// Pass Label
		
		passLabel = new CustomLabel("Password : ", customFont, Color.WHITE, 100, 145);
		fadePane.getChildren().add(passLabel.getFXLabel());

		// Quit Label
		
		quitLabel = new CustomLabel("X", customFont, Color.web("#A00000"), 480, 0);
		fadePane.getChildren().add(quitLabel.getFXLabel());
		
		quitLabel.getFXLabel().setOnMouseEntered(e->{
			quitLabel.setCursor(Cursor.HAND);
			quitLabel.setFillColor(Color.web("#FF0000"));
		});
		
		quitLabel.getFXLabel().setOnMouseExited(e->{
			quitLabel.setCursor(Cursor.DEFAULT);
			quitLabel.setFillColor(Color.web("#A00000"));
		});
		
		quitLabel.getFXLabel().setOnMouseClicked(e->
		{
			
			KeyFrame frames[] = {
					new KeyFrame(Duration.millis(500), new KeyValue (primaryStage.getScene().getRoot().opacityProperty(), 0)),
					new KeyFrame(Duration.millis(500), new KeyValue (getSoundSlider().valueProperty(), 0)),
					new KeyFrame(Duration.millis(50), new KeyValue (optionPane.getFXPane().opacityProperty(), 0))
			};

			Timeline timeline = new Timeline();
			timeline.getKeyFrames().setAll(frames);
			timeline.play();
			timeline.setOnFinished((ae) -> System.exit(1)); 
			
			
		});

		// Reduce Label
		
		minimizeLabel = new CustomLabel("-", customFont, Color.web("#AAAAAA"), 460, 0); 
		fadePane.getChildren().add(minimizeLabel.getFXLabel());
		
		minimizeLabel.getFXLabel().setOnMouseEntered(e->
		{
			minimizeLabel.setCursor(Cursor.HAND);
			minimizeLabel.setFillColor(Color.web("#DDDDDD"));
		});
		
		minimizeLabel.getFXLabel().setOnMouseExited(e->
		{
			minimizeLabel.setCursor(Cursor.DEFAULT);
			minimizeLabel.setFillColor(Color.web("#AAAAAA"));
		});
		
		minimizeLabel.getFXLabel().setOnMouseClicked(e->primaryStage.setIconified(true));

		// Option Button
		
		optionButton = new CustomAnchorPane();
		optionButton.setBackgroundImage("/kube/launcher/resources/SettingsButton.png", BackgroundRepeat.ROUND, 20);
		optionButton.setSize(20, 20);
		optionButton.setPosition(355, 185);
		fadePane.getChildren().add(optionButton.getFXPane());
		
		optionButton.getFXPane().setOnMouseEntered(e->
		{
			optionButton.setBackgroundImage("/kube/launcher/resources/SettingsButton_selected.png", BackgroundRepeat.ROUND, 20);
			optionButton.getFXPane().setStyle(optionButton.getFXPane().getStyle() + "; -fx-cursor:hand;");
		});
		
		optionButton.getFXPane().setOnMouseExited(e->
		{
			optionButton.setBackgroundImage("/kube/launcher/resources/SettingsButton.png", BackgroundRepeat.ROUND, 20);
			optionButton.getFXPane().setStyle(optionButton.getFXPane().getStyle() + "; -fx-cursor:normal;");
		});
		
		optionButton.getFXPane().setOnMouseClicked(e->
		{
			optionPane.show();
			quitLabel.toFront();
			minimizeLabel.toFront();
			
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(250), new KeyValue (optionPane.getFXPane().opacityProperty(), 1)));
	        timeline.play();
			
		});
		
		// Sound Informations
		// Sappheiros
		
		soundLabel = new CustomLabel("Sound by Sappheiros", Font.getDefault(), Color.WHITE, WIDTH - 140, HEIGHT - 35);
		fadePane.getChildren().add(soundLabel.getFXLabel());
		
		// Play Button
		
		playButtonPane = new CustomAnchorPane();
		playButtonPane.setPosition(250, 180);
		playButtonPane.setBackgroundImage("/kube/launcher/resources/Back.png", BackgroundRepeat.REPEAT, 32);
		playButtonPane.setSize(96, 32);
		fadePane.getChildren().add(playButtonPane.getFXPane());
		
		playButton = new CustomAnchorPane();
		playButton.setBackgroundColor(.9, 0, 0, 0);
		playButton.setAllAnchors(2);
		playButtonPane.getChildren().add(playButton.getFXPane());
		
		playLabel = new CustomLabel("Jouer", customFont, Color.WHITE, playButton.getFXPane().getWidth() / 2 + 15, playButton.getFXPane().getHeight() / 2 + 5);
		playButton.getChildren().add(playLabel.getFXLabel());
		
		playButton.getFXPane().setOnMouseEntered(e->
		{
			playButton.getFXPane().setStyle("-fx-background-color:rgba(0, 0, 0, 0.3);"
					+ "-fx-cursor:hand;");
		});
		playButton.getFXPane().setOnMouseExited(e->
		{
			playButton.getFXPane().setStyle("-fx-background-color:rgba(0, 0, 0, 0.9);"
					+ "-fx-cursor:normal;");
		});
		
		playButton.getFXPane().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event) 
			{
				if (nameField.getText().replaceAll(" ", "").length() == 0 || passField.getText().length() == 0)
				{
					new CustomAlert(AlertType.ERROR, "Erreur", "Erreur dans la saisie", "Des champs sont vides !");
					showfadePane();
					return;
				}
				
				
				hidefadePane();
				
				
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
							showfadePane();
							
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
							showfadePane();
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
							showfadePane();
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
		optionPane.getFXPane().setOpacity(0);
		fadePane.getChildren().add(optionPane.getFXPane());
		
		// Option Title Label
		
		optionTitleLabel = new CustomLabel("Options", customFont, Color.WHITE, 220, 10);
		optionPane.getChildren().add(optionTitleLabel.getFXLabel());
		
		// Sound Label
		
		optionSoundLabel = new CustomLabel("Volume : ", customFont, Color.WHITE, 130, 80);
		optionPane.getChildren().add(optionSoundLabel.getFXLabel());
		
		// Sound Label Percentage
		
		optionSoundLabelPercentage = new CustomLabel(String.valueOf((int)(audioPlayer.getVolume() * 100)) + "%", customFont, Color.WHITE, 380, 80);
		optionPane.getChildren().add(optionSoundLabelPercentage.getFXLabel());
		
		// Sound Slider
		
		soundSlider = new Slider();
		soundSlider.relocate(220, 82);
		soundSlider.setMax(100);
		soundSlider.setMin(0);
		
		optionPane.getChildren().add(soundSlider);
		
		audioPlayer.volumeProperty().bind(Bindings.divide(soundSlider.valueProperty(), 100));
		optionSoundLabelPercentage.getFXLabel().textProperty().bind(Bindings.format("%.0f%%", soundSlider.valueProperty()));
		
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
		
		ramChoiceLabel = new CustomLabel("Memoire : ", customFont, Color.WHITE, 112, 118);
		optionPane.getChildren().add(ramChoiceLabel.getFXLabel());
		
		// Local CheckBox
		if (Boolean.parseBoolean(prop.getProperty("localCheckbox")))
		{
			localBox = new CheckBox("Local");
			localBox.relocate(112, 150);
			localBox.setFont(Font.getDefault());
			localBox.setTextFill(Color.WHITE);
			optionPane.getChildren().add(localBox);
			localBox.setOnAction(e->
			{
				if(localBox.isSelected()) 
				{
					Launcher.setServerAddress("192.168.1.25");
				}
				else 
				{
					Launcher.setServerAddress("robin-leclair.ovh");
				}
				
				
			});
			
		}

		// Back Label
		
		backLabel = new CustomLabel("<<", customFont, Color.web("#AAAAAA"), 10, HEIGHT - 40);
		optionPane.getChildren().add(backLabel.getFXLabel());
		
		backLabel.getFXLabel().setOnMouseEntered(e->
		{
			backLabel.setFillColor(Color.WHITE);
			backLabel.setCursor(Cursor.HAND);
		});
		
		backLabel.getFXLabel().setOnMouseExited(e->
		{
			backLabel.setFillColor(Color.web("#AAAAAA"));
			backLabel.setCursor(Cursor.DEFAULT);
		});
		
		
		backLabel.getFXLabel().setOnMouseClicked(e->
		{
			Timeline timeline = new Timeline();
			timeline.getKeyFrames().add(new KeyFrame(Duration.millis(250), new KeyValue (optionPane.getFXPane().opacityProperty(), 0)));
			timeline.setOnFinished(ea->optionPane.hide());
	        timeline.play();
		});
		
		// Load Pane
		loadPane = new CustomAnchorPane();
		loadPane.setAllAnchors(0);
		loadPane.setOpacity(0);
		loadPane.setBackgroundColor(Color.TRANSPARENT);
		videoPane.getChildren().add(loadPane.getFXPane());
		
		// Loadfade Pane
		
		loadfadePane = new CustomAnchorPane();
		loadfadePane.setAnchor(AnchorType.Top, 0);
		loadfadePane.setAnchor(AnchorType.Left, 0);
		loadfadePane.setAnchor(AnchorType.Right, 0);
		loadfadePane.setSize(0, 16);
		loadfadePane.setBackgroundColor(0.8, 0, 0, 0);
		loadPane.getChildren().add(loadfadePane.getFXPane());
		
		
		// Load Label
		
		loadLabel = new CustomLabel("Chargement...", customFont, Color.WHITE, WIDTH / 2 - 75, 0);
		loadfadePane.getChildren().add(loadLabel.getFXLabel());
		
		// Info Label
		
		infoLabel = new CustomLabel("", Font.getDefault(), Color.WHITE, 10, HEIGHT - 35);
		loadPane.getChildren().add(infoLabel.getFXLabel());
		
		// Progressbar
		
		progressBar = new CustomProgressBar(WIDTH - 42, 16, 2, "/kube/launcher/resources/Lava.gif", customFont, "/kube/launcher/resources/Back.png");
		progressBar.setPosition(10, HEIGHT - 58);
		progressBar.hide();
		loadPane.getFXPane().getChildren().add(progressBar.getFXProgressBar());
		
		fadePane.getFXPane().toFront();
		
        primaryStage.setScene(new Scene(backPane.getFXPane(), WIDTH, HEIGHT, javafx.scene.paint.Color.TRANSPARENT));
        this.primaryStage = primaryStage;
       
        // FadeIn
        
        primaryStage.getScene().getRoot().setOpacity(0);
        primaryStage.show();
        Timeline timeline = new Timeline();
        
        KeyFrame frames[] = new KeyFrame[] {
        		new KeyFrame(Duration.millis(500), new KeyValue (primaryStage.getScene().getRoot().opacityProperty(), 1)),
        		new KeyFrame(Duration.millis(500), new KeyValue (soundSlider.valueProperty(), Integer.parseInt(prop.getProperty("soundLevel"))))
        };
        timeline.getKeyFrames().addAll(frames);
        timeline.play();
        
        
    }
    
}
