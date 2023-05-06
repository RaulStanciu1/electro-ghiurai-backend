package com.backend.electroghiurai.service;

import com.backend.electroghiurai.entity.Feedback;
import com.backend.electroghiurai.repo.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository repository;
    public Feedback saveFeedback(Feedback feedback,Long customerId){
        feedback.setCustomer(customerId);
        return repository.save(feedback);
    }
}
