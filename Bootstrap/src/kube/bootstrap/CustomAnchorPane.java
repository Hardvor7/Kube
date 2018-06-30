package kube.bootstrap;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BackgroundRepeat;
public class CustomAnchorPane{

	public AnchorPane pane;
	
	
	// ----- CONSTRUCTOR ----- //
	
	public CustomAnchorPane()
	{
		pane = new AnchorPane();
	}
	
	public CustomAnchorPane(String imageURL)
	{
		this();
		pane.setStyle("-fx-background-image:url(" + imageURL + ");");
	}
	
	public void setBackgroundImage(String imageURL, BackgroundRepeat t, int tileSize)
	{
		pane.setStyle("-fx-background-image:url(" + imageURL + ");" 
				+ "-fx-background-repeat: " + t.toString().toLowerCase() + ";" 
				+ "-fx-background-size:" + tileSize + "," + tileSize + ";");

		//System.out.print(t);
	}
	
	public void setBackgroundColor(double a, double r, double g, double b)
	{
		pane.setStyle("-fx-background-color:rgba(" + r + "," + g + "," + b + "," + a + ");");
	}
	
	public void hide() {
		pane.setVisible(false);
	}
	
	public void show() {
		pane.setVisible(true);
	}
	
	public void setSize(double width, double height) {
		pane.setPrefSize(width, height);
	}
	
	public void setParent(Pane p) {
		p.getChildren().add(pane);
		
	}
	
	public void setPosition(double x, double y)
	{
		pane.relocate(x, y);
	}
	
	public void setAllAnchors(double k)
	{
		AnchorPane.setBottomAnchor(pane, k);
		AnchorPane.setTopAnchor(pane, k);
		AnchorPane.setLeftAnchor(pane, k);
		AnchorPane.setRightAnchor(pane, k);
	}
	
	public ObservableList<Node> getChildren() {
		return pane.getChildren();
	}
	
	public void setAnchor(AnchorType t, double k) 
	{
		switch(t) 
		{
		
			case Top:
				AnchorPane.setTopAnchor(pane, k);
			break;
			
			case Bottom:
				AnchorPane.setBottomAnchor(pane, k);
			break;
			
			case Right:
				AnchorPane.setRightAnchor(pane, k);
			break;
			
			case Left:
				AnchorPane.setLeftAnchor(pane, k);
			break;
		
		}
	}
	
	public enum AnchorType
	{
		Top,
		Bottom,
		Right,
		Left
	}

	
	
}
