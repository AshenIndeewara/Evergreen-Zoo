package com.evergreen.zoo.otpSend;
import com.resend.*;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

public class SendEmail {
    public static void sendEmail(String email, String otp) {
        Resend resend = new Resend(System.getenv("resendKey"));

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("evergreen@sldatabase.ninja")
                .to(email)
                .subject("Your, Evergreen OTP "+otp)
                .html("<!DOCTYPE html>" +
                        "<html lang=\"en\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                        "    <title>OTP Verification - Evergreen</title>" +
                        "    <style>" +
                        "        body {" +
                        "            font-family: Arial, sans-serif;" +
                        "            background-color: #f4f4f4;" +
                        "            margin: 0;" +
                        "            padding: 20px;" +
                        "        }" +
                        "        .email-container {" +
                        "            background-color: #ffffff;" +
                        "            margin: 0 auto;" +
                        "            padding: 20px;" +
                        "            border-radius: 8px;" +
                        "            max-width: 600px;" +
                        "            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);" +
                        "        }" +
                        "        .email-header {" +
                        "            background-color: #28a745;" +
                        "            color: white;" +
                        "            padding: 10px;" +
                        "            border-radius: 8px 8px 0 0;" +
                        "            text-align: center;" +
                        "        }" +
                        "        .email-content {" +
                        "            padding: 20px;" +
                        "            text-align: center;" +
                        "        }" +
                        "        .otp-code {" +
                        "            font-size: 24px;" +
                        "            color: #333;" +
                        "            font-weight: bold;" +
                        "            margin: 20px 0;" +
                        "        }" +
                        "        .email-footer {" +
                        "            margin-top: 20px;" +
                        "            text-align: center;" +
                        "            font-size: 14px;" +
                        "            color: #888;" +
                        "        }" +
                        "    </style>" +
                        "</head>" +
                        "<body>" +
                        "    <div class=\"email-container\">" +
                        "        <div class=\"email-header\">" +
                        "            <h1>Evergreen</h1>" +
                        "        </div>" +
                        "        <div class=\"email-content\">" +
                        "            <h2>OTP Verification</h2>" +
                        "            <p>Dear Customer,</p>" +
                        "            <p>Thank you for choosing Evergreen. To complete your login or verification process, please use the following OTP:</p>" +
                        "            <div class=\"otp-code\">"+otp+"</div>" +
                        "            <p>This OTP is valid for 10 minutes. Please do not share it with anyone.</p>" +
                        "            <p>If you did not request this, please ignore this email.</p>" +
                        "        </div>" +
                        "        <div class=\"email-footer\">" +
                        "            <p>Evergreen Company</p>" +
                        "            <p>&copy; 2024 Evergreen. All rights reserved.</p>" +
                        "        </div>" +
                        "    </div>" +
                        "</body>" +
                        "</html>")
                .build();

        try {
            CreateEmailResponse data = resend.emails().send(params);
            System.out.println(data.getId());
        } catch (ResendException e) {
            e.printStackTrace();
        }
    }
}
