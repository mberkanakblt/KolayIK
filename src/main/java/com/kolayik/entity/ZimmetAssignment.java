package com.kolayik.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.kolayik.utility.enums.Status;

@Entity
@Table(name = "zimmet_assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZimmetAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    private String model;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private String feedback;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
