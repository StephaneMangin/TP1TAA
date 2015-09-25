package org.istic.taa.todoapp.web.rest.mapper;

import org.istic.taa.todoapp.domain.*;
import org.istic.taa.todoapp.web.rest.dto.OwnerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Owner and its DTO OwnerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OwnerMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    OwnerDTO ownerToOwnerDTO(Owner owner);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "TODOItems", ignore = true)
    Owner ownerDTOToOwner(OwnerDTO ownerDTO);

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
