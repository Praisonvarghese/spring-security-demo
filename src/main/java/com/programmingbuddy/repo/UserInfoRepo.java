package com.programmingbuddy.repo;

import com.programmingbuddy.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepo extends JpaRepository<UserInfo,Long> {
    Optional<UserInfo> findByUsername(String username);
}
