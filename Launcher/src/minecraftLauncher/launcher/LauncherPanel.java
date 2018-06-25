package minecraftLauncher.launcher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.launcher.util.UsernameSaver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

@SuppressWarnings("serial")
public class LauncherPanel extends JPanel implements SwingerEventListener
{
	private Image background = Swinger.getResource("Background v1.2.png");
	
	private UsernameSaver saver = new UsernameSaver(Launcher.ML_INFOS);
	
	private JTextField usernameField = new JTextField(saver.getUsername(""));
	private JTextField passwordField = new JPasswordField();
	
	private STexturedButton playButton = new STexturedButton(Swinger.getResource("PlayButton_no_Selected.png"));
	private STexturedButton quitButton = new STexturedButton(Swinger.getResource("QuitButton_no_Selected.png"));
	private STexturedButton hideButton = new STexturedButton(Swinger.getResource("HideButton_no_Selected.png"));
	
	private SColoredBar progressBar = new SColoredBar(Swinger.getTransparentWhite(100), Swinger.getTransparentWhite(175));
	private JLabel infoLabel = new JLabel("Click to Play !", SwingConstants.CENTER);
	
	public LauncherPanel() {
		this.setLayout(null);
		
		// Translucid background
		this.setBackground(new Color(1, 1, 1, 0));
		
		// Username Field
		usernameField.setBounds(50, 100, 200, 20);
		usernameField.setBackground(Color.WHITE);
		usernameField.setFont(usernameField.getFont().deriveFont(15F));
		this.add(usernameField);

		
		// Password Field
		passwordField.setBounds(50, 130, 200, 20);
		passwordField.setBackground(Color.WHITE);
		passwordField.setFont(passwordField.getFont().deriveFont(15F));
		this.add(passwordField);
		
		playButton.setBounds(50, 170);
		playButton.setSize(playButton.getSize().width / 3, playButton.getSize().height / 3);
		
		playButton.addEventListener(this);
		this.add(playButton);
		
		quitButton.setBounds(400, 5);
		quitButton.setSize(32, 16);
		quitButton.setTextureHover(Swinger.getResource("QuitButton_selected.png"));
		quitButton.addEventListener(this);
		this.add(quitButton);
		
		hideButton.setBounds(360, 5);
		hideButton.setSize(32, 16);
		hideButton.setTextureHover(Swinger.getResource("HideButton_selected.png"));
		hideButton.addEventListener(this);
		this.add(hideButton);
		
		progressBar.setBounds(5, 235, 470, 10);
		this.add(progressBar);
		
		/*
		infoLabel.setFont(infoLabel.getFont().deriveFont(20F));
		infoLabel.setForeground(Color.WHITE);
		infoLabel.setBounds(10, 20, 400, 30);
		this.add(infoLabel);
		*/
	}



	@Override
	public void onEvent(SwingerEvent e) 
	{
		if (e.getSource() == playButton)
		{
			setFieldsEnabled(false);
			
			if (usernameField.getText().replaceAll(" ", "").length() == 0 || passwordField.getText().length() == 0)
			{
				JOptionPane.showMessageDialog(this, "Erreur, identifiant ou mot de passe invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
				setFieldsEnabled(true);
				return;
			}
			
			Thread t = new Thread() 
			{
				@Override
				public void run()
				{
					try 
					{
						Launcher.auth(usernameField.getText(), passwordField.getText());
					}
					catch (AuthenticationException e) 
					{
						JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur, impossible de se connecter :" + e.getErrorModel().getErrorMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
						setFieldsEnabled(true);
						return;
					}

					try 
					{
						Launcher.update();
					}
					catch (Exception e) 
					{
						Launcher.interruptThread();
						JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur, mise � jour impossible", "Erreur", JOptionPane.ERROR_MESSAGE);
						setFieldsEnabled(true);
						return;
					}

					try 
					{
						Launcher.launch();
					}
					catch (IOException e) 
					{
						Launcher.interruptThread();
						JOptionPane.showMessageDialog(LauncherPanel.this, "Erreur, impossible de lacer le jeu", "Erreur", JOptionPane.ERROR_MESSAGE);
						setFieldsEnabled(true);
						return;
					}
				}
			};
			t.start();
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
	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);

		Swinger.drawFullsizedImage(graphics, this, background);
	}
	
	private void setFieldsEnabled(boolean enabled)
	{
		usernameField.setEnabled(enabled);
		passwordField.setEnabled(enabled);
	}
	
	public SColoredBar getProgressBar()
	{
		return progressBar;
	}
	
	public void setInfoText(String text)
	{
		infoLabel.setText(text);
	}
}
