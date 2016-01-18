package org.istic.taa.todoapp.repository;

import org.istic.taa.todoapp.domain.Team;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Team entity.
 */
public interface TeamRepository extends JpaRepository<Team,Long> {

}
