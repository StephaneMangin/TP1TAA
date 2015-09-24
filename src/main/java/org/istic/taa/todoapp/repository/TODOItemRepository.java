package org.istic.taa.todoapp.repository;

import org.istic.taa.todoapp.domain.TODOItem;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TODOItem entity.
 */
public interface TODOItemRepository extends JpaRepository<TODOItem,Long> {

}
