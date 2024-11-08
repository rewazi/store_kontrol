package org.example.model;

import java.util.UUID;

public class Customer {
    private UUID id;
    private String name;
    private String surname;
    private String phone;

    public Customer() {
        this.id = UUID.randomUUID();
    }

    public Customer(String name, String surname, String phone) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public UUID getId() {
        return id;
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

    @Override
    public String toString() {
        return "Имя: " + name + ", Фамилия: " + surname + ", Телефон: " + phone;
    }
}
