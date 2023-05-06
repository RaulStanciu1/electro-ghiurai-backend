package com.backend.electroghiurai.repo;

import com.backend.electroghiurai.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
}
