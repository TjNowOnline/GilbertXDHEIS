package org.example.gilbertxdheis.application;

import org.example.gilbertxdheis.domain.Feedback;
import org.example.gilbertxdheis.infrastructure.JdbcUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {

    private final JdbcUserRepository userRepository;

    public FeedbackService(JdbcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveFeedback(int orderId, int itemId, int rating, String comment) {
        Feedback feedback = new Feedback();
        feedback.setOrderId(orderId);
        feedback.setItemId(itemId);
        feedback.setRating(rating);
        feedback.setComment(comment);
        userRepository.saveFeedback(feedback);
    }

    public List<Feedback> getFeedbackBySeller(Long sellerId) {
        return userRepository.findFeedbackBySellerId(sellerId);
    }

    public List<Feedback> getFeedbackByItem(int itemId) {
        return userRepository.findFeedbackByItemId(itemId);
    }
}
