package org.istic.taa.todoapp.web.rest.mapper;

import org.istic.taa.todoapp.domain.*;
import org.istic.taa.todoapp.web.rest.dto.TODOItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TODOItem and its DTO TODOItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TODOItemMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.login", target = "ownerLogin")
    TODOItemDTO tODOItemToTODOItemDTO(TODOItem tODOItem);

    @Mapping(source = "ownerId", target = "owner")
    TODOItem tODOItemDTOToTODOItem(TODOItemDTO tODOItemDTO);

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
