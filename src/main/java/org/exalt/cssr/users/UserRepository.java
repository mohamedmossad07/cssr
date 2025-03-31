package org.exalt.cssr.users;

import org.springframework.data.aerospike.repository.AerospikeRepository;

import java.util.Optional;

/**
 * Represents the DAO Layer for the users
 * fetching/inserting, dealing with users set or table in the database
 */
public interface UserRepository extends AerospikeRepository<User, String> {//User As an entity ro the repo + Identifier for the user id

    //AUTO-GENERATED
    Optional<User> findByUsername(String username);//find user by its username

    //AUTO-GENERATED
    Optional<User> findByEmail(String email);//find user by its email
}
