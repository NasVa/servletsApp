package dto;

import java.util.List;
import java.util.Objects;

public class AuthorDto {
    private Integer id;
    private String name;
    private List<Integer> booksId;

    public List<Integer> getBooksId() {
        return booksId;
    }

    public void setBooksId(List<Integer> booksId) {
        this.booksId = booksId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AuthorDto authorDto = (AuthorDto) object;
        return Objects.equals(id, authorDto.id) && Objects.equals(name, authorDto.name) && Objects.equals(booksId, authorDto.booksId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, booksId);
    }

    @Override
    public String toString() {
        return "AuthorDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", booksId=" + booksId +
                '}';
    }
}
