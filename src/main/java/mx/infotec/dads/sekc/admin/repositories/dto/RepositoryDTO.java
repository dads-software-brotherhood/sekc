package mx.infotec.dads.sekc.admin.repositories.dto;

import javax.validation.constraints.*;

import static mx.infotec.dads.sekc.util.RegExpConstants.URL_REG_EXP;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Repository entity.
 * 
 * @author Daniel Cortes Pichardo
 */
public class RepositoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @NotNull
    @Size(min = 3)
    private String name;

    @NotNull
    @Size(min = 5)
    @Pattern(regexp = URL_REG_EXP)
    private String url;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RepositoryDTO repositoryDTO = (RepositoryDTO) o;
        if (repositoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), repositoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RepositoryDTO{" + "id=" + getId() + ", name='" + getName() + "'" + ", url='" + getUrl() + "'"
                + ", description='" + getDescription() + "'" + "}";
    }
}
