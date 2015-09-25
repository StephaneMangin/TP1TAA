package org.istic.taa.todoapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.istic.taa.todoapp.domain.Owner;
import org.istic.taa.todoapp.repository.OwnerRepository;
import org.istic.taa.todoapp.security.AuthoritiesConstants;
import org.istic.taa.todoapp.web.rest.util.HeaderUtil;
import org.istic.taa.todoapp.web.rest.util.PaginationUtil;
import org.istic.taa.todoapp.web.rest.dto.OwnerDTO;
import org.istic.taa.todoapp.web.rest.mapper.OwnerMapper;
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
 * REST controller for managing Owner.
 */
@RestController
@RequestMapping("/api")
public class OwnerResource {

    private final Logger log = LoggerFactory.getLogger(OwnerResource.class);

    @Inject
    private OwnerRepository ownerRepository;

    @Inject
    private OwnerMapper ownerMapper;

    /**
     * POST  /owners -> Create a new owner.
     */
    @Secured(AuthoritiesConstants.ADMIN)
    @RequestMapping(value = "/owners",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OwnerDTO> createOwner(@RequestBody OwnerDTO ownerDTO) throws URISyntaxException {
        log.debug("REST request to save Owner : {}", ownerDTO);
        if (ownerDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new owner cannot already have an ID").body(null);
        }
        Owner owner = ownerMapper.ownerDTOToOwner(ownerDTO);
        Owner result = ownerRepository.save(owner);
        return ResponseEntity.created(new URI("/api/owners/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("owner", result.getId().toString()))
                .body(ownerMapper.ownerToOwnerDTO(result));
    }

    /**
     * PUT  /owners -> Updates an existing owner.
     */
    @Secured(AuthoritiesConstants.ADMIN)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/owners",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OwnerDTO> updateOwner(@RequestBody OwnerDTO ownerDTO) throws URISyntaxException {
        log.debug("REST request to update Owner : {}", ownerDTO);
        if (ownerDTO.getId() == null) {
            return createOwner(ownerDTO);
        }
        Owner owner = ownerMapper.ownerDTOToOwner(ownerDTO);
        Owner result = ownerRepository.save(owner);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("owner", ownerDTO.getId().toString()))
                .body(ownerMapper.ownerToOwnerDTO(result));
    }

    /**
     * GET  /owners -> get all the owners.
     */
    @Secured(AuthoritiesConstants.USER)
    @RequestMapping(value = "/owners",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<OwnerDTO>> getAllOwners(Pageable pageable)
        throws URISyntaxException {
        Page<Owner> page = ownerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/owners");
        return new ResponseEntity<>(page.getContent().stream()
            .map(ownerMapper::ownerToOwnerDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /owners/:id -> get the "id" owner.
     */
    @Secured(AuthoritiesConstants.USER)
    @RequestMapping(value = "/owners/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable Long id) {
        log.debug("REST request to get Owner : {}", id);
        return Optional.ofNullable(ownerRepository.findOne(id))
            .map(ownerMapper::ownerToOwnerDTO)
            .map(ownerDTO -> new ResponseEntity<>(
                ownerDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /owners/:id -> delete the "id" owner.
     */
    @Secured(AuthoritiesConstants.ADMIN)
    @RequestMapping(value = "/owners/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        log.debug("REST request to delete Owner : {}", id);
        ownerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("owner", id.toString())).build();
    }
}
