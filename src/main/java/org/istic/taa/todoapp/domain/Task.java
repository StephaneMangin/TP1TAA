package org.istic.taa.todoapp.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.istic.taa.todoapp.domain.util.CustomDateTimeDeserializer;
import org.istic.taa.todoapp.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Task.
 */
@Entity
@Table(name = "TASK")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "content")
    private String content;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "end_date")
    private DateTime endDate;

    @Column(name = "done")
    private Boolean done;

    @ManyToOne
    private Owner owner;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "TASK_SHARED_OWNER",
               joinColumns = @JoinColumn(name="tasks_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="shared_owners_id", referencedColumnName="ID"))
    private Set<Owner> sharedOwners = new HashSet<>();

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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner Owner) {
        this.owner = Owner;
    }

    public Set<Owner> getSharedOwners() {
        return sharedOwners;
    }

    public void setSharedOwners(Set<Owner> Owners) {
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

        Task task = (Task) o;

        if ( ! Objects.equals(id, task.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", content='" + content + "'" +
                ", owner='" + owner + "'" +
                ", endDate='" + endDate + "'" +
                ", done='" + done + "'" +
                ", sharedWith='" + sharedOwners + "'" +
                '}';
    }
}
