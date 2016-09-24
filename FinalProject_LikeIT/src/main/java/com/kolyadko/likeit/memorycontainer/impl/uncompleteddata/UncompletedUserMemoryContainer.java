package com.kolyadko.likeit.memorycontainer.impl.uncompleteddata;

import com.kolyadko.likeit.memorycontainer.MemoryContainer;
import com.kolyadko.likeit.type.MemoryContainerType;

/**
 * Created by DaryaKolyadko on 04.08.2016.
 */

/**
 * Uncompleted user info (is used on user creation and editing)
 */
public class UncompletedUserMemoryContainer extends MemoryContainer {
    private String login;
    private String firstName;
    private String lastName;
    private String password;
    private String birthDate;
    private String email;
    private String gender;

    public UncompletedUserMemoryContainer() {
        containerType = MemoryContainerType.ONE_OFF;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}