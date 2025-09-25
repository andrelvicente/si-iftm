package com.example.example.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.example.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    
}