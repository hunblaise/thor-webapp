package com.balazs.hajdu.repository;

import com.balazs.hajdu.domain.repository.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * User data related repository.
 *
 * @author Balazs Hajdu
 */
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {

    UserEntity findOneByUsernameAndPassword(String username, String password);

    UserEntity findOneByUsername(String username);

}
