package com.evergreen.zoo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendSMS {
    public static void sendsms(String phoneNumber, int message) {
        try {
            // URL of the web resource
            URL url = new URL(System.getenv("smsApi")+phoneNumber+"&message=Hello! Your secure verification code is  "+message);

            // Open connection
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            // Set request method to GET
            con.setRequestMethod("GET");

            // Optional: Set request headers
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Get the response code
            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            // Close connections
            in.close();

            // Print the response
            System.out.println("Response: " + response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
