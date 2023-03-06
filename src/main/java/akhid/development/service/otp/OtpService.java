package akhid.development.service.otp;

import akhid.development.util.Otp;
import io.quarkus.redis.client.RedisClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class OtpService {
    final int timeInSeconds = 20;

    @Inject
    RedisClient redisClient;

    public String getOtp(String session_key) {
        return redisClient.get(session_key).toString();
    }

    public void newOtp(String session_key) {
        Otp otp = new Otp(session_key);

        // SETNX akan membuat session key baru key jika tidak ditemukan.
        if (redisClient.setnx(otp.session_key.toString(),
                String.valueOf(otp.otp_value)).toBoolean()) {

            // Hanya update TTL/expiration jika session key sudah ada.
            redisClient.setex(otp.session_key.toString(),
                    String.valueOf(timeInSeconds),
                    String.valueOf(otp.otp_value));
        }
    }

    public String getOtpTTL(String session_key) {
        return redisClient.ttl(session_key).toString();
    }

    public boolean keyExists(String session_key) {
        return redisClient.exists(Arrays.asList(session_key)).toBoolean();
    }
}
