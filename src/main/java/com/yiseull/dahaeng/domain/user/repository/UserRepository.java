package com.yiseull.dahaeng.domain.user.repository;

import com.yiseull.dahaeng.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    <S extends User> S save(S entity);

    Optional<User> findByEmailAndPassword(String email, String password);

    @Override
    void deleteById(Integer integer);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
