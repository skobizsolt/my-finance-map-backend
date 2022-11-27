package com.myfinancemap.app.persistence.domain.auth;

import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.util.TokenUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "password_token")
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    private String token;
    private Date expiryDate;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId",
            referencedColumnName = "userId",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_PASSWORD_TOKEN"))
    private User user;

    public PasswordResetToken(final String token, final User user) {
        super();
        this.token = token;
        this.user = user;
        this.expiryDate = TokenUtils.calculateExpiryDate(EXPIRATION);
    }

    public PasswordResetToken(final String token) {
        super();
        this.token = token;
        this.expiryDate = TokenUtils.calculateExpiryDate(EXPIRATION);
    }
}
