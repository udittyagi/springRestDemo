package com.springrestjosh.demo.repository;

import com.springrestjosh.demo.domain.TwitterAccount;
import com.springrestjosh.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TwitterRepository extends JpaRepository<TwitterAccount,Long> {
    Optional<TwitterAccount> findByAccountName(String accountName);
    List<TwitterAccount> findByUser(User user);
}
