package com.agent7799.springboot.security.firstsecurityapp.repositories;

import com.agent7799.springboot.security.firstsecurityapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    //Optional<List<Message>> findAllByUsername(String username);

    List<Message> findAllByUsername(String username);
}
