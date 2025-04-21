package com.enterapp.enterapp.repository;

import com.enterapp.enterapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long aLong);

    @Query("SELECT u FROM User u WHERE id=:id")
    Optional<User> findUserById(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE id != :id")
    List<User> findUserOtherthanLoggedin(@Param("id") Integer id);




//    User findByPhone(String formattedPh);

    Optional<User> findByPhone(Long phone);
}
