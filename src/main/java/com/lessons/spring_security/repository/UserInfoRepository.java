package com.lessons.spring_security.repository;

import com.lessons.spring_security.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo,Integer> {
    public Optional<UserInfo> findByName(String name);
}
