package com.backend.electroghiurai.repo;

import com.backend.electroghiurai.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    @Query(value
            = "SELECT * FROM FEEDBACK ORDER BY RATING DESC FETCH FIRST 4 ROWS ONLY",
            nativeQuery = true)
    List<Feedback> getFeedbacksByBestRating();
}
