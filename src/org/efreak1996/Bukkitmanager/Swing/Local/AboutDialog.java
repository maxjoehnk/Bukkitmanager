package org.efreak1996.Bukkitmanager.Swing.Local;

import javax.swing.JComponent;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;

public class AboutDialog extends StandardDialog {

	private static final long serialVersionUID = 4690547169315244840L;

	public AboutDialog() {
		super();
		setTitle("About");
		setBounds(100, 100, 550, 250);
	}
	
	@Override
	public JComponent createBannerPanel() {
		return null;
	}

	@Override
	public ButtonPanel createButtonPanel() {
		ButtonPanel buttonPanel = new ButtonPanel();
		return buttonPanel;
	}

	@Override
	public JComponent createContentPanel() {
		return null;
	}

}
