package org.istic.taa.todoapp.repository;

import org.istic.taa.todoapp.domain.Owner;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Owner entity.
 */
public interface OwnerRepository extends JpaRepository<Owner,Long> {

}
