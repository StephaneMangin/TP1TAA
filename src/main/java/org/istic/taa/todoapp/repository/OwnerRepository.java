package org.istic.taa.todoapp.repository;

import org.istic.taa.todoapp.domain.Owner;
import org.istic.taa.todoapp.domain.User;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Owner entity.
 */
public interface OwnerRepository extends JpaRepository<Owner,Long> {

    Optional<Owner> findOneByName(String n);

    Optional<Owner> findOneByUserId(Long id);

}
