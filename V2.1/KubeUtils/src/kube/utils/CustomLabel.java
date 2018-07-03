package kube.utils;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CustomLabel {
	
	private Label label;
	
	public Label getFXLabel()
	{
		return label;
	}
	
	public void setFillColor(Color color)
	{
		label.setTextFill(color);
	}
	
	public void setOpacity(double value) 
	{
		label.setOpacity(value);
	}
	
	
	public void setCursor(Cursor cursor) 
	{
		label.setCursor(cursor);
	}
	
	
	public CustomLabel(String text, Font font, Color color, double x, double y) 
	{
		label = new Label(text);
		label.setFont(font);
		label.relocate(x, y);
		label.setTextFill(color);
	}
	
	public void toFront() 
	{
		label.toFront();
	}
	public void setText(String s)
	{
		label.setText(s);
	}
	
}
