package com.enterapp.enterapp.repository;

import com.enterapp.enterapp.entity.ChatDetails;
import com.enterapp.enterapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatDetailsRepository extends JpaRepository<ChatDetails, Long> {
    @Override
    Optional<ChatDetails> findById(Long aLong);

    @Query("SELECT C FROM ChatDetails C WHERE (C.receiver = :receiver OR C.sender = :receiver)  AND (C.receiver = :sender OR C.sender = :sender)")
    List<ChatDetails> fetchChats(@Param("sender") User sender, @Param("receiver") User receiver);

    @Query(value = "SELECT C.* FROM chat_details C " +
            "WHERE C.msg_id IN (" +
            "SELECT MAX(CD.msg_id) FROM chat_details CD " +
            "WHERE (CD.sender = :loggedUser OR CD.receiver = :loggedUser) " +
            "GROUP BY CASE WHEN CD.sender = :loggedUser THEN CD.receiver ELSE CD.sender END)",
            nativeQuery = true)
    List<ChatDetails> fetchLastChat(@Param("loggedUser") Long loggedUser);




}
