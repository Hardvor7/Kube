package kube.bootstrap;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;
import kube.bootstrap.CustomAnchorPane.AnchorType;
import javafx.scene.control.Label;
public class CustomProgressBar {
	
	public float value;
	private CustomAnchorPane box;
	private AnchorPane progressBar;
	private Label percentageLabel;
	
	
	public CustomProgressBar(double width, double height, float margin, String stepURL, Font customFont, String backURL) 
	{
		this.box = new CustomAnchorPane();
		this.progressBar = new AnchorPane();
		
		box.setAnchor(AnchorType.Top, margin);
		box.setAnchor(AnchorType.Left, margin);
		box.setAnchor(AnchorType.Bottom, margin);
		box.setBackgroundColor(0, 0, 0, .9);
		box.setBackgroundImage(stepURL, BackgroundRepeat.REPEAT, (int)(height - 2 * margin));
		box.pane.setPrefWidth(width - 2 * margin);
		
		
		progressBar.setPrefSize(width, height);
		progressBar.setStyle("-fx-background-image:url('" + backURL + "');");
		progressBar.getChildren().add(box.pane);
		
		
		
		// Progressbar Label
		
		percentageLabel = new Label("100%");
		percentageLabel.setFont(customFont);
		percentageLabel.setStyle("-fx-text-fill: #FFFFFF;");
		percentageLabel.relocate(width/2 - 20, height - 17);
		progressBar.getChildren().add(percentageLabel);
		
		
		
	}
	
	
	public AnchorPane getFXProgressBar()
	{
		return progressBar;
	}
	
	public void setValue(float value)
	{
		this.value = value;
		box.setAnchor(AnchorType.Right, box.pane.getPrefWidth() + 4 - (value* box.pane.getPrefWidth()));
		//percentageLabel.setText((int)(value * 100) + "%");
		
	}
	
	public void setPosition(double x, double y) 
	{
		progressBar.relocate(x, y);
	}
	public void setSize(double width, double height) 
	{
		progressBar.setPrefSize(width, height);
	}
	
	public void show() 
	{
		progressBar.setVisible(true);
	}
	public void hide() 
	{
		progressBar.setVisible(false);
	}
	
	
	
	
}
