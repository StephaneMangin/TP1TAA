package org.istic.taa.todoapp.web.rest.mapper;

import org.istic.taa.todoapp.domain.*;
import org.istic.taa.todoapp.web.rest.dto.TODOItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TODOItem and its DTO TODOItemDTO.
 */
@Mapper(componentModel = "spring", uses = {OwnerMapper.class, })
public interface TODOItemMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.name", target = "ownerName")
    TODOItemDTO tODOItemToTODOItemDTO(TODOItem tODOItem);

    @Mapping(source = "ownerId", target = "owner")
    TODOItem tODOItemDTOToTODOItem(TODOItemDTO tODOItemDTO);

    default Owner OwnerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Owner Owner = new Owner();
        Owner.setId(id);
        return Owner;
    }
}
