package com.skillmatch.user_service.service;

import com.skillmatch.user_service.dto.FeedbackDTO;
import com.skillmatch.user_service.dto.FeedbackRequest;
import com.skillmatch.user_service.dto.FeedbackSummary;
import com.skillmatch.user_service.model.Feedback;
import com.skillmatch.user_service.model.User;
import com.skillmatch.user_service.repository.FeedbackRepository;
import com.skillmatch.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepo;
    private final UserRepository userRepo;

    public FeedbackDTO createFeedback(Long raterId, FeedbackRequest req) {

        User professional = userRepo.findById(req.getProfessionalId())
                .orElseThrow(() -> new RuntimeException("Professional not found"));

        Feedback fb = Feedback.builder()
                .contractId(req.getContractId())
                .professional(professional)
                .raterId(raterId)
                .rating(req.getRating())
                .comment(req.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        Feedback saved = feedbackRepo.save(fb);
        updateProfessionalReputation(professional);

        return toDTO(saved);
    }

    public FeedbackSummary getSummary(Long professionalId) {
        User professional = userRepo.findById(professionalId)
                .orElseThrow(() -> new RuntimeException("Professional not found"));

        var list = feedbackRepo.findByProfessional(professional);
        if (list.isEmpty()) {
            return new FeedbackSummary(0.0, 0L, "Junior");
        }

        DoubleSummaryStatistics stats = list.stream()
                .mapToDouble(Feedback::getRating)
                .summaryStatistics();

        double avg = stats.getAverage();
        long count = stats.getCount();
        String level = computeLevel(avg, count);

        return new FeedbackSummary(avg, count, level);
    }

    public List<FeedbackDTO> getAllFeedbackForProfessional(User professional) {
        return feedbackRepo.findByProfessional(professional)
                .stream()
                .map(this::toDTO)
                .toList();
    }



    private void updateProfessionalReputation(User professional) {
        var list = feedbackRepo.findByProfessional(professional);

        if (list.isEmpty()) {
            professional.setReputationAverage(0.0);
            professional.setReputationCount(0);
            professional.setReputationLevel("Junior");
        } else {
            DoubleSummaryStatistics stats = list.stream()
                    .mapToDouble(Feedback::getRating)
                    .summaryStatistics();
            double avg = stats.getAverage();
            long count = stats.getCount();

            professional.setReputationAverage(avg);
            professional.setReputationCount((int) count);
            professional.setReputationLevel(computeLevel(avg, count));
        }

        userRepo.save(professional);
    }

    private String computeLevel(double avg, long count) {
        if (count < 3) return "Junior";
        if (avg < 3.5) return "Junior";
        if (avg < 4.5) return "Affidabile";
        return "Top Performer";
    }

    private FeedbackDTO toDTO(Feedback fb) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(fb.getId());
        dto.setContractId(fb.getContractId());
        dto.setProfessionalId(fb.getProfessional().getId());
        dto.setRaterId(fb.getRaterId());
        dto.setRating(fb.getRating());
        dto.setComment(fb.getComment());
        dto.setCreatedAt(fb.getCreatedAt());
        return dto;
    }
}