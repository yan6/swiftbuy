package com.ywj.swiftbuy.service.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Calendar;

public class TestService {
    public static void test1(String[] args) {
        try {
            File file = new File("/Users/ywj/Downloads/synfollow.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                long uid = Long.valueOf(parts[0]);
                int authorId = Integer.valueOf(parts[1]);
                System.out.println("uid=" + uid + " authorId=" + authorId);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("read file error");
        }
    }

    public static void main(String[] args) {
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        System.out.println("currentHour=" + currentHour);
    }


}
