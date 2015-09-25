package org.istic.taa.todoapp.repository;

import org.istic.taa.todoapp.domain.Owner;
import org.istic.taa.todoapp.domain.TODOItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TODOItem entity.
 */
public interface TODOItemRepository extends JpaRepository<TODOItem,Long> {

    Page<TODOItem> findAllByOwner(Owner o, Pageable p);

    @Query("select distinct tODOItem from TODOItem tODOItem left join fetch tODOItem.sharedOwners")
    List<TODOItem> findAllWithEagerRelationships();

    @Query("select tODOItem from TODOItem tODOItem left join fetch tODOItem.sharedOwners where tODOItem.id =:id")
    TODOItem findOneWithEagerRelationships(@Param("id") Long id);

}
