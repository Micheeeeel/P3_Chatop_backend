package com.chatop.backend.repository;

import org.springframework.data.repository.CrudRepository;
import com.chatop.backend.model.DAOUser;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<DAOUser, Integer> {
    DAOUser findByName(String username);

    DAOUser findByEmail(String email);

}
