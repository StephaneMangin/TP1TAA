package org.istic.taa.todoapp.web.rest.mapper;

import org.istic.taa.todoapp.domain.*;
import org.istic.taa.todoapp.web.rest.dto.TaskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Task and its DTO TaskDTO.
 */
@Mapper(componentModel = "spring", uses = {OwnerMapper.class, })
public interface TaskMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.name", target = "ownerName")
    TaskDTO taskToTaskDTO(Task task);

    @Mapping(source = "ownerId", target = "owner")
    Task taskDTOToTask(TaskDTO taskDTO);

    default Owner OwnerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Owner Owner = new Owner();
        Owner.setId(id);
        return Owner;
    }
}
