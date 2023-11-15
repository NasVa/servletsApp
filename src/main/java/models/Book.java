package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book {
    private Integer id;
    private String name;
    private int yearOfPublishing;
    private String locationOfPublishing;
    private int pages;
    private List<Author> authors = new ArrayList<>();
    private Reader owner;

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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Reader getOwner() {
        return owner;
    }

    public void setOwner(Reader owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return yearOfPublishing == book.yearOfPublishing && pages == book.pages && Objects.equals(id, book.id) && Objects.equals(name, book.name) && Objects.equals(locationOfPublishing, book.locationOfPublishing) && Objects.equals(authors, book.authors) && Objects.equals(owner, book.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, yearOfPublishing, locationOfPublishing, pages, authors, owner);
    }
}
