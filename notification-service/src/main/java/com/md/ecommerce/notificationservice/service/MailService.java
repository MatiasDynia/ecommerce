package com.md.ecommerce.notificationservice.service;

public interface MailService {

    public void sendEmail(String to, String subject, String text);
}
