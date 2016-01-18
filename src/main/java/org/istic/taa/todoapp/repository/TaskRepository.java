package org.istic.taa.todoapp.repository;

import org.istic.taa.todoapp.domain.Owner;
import org.istic.taa.todoapp.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Task entity.
 */
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("select distinct task" +
        " from Task task left join task.sharedOwners" +
        " where task.owner = :owner" +
        " or :owner member of task.sharedOwners")
    Page<Task> findAllByOwnerAndSharedOwners(@Param("owner") Owner o, Pageable p);

    @Query("select distinct task from Task task left join fetch task.sharedOwners")
    List<Task> findAllWithEagerRelationships();

    @Query("select task from Task task left join fetch task.sharedOwners where task.id =:id")
    Task findOneWithEagerRelationships(@Param("id") Long id);

}
