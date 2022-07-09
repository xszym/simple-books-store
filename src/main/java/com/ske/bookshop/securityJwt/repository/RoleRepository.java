package com.ske.bookshop.securityJwt.repository;

import com.ske.bookshop.securityJwt.models.ERole;
import com.ske.bookshop.securityJwt.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface RoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);

}
