package org.istic.taa.todoapp.web.rest.mapper;

import org.istic.taa.todoapp.domain.*;
import org.istic.taa.todoapp.web.rest.dto.TeamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Team and its DTO TeamDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TeamMapper {

    TeamDTO teamToTeamDTO(Team team);

    @Mapping(target = "tasks", ignore = true)
    Team teamDTOToTeam(TeamDTO teamDTO);
}
