package models;

import java.util.ArrayList;

public class Book {
    private int id;
    private String name;
    private int yearOfPublishing;
    private String locationOfPublishing;
    private int pages;
    private ArrayList<Author> authors;
    private Reader owner;

    public Book(String name, int yearOfPublishing, String locationOfPublishing, int pages, ArrayList<Author> authors, Reader owner) {
        this.name = name;
        this.yearOfPublishing = yearOfPublishing;
        this.locationOfPublishing = locationOfPublishing;
        this.pages = pages;
        this.authors = authors;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public Reader getOwner() {
        return owner;
    }

    public void setOwner(Reader owner) {
        this.owner = owner;
    }
}
