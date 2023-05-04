package net.example.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }

    public Optional<Email> getEmailById(String id) {
        return emailRepository.findById(id);
    }

    public Email addEmail(Email email) {
        return emailRepository.insert(email);
    }

    public Email updateEmail(Email email) {
        return emailRepository.save(email);
    }

    public void deleteEmail(String id) {
        emailRepository.deleteById(id);
    }
}