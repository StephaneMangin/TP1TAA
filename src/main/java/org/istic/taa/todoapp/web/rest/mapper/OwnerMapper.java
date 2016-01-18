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
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "sharedTasks", ignore = true)
    Owner ownerDTOToOwner(OwnerDTO ownerDTO);

    default User UserFromId(Long id) {
        if (id == null) {
            return null;
        }
        User User = new User();
        User.setId(id);
        return User;
    }
}
