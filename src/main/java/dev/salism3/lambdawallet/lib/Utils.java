package dev.salism3.lambdawallet.lib;

import java.util.regex.Pattern;

public class Utils {
    public static boolean isValidEVMAddress(String address) {
        String evmAddressPattern = "^(0x)?[0-9a-fA-F]{40}$";
        Pattern pattern = Pattern.compile(evmAddressPattern);
        return pattern.matcher(address).matches();
    }
}
