package org.istic.taa.todoapp.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Owner entity.
 */
public class OwnerDTO implements Serializable {

    private Long id;

    private String name;

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long UserId) {
        this.userId = UserId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String UserLogin) {
        this.userLogin = UserLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OwnerDTO ownerDTO = (OwnerDTO) o;

        if ( ! Objects.equals(id, ownerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OwnerDTO{" +
                "id=" + id +
                ", name='" + name + "'" +
                '}';
    }
}
