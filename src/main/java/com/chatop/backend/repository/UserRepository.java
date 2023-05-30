package com.chatop.backend.repository;

import org.springframework.data.repository.CrudRepository;
import com.chatop.backend.model.User;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
