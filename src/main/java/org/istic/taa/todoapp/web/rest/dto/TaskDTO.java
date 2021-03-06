package org.istic.taa.todoapp.web.rest.dto;

import org.joda.time.DateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import java.util.function.Predicate;


/**
 * A DTO for the Task entity.
 */
public class TaskDTO implements Serializable {

    private Long id;

    private String content;

    private DateTime endDate;

    private Boolean done;

    private Long ownerId;

    private String ownerName;

    private Set<OwnerDTO> sharedOwners = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long OwnerId) {
        this.ownerId = OwnerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String OwnerName) {
        this.ownerName = OwnerName;
    }

    public Set<OwnerDTO> getSharedOwners() {
        sharedOwners.removeIf((OwnerDTO ownerDTO) -> ownerDTO.getId().equals(getOwnerId()));
        return sharedOwners;
    }

    public void setSharedOwners(Set<OwnerDTO> Owners) {
        sharedOwners.removeIf((OwnerDTO ownerDTO) -> ownerDTO.getId().equals(getOwnerId()));
        this.sharedOwners = Owners;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskDTO taskDTO = (TaskDTO) o;

        if ( ! Objects.equals(id, taskDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                ", content='" + content + "'" +
                ", endDate='" + endDate + "'" +
                ", done='" + done + "'" +
                '}';
    }
}
