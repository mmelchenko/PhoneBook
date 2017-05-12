package bk.springRS.request;

import java.io.Serializable;

public class ContactRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String surname;
    private String phone;

    public ContactRequest() {}

    public ContactRequest(String name, String surname, String phone) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAnyEmptyProperties() {
        return this.getName() == null ||
                this.getSurname() == null ||
                this.getPhone() == null;
    }

    @Override
    public String toString() {
        return "[name = " + getName() + ", surname = " + getSurname() +
                ", phone = " + getPhone() + "]";
    }
}
