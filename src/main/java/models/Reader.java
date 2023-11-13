package models;

import java.util.Objects;

public class Reader {
    private Integer id;
    private String name;
    private String mail;
    private int phone;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return phone == reader.phone
                && Objects.equals(id, reader.id)
                && Objects.equals(name, reader.name)
                && Objects.equals(mail, reader.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mail, phone);
    }
}
