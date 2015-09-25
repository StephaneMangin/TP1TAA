package org.istic.taa.todoapp.web.rest.dto;

import org.joda.time.DateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the TODOItem entity.
 */
public class TODOItemDTO implements Serializable {

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
        return sharedOwners;
    }

    public void setSharedOwners(Set<OwnerDTO> Owners) {
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

        TODOItemDTO tODOItemDTO = (TODOItemDTO) o;

        if ( ! Objects.equals(id, tODOItemDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TODOItemDTO{" +
                "id=" + id +
                ", content='" + content + "'" +
                ", endDate='" + endDate + "'" +
                ", done='" + done + "'" +
                '}';
    }
}
