package com.bajajfinserv.webhookchallenge;

import com.bajajfinserv.webhookchallenge.service.WebhookService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class WebhookChallengeApplication implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(WebhookChallengeApplication.class);
    
    @Autowired
    private WebhookService webhookService;

    public static void main(String[] args) {
        SpringApplication.run(WebhookChallengeApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Application started. Initiating webhook challenge...");
        webhookService.executeWebhookChallenge();
    }
}