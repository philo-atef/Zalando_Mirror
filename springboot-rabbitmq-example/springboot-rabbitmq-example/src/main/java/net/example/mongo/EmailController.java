package net.example.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class EmailController {

    @Autowired
    //private EmailService emailService;
    private  EmailRepository emailRepository;

//    @PostMapping
//    public ResponseEntity<Email> addEmail(@RequestBody Email email) {
//        Email addedEmail = emailService.addEmail(email);
//        return new ResponseEntity<>(addedEmail, HttpStatus.CREATED);
//    }
    @GetMapping("/getAll")
    public List<Email> getAllUser(){
        return emailRepository.findAll();
    }
    @PostMapping("/addEmail")
    public Email addUser(@RequestBody Email email) {
        return emailRepository.save(email);
    }





}
