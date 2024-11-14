package com.luv2code.spring_boot_library.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime; // Import LocalDateTime

@Entity
@Table(name = "review")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "date")
    @CreationTimestamp
    private LocalDateTime date; // Changed to LocalDateTime

    @Column(name = "rating") // Changed name to "rating"
    private double rating;

    @Column(name = "book_id") // Changed to book_id
    private Long bookId;

    @Column(name = "review_description")
    private String reviewDescription;
}
