package com.yiseull.dahaeng.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    protected String password;
    @Column(nullable = false)
    private String nickname;
    private int userColor;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
