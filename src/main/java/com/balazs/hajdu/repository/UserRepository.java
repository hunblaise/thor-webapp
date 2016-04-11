package com.balazs.hajdu.repository;

import com.balazs.hajdu.domain.repository.MeasurementResultEntity;
import com.balazs.hajdu.domain.repository.SensorEntity;
import com.balazs.hajdu.domain.repository.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * User data related repository.
 *
 * @author Balazs Hajdu
 */
public interface UserRepository extends MongoRepository<UserEntity, ObjectId>, UserRepositoryCustom {

    UserEntity findOneByUsernameAndPassword(String username, String password);

    UserEntity findOneByUsername(String username);

}
