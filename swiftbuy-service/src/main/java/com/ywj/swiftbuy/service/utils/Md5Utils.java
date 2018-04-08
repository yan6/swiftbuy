package com.ywj.swiftbuy.service.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {

    public static String md5(String params) {
        try {
            byte[] bytes = params.getBytes("UTF-8");
            return md5(bytes);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
    }

    public static String md5(byte[] bytes) {
        byte[] md5bytes;
        try {
            md5bytes = MessageDigest
                    .getInstance("MD5")
                    .digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        }

        StringBuilder builder = new StringBuilder(2 * md5bytes.length);
        int j = 0;
        while (true) {
            int k = md5bytes[j];
            if ((k & 0xFF) < 16)
                builder.append("0");
            builder.append(Integer.toHexString(k & 0xFF));
            j++;
            if (j >= md5bytes.length)
                return builder.toString();
        }
    }

    public static String generateToken(String params) {
        byte[] md5bytes;
        try {
            md5bytes = MessageDigest
                .getInstance("MD5")
                .digest(params.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e1) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e1);
        }

        StringBuilder builder = new StringBuilder(md5bytes.length);
        for (int j = md5bytes.length - 1; j > 0; j = j - 2) {
            int k = md5bytes[j] + j;
            if ((k & 0xFF) < 16) {
                builder.append("0");
            }
            builder.append(Integer.toHexString(k & 0xFF));
        }
        return builder.toString();
    }
}
