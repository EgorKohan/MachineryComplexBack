package com.bsuir.repositories;

import com.bsuir.models.ERole;
import com.bsuir.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(ERole roleType);

}
