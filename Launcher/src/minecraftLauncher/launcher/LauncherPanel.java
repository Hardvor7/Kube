package minecraftLauncher.launcher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
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
	private STexturedButton quitButton = new STexturedButton(Swinger.getResource("QuitButton_no_Selected.png"));
	private STexturedButton hideButton = new STexturedButton(Swinger.getResource("HideButton_no_Selected.png"));
	
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
		
		quitButton.setBounds(1365 - 128, 0);
		quitButton.setSize(128, 64);
		quitButton.setTextureHover(Swinger.getResource("QuitButton_selected.png"));
		quitButton.addEventListener(this);
		this.add(quitButton);
		
		hideButton.setBounds(1365-280, 0);
		hideButton.setSize(128, 64);
		hideButton.setTextureHover(Swinger.getResource("HideButton_selected.png"));
		hideButton.addEventListener(this);
		this.add(hideButton);
	}



	@Override
	public void onEvent(SwingerEvent e) 
	{
		if (e.getSource() == playButton)
		{
			
		}
		else if (e.getSource() == quitButton)
		{
			System.exit(0);
		}
		else if (e.getSource() == hideButton)
		{
			LauncherFrame.getInstance().setState(JFrame.ICONIFIED);
		}
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
