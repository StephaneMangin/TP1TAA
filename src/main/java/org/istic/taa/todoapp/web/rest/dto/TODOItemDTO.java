package org.istic.taa.todoapp.web.rest.dto;

import org.joda.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the TODOItem entity.
 */
public class TODOItemDTO implements Serializable {

    private Long id;

    private String content;

    private LocalDate endDate;

    private Boolean done;

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

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
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
