package org.istic.taa.todoapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import jdk.nashorn.internal.runtime.options.Option;
import org.istic.taa.todoapp.domain.Authority;
import org.istic.taa.todoapp.domain.Owner;
import org.istic.taa.todoapp.domain.Task;
import org.istic.taa.todoapp.domain.User;
import org.istic.taa.todoapp.repository.AuthorityRepository;
import org.istic.taa.todoapp.repository.OwnerRepository;
import org.istic.taa.todoapp.repository.TaskRepository;
import org.istic.taa.todoapp.security.AuthoritiesConstants;
import org.istic.taa.todoapp.web.rest.util.HeaderUtil;
import org.istic.taa.todoapp.web.rest.util.PaginationUtil;
import org.istic.taa.todoapp.web.rest.dto.TaskDTO;
import org.istic.taa.todoapp.web.rest.mapper.TaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Task.
 */
@RestController
@RequestMapping("/api")
public class TaskResource {

    private final Logger log = LoggerFactory.getLogger(TaskResource.class);

    @Inject
    private OwnerRepository ownerRepository;

    @Inject
    private TaskRepository taskRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private TaskMapper taskMapper;

    private Owner getCurrentOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            Optional<Owner> owner = ownerRepository.findOneByName(username);
            if (owner.isPresent()) {
                return owner.get();
            }
        }
        return null;
    }

    /**
     * POST  /tasks -> Create a new task.
     */
    @Secured(AuthoritiesConstants.USER)
    @RequestMapping(value = "/tasks",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) throws URISyntaxException {
        log.debug("REST request to save Task : {}", taskDTO);
        if (taskDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new task cannot already have an ID").body(null);
        }
        Task task = taskMapper.taskDTOToTask(taskDTO);
        task.setOwner(getCurrentOwner());
        Task result = taskRepository.save(task);
        return ResponseEntity.created(new URI("/api/tasks/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("task", result.getId().toString()))
                .body(taskMapper.taskToTaskDTO(result));
    }

    /**
     * PUT  /tasks -> Updates an existing task.
     */
    @Secured(AuthoritiesConstants.USER)
    @RequestMapping(value = "/tasks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity updateTask(@RequestBody TaskDTO taskDTO) throws URISyntaxException {
        log.debug("REST request to update Task : {}", taskDTO);
        if (taskDTO.getId() == null) {
            return createTask(taskDTO);
        }
        Task task = taskMapper.taskDTOToTask(taskDTO);
        if (getCurrentOwner() != task.getOwner()) {
            return ResponseEntity.status(
                HttpStatus.FORBIDDEN
            ).headers(
                HeaderUtil.createEntityForbidden(
                    "task",
                    task.getId().toString()
                )
            ).build();
        }
        Task result = taskRepository.save(task);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("task", taskDTO.getId().toString()))
                .body(taskMapper.taskToTaskDTO(result));
    }

    /**
     * GET  /tasks -> get all the tasks.
     */
    @Secured(AuthoritiesConstants.USER)
    @RequestMapping(value = "/tasks",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TaskDTO>> getAllTasks(Pageable pageable)
        throws URISyntaxException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Page<Task> page = null;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            page = taskRepository.findAll(pageable);
        } else {
            page = taskRepository.findAllByOwnerAndSharedOwners(getCurrentOwner(), pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tasks");
        return new ResponseEntity<>(page.getContent().stream()
            .map(taskMapper::taskToTaskDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /tasks/:id -> get the "id" task.
     */
    @Secured(AuthoritiesConstants.USER)
    @RequestMapping(value = "/tasks/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long id) {
        log.debug("REST request to get Task : {}", id);
        return Optional.ofNullable(taskRepository.findOneWithEagerRelationships(id))
            .map(taskMapper::taskToTaskDTO)
            .map(taskDTO -> new ResponseEntity<>(
                taskDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tasks/:id -> delete the "id" task.
     */
    @Secured(AuthoritiesConstants.USER)
    @RequestMapping(value = "/tasks/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.debug("REST request to delete Task : {}", id);
        if (getCurrentOwner() != taskRepository.getOne(id).getOwner()) {
            return ResponseEntity.status(
                HttpStatus.FORBIDDEN
            ).headers(
                HeaderUtil.createEntityForbidden(
                    "task",
                    taskRepository.getOne(id).getId().toString()
                )
            ).build();
        }
        taskRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("task", id.toString())).build();
    }
}
