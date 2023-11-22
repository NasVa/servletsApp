package dto;

import java.util.Objects;

public class ReaderDto {
    private Integer id;
    private String name;
    private String mail;
    private int phone;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReaderDto readerDto = (ReaderDto) o;
        return phone == readerDto.phone && Objects.equals(id, readerDto.id) && Objects.equals(name, readerDto.name) && Objects.equals(mail, readerDto.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mail, phone);
    }
}
