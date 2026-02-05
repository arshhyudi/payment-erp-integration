package com.apiintegration.entity.Token;

import com.apiintegration.entity.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean revoked;


}
