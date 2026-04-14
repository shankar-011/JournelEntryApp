package com.example.notesApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    private static final long OTP_TTL = 5; // minutes

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public void sendOtp(String email) {

        String key = "otp:" + email;

        String otp = String.valueOf(100000 + new SecureRandom().nextInt(900000));

        String hashedOtp = passwordEncoder.encode(otp);

        redisTemplate.opsForValue().set(key, hashedOtp, OTP_TTL, TimeUnit.MINUTES);

        emailService.sendMail(email,"OTP FOR VERIFICATION", otp);
    }

    public boolean verifyOtp(String email, String otp) {

        String key = "otp:" + email;

        String storedHash = redisTemplate.opsForValue().get(key);

        if (storedHash == null) return false;

        boolean matches = passwordEncoder.matches(otp, storedHash);

        if (matches) {
            redisTemplate.delete(key); // one-time use
        }

        return matches;
    }
}