package com.vodacom.er.gui.service;

import com.vodacom.er.gui.entity.Subscription;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class RequestService {
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    public RequestService() {
        this.restTemplate = new RestTemplate();
    }

    public String makePostRequestWithCustomHeaders(Subscription _subscription, String url) {
        try {
            String xmlPayload = "<er-request id=\"100002\" client-application-id=\""+ _subscription.getCsr_id()+"\" purchase_locale=\"en_ZA\" language_locale=\"en_ZA\">\n"
                    + "  <payload>\n"
                    + "    <inactivate-subscription>\n"
                    + "      <msisdn>"+ _subscription.getMsisdn()+"</msisdn>\n"
                    + "      <subscription-id>"+ _subscription.getSubscription_id()+"</subscription-id>\n"
                    + "      <csr-id>"+ _subscription.getCsr_id()+"</csr-id>\n"
                    + "      <reason>"+ _subscription.getReason()+"</reason>\n"
                    + "    </inactivate-subscription>\n"
                    + "  </payload>\n"
                    + "</er-request>";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            headers.set("subscription_id", _subscription.getSubscription_id());

            // Create the HTTP entity with headers and body
            HttpEntity<String> requestEntity = new HttpEntity<String>(xmlPayload, headers);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

            String responseBody = responseEntity.getBody();

            logger.info("RequestService : makePostRequestWithCustomHeaders() Method Called : responseBody Data : "+responseBody);
            return responseBody;
        } catch (Exception e) {
            logger.error("RequestService : makePostRequestWithCustomHeaders() Method Called : Exception Error : "+e);
            // Handle the exception here
            e.printStackTrace();
            return null;
        }
    }

}
