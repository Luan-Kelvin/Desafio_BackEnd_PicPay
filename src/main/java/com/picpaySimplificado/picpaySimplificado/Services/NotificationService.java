package com.picpaySimplificado.picpaySimplificado.Services;

import com.picpaySimplificado.picpaySimplificado.DTOs.NotificationDTO;
import com.picpaySimplificado.picpaySimplificado.Domain.User;
import com.picpaySimplificado.picpaySimplificado.Exceptions.NotificationServiceDownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message){
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        ResponseEntity<String> response = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);

        if (!(response.getStatusCode() == HttpStatus.OK)){
            throw new NotificationServiceDownException("ERRO! Serviço de notificação fora do ar.");
        }
    }

}
