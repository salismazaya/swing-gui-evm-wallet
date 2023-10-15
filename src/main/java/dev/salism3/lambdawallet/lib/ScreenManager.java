package dev.salism3.lambdawallet.lib;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class ScreenManager extends JPanel {

	private static final long serialVersionUID = 1L;
    private static Map<String, Screen> screens = new HashMap<>();

	public ScreenManager() {
		setLayout(new CardLayout());
	}
	
	public void addScreen(Screen screen, String screenName) {
		screens.put(screenName, screen);
		add(screen, screenName);
	}
	

	public static Map<String, Screen> getScreens() {
		return ScreenManager.screens;
	}
}