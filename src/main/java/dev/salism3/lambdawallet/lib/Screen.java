package dev.salism3.lambdawallet.lib;

import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class Screen extends JPanel {

	private static final long serialVersionUID = 1L;

	public Screen() {
		super();
		this.setLayout(new GridLayout(1,1));
	}

	public void load() {

	}

	public void navigateTo(String screenName) {
		CardLayout cl = (CardLayout) this.getParent().getLayout();
		cl.show(this.getParent(), screenName);
		ScreenManager.getScreens().get(screenName).load();

	}

}
