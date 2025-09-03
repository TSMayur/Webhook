package com.bajajfinserv.webhookchallenge.service;

import com.bajajfinserv.webhookchallenge.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

    private static final Logger logger = LoggerFactory.getLogger(WebhookService.class);
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    private static final String GENERATE_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String TEST_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";
    
    public WebhookService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public void executeWebhookChallenge() {
        try {
            // Step 1: Generate webhook
            WebhookRequest request = new WebhookRequest("John Doe", "REG12347", "john@example.com");
            WebhookResponse response = generateWebhook(request);
            
            if (response == null) {
                logger.error("Failed to generate webhook");
                return;
            }
            
            logger.info("Webhook generated successfully. URL: {}", response.getWebhook());
            
            // Step 2: Determine question based on regNo last two digits
            String regNo = request.getRegNo();
            int lastTwoDigits = Integer.parseInt(regNo.substring(regNo.length() - 2));
            boolean isOdd = lastTwoDigits % 2 != 0;
            
            logger.info("Registration number: {}, Last two digits: {}, Question type: {}", 
                regNo, lastTwoDigits, isOdd ? "Question 1 (Odd)" : "Question 2 (Even)");
            
            // Step 3: Solve SQL problem and get final query
            String finalQuery = getSqlSolution(isOdd);
            
            // Step 4: Submit solution
            submitSolution(response.getAccessToken(), finalQuery);
            
        } catch (Exception e) {
            logger.error("Error executing webhook challenge", e);
        }
    }

    private WebhookResponse generateWebhook(WebhookRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);
            
            logger.info("Sending POST request to generate webhook...");
            ResponseEntity<String> response = restTemplate.postForEntity(
                GENERATE_WEBHOOK_URL, entity, String.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Webhook generation successful. Response: {}", response.getBody());
                return objectMapper.readValue(response.getBody(), WebhookResponse.class);
            } else {
                logger.error("Failed to generate webhook. Status: {}, Body: {}", 
                    response.getStatusCode(), response.getBody());
                return null;
            }
            
        } catch (Exception e) {
            logger.error("Error generating webhook", e);
            return null;
        }
    }

    private String getSqlSolution(boolean isOdd) {
        if (isOdd) {
            // Question 1 (Odd regNo ending)
            logger.info("Solving Question 1 (Odd registration number)");
            // TODO: Replace with actual Question 1 solution from Google Drive
            return "SELECT * FROM users WHERE status = 'active' ORDER BY created_date DESC";
        } else {
            // Question 2 (Even regNo ending)  
            logger.info("Solving Question 2 (Even registration number)");
            // TODO: Replace with actual Question 2 solution from Google Drive
            return "SELECT COUNT(*) as total_orders FROM orders WHERE order_date >= '2023-01-01'";
        }
    }

    private void submitSolution(String accessToken, String finalQuery) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);
            
            SolutionRequest solutionRequest = new SolutionRequest(finalQuery);
            HttpEntity<SolutionRequest> entity = new HttpEntity<>(solutionRequest, headers);
            
            logger.info("Submitting solution with query: {}", finalQuery);
            ResponseEntity<String> response = restTemplate.postForEntity(
                TEST_WEBHOOK_URL, entity, String.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Solution submitted successfully! Response: {}", response.getBody());
            } else {
                logger.error("Failed to submit solution. Status: {}, Body: {}", 
                    response.getStatusCode(), response.getBody());
            }
            
        } catch (Exception e) {
            logger.error("Error submitting solution", e);
        }
    }
}