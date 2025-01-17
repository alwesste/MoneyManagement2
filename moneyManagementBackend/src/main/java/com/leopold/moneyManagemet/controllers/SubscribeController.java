package com.leopold.moneyManagemet.controllers;

import com.leopold.moneyManagemet.models.SubscriberData;
import com.leopold.moneyManagemet.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscribe")
@CrossOrigin(origins = "http://localhost:4200")
public class SubscribeController {

    @Autowired
    private SubscriberService subscriberService;

    @PostMapping("/form")
    public ResponseEntity<?> handleFormSubmission(@RequestBody SubscriberData subscriberData) {

        if (subscriberData.getPassword() == null || !subscriberData.getPassword().equals(subscriberData.getPasswordConfirm())) {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        if (subscriberService.existSubscriber(subscriberData)) {
            return new ResponseEntity<>("Subscriber already exists", HttpStatus.CONFLICT);
        }
        subscriberService.addSubscriber(subscriberData);

        return new ResponseEntity<>(Map.of("message", "Form submitted successfully!"), HttpStatus.OK);
    }

    @GetMapping("/getSubscriber")
    public ResponseEntity<SubscriberData> getSubscriberId(@RequestParam int id) {
        Optional<SubscriberData> subscriberData = subscriberService.getSubscriberById(id);
        if (subscriberData.isPresent()) {
            return ResponseEntity.ok(subscriberData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
