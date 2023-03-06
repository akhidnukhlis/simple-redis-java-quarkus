package akhid.development.util;

import java.util.Random;

public class Otp {
    final int lowRange = 100000;
    final int highRange = 999999;
    public String session_key;
    public int otp_value;

    public Otp(String session_key) {
        this.session_key = session_key;
        this.otp_value = generateRandomOtp(lowRange, highRange);
    }

    public Otp() {}

    private static int generateRandomOtp(int low, int high) {

        // Generate random int value from $low to ($high - 1)
        return low + new Random().nextInt(high - low);
    }
}
