package dev.salism3.lambdawallet.lib;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    private static Map<String, Object> data = new HashMap<>();
    
    public static void set(String key, Object object) {
        Memory.data.put(key, object);
    }

    public static Object get(String key) {
        return Memory.data.get(key);
    }
}
