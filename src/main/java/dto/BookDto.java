package dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookDto {
    private Integer id;
    private String name;
    private int yearOfPublishing;
    private String locationOfPublishing;
    private int pages;
    private List<Integer> authorsId = new ArrayList<>();
    private int ownerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    public String getLocationOfPublishing() {
        return locationOfPublishing;
    }

    public void setLocationOfPublishing(String locationOfPublishing) {
        this.locationOfPublishing = locationOfPublishing;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<Integer> getAuthorsId() {
        return authorsId;
    }

    public void setAuthorsId(List<Integer> authorsId) {
        this.authorsId = authorsId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BookDto bookDto = (BookDto) object;
        return yearOfPublishing == bookDto.yearOfPublishing && pages == bookDto.pages && ownerId == bookDto.ownerId && Objects.equals(id, bookDto.id) && Objects.equals(name, bookDto.name) && Objects.equals(locationOfPublishing, bookDto.locationOfPublishing) && Objects.equals(authorsId, bookDto.authorsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, yearOfPublishing, locationOfPublishing, pages, authorsId, ownerId);
    }
}
