package minecraftLauncher.launcher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import fr.theshark34.openlauncherlib.launcher.util.UsernameSaver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

@SuppressWarnings("serial")
public class LauncherPanel extends JPanel implements SwingerEventListener
{
	private Image background = Swinger.getResource("Background v1.1.png");
	
	private UsernameSaver saver = new UsernameSaver(Launcher.ML_INFOS);
	
	private JTextField usernameField = new JTextField(saver.getUsername(""));
	private JTextField passwordField = new JPasswordField();
	
	private STexturedButton playButton = new STexturedButton(Swinger.getResource("play.png"));
	private STexturedButton quitButton = new STexturedButton(Swinger.getResource("quit.png"));
	private STexturedButton hideButton = new STexturedButton(Swinger.getResource("hide.png"));
	
	public LauncherPanel() {
		this.setLayout(null);
		
		// Username Field
		usernameField.setBounds(100, 100, 200, 30);
		usernameField.setBackground(Color.WHITE);
		usernameField.setFont(usernameField.getFont().deriveFont(20F));
		this.add(usernameField);

		
		// Password Field
		passwordField.setBounds(100, 150, 200, 30);
		passwordField.setBackground(Color.WHITE);
		passwordField.setFont(passwordField.getFont().deriveFont(20F));
		this.add(passwordField);
		
		playButton.setBounds(130, 200);
		playButton.addEventListener(this);
		this.add(playButton);
		
		quitButton.setBounds(530, 100);
		quitButton.addEventListener(this);
		this.add(quitButton);
		
		hideButton.setBounds(530, 200);
		hideButton.addEventListener(this);
		this.add(hideButton);
	}



	@Override
	public void onEvent(SwingerEvent e) 
	{
		if (e.getSource() == playButton)
		{
			
		}
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
