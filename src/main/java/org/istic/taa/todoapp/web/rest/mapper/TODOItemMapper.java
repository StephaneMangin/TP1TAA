package org.istic.taa.todoapp.web.rest.mapper;

import org.istic.taa.todoapp.domain.*;
import org.istic.taa.todoapp.web.rest.dto.TODOItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TODOItem and its DTO TODOItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TODOItemMapper {

    TODOItemDTO tODOItemToTODOItemDTO(TODOItem tODOItem);

    TODOItem tODOItemDTOToTODOItem(TODOItemDTO tODOItemDTO);
}
