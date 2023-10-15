package dev.salism3.lambdawallet.lib;

import javax.swing.JComponent;
import java.util.HashMap;
import java.util.Map;

public class Interactive {
    private JComponent child;
    public static Map<String, JComponent> states = new HashMap<>();
    
    public Interactive(String key, JComponent child) {
        Interactive.states.put(key, child);
        this.child = child;
    }

    public JComponent getChild() {
        return child;
    }

    public static JComponent getComponent(String key) {
        return Interactive.states.get(key);
    }
}
