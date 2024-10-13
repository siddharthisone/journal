package com.project.myFirstProject.repository;

import com.project.myFirstProject.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, ObjectId> {

    User findByUsername(String username);

    void deleteByUsername(String name);
}
