package kube.launcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import fr.theshark34.openlauncherlib.LanguageManager;
import fr.theshark34.openlauncherlib.util.ramselector.AbstractOptionFrame;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;

@SuppressWarnings("serial")
public class LauncherRamSelectorWindow extends AbstractOptionFrame
{
	private JLabel ramLabel;
	private JComboBox<String> ramBox;

	private JLabel localNetworkLabel;
	private JCheckBox localNetworkCheckBox;

	public LauncherRamSelectorWindow(RamSelector selector)
	{
		super(selector);
		this.setIconImage(Swinger.getResource("Logo v1.0_partial.png"));

		this.setTitle(LanguageManager.lang("options"));
		this.setResizable(false);
		this.setSize(275, 120);
		this.setLocationRelativeTo(null);
		this.setLayout(null);

		ramLabel = new JLabel(LanguageManager.lang("ram") + " : ");
		ramLabel.setBounds(15, 20, 45, 25);
		this.add(ramLabel);

		ramBox = new JComboBox<String>(RamSelector.RAM_ARRAY);
		ramBox.setBounds(65, 20, 195, 25);
		this.add(ramBox);

		localNetworkLabel = new JLabel("Local Network File Download : ");
		localNetworkLabel.setBounds(15, 55, 200, 25);
		this.add(localNetworkLabel);

		localNetworkCheckBox = new JCheckBox("");
		localNetworkCheckBox.setOpaque(false);
		localNetworkCheckBox.setBounds(160, 55, 25, 25);
		localNetworkCheckBox.setSelected(false);
		localNetworkCheckBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				JCheckBox cb = (JCheckBox) event.getSource();
				if (cb.isSelected())
				{
					Launcher.setServerAddress("192.168.1.25");
				}
				else
				{
					Launcher.setServerAddress("robin-leclair.ovh");
				}
			}
		});
		this.add(localNetworkCheckBox);
	}

	@Override
	public int getSelectedIndex()
	{
		return ramBox.getSelectedIndex();
	}

	@Override
	public void setSelectedIndex(int index)
	{
		ramBox.setSelectedIndex(index);
	}

}
