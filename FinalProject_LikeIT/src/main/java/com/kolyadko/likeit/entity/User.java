package com.kolyadko.likeit.entity;

import com.kolyadko.likeit.type.GenderType;
import com.kolyadko.likeit.type.RoleType;
import com.kolyadko.likeit.type.StateType;

import java.sql.Date;


/**
 * Created by DaryaKolyadko on 15.07.2016.
 */

/**
 * User entity
 */
public class User extends Entity<String> {
    private String firstName;
    private String lastName;
    private GenderType gender;
    private String password;
    private Date birthDate;
    private Date signUpDate;
    private String email;
    private RoleType role = RoleType.USER;
    private StateType state = StateType.ACTIVE;
    private Float rating;
    private Boolean archive;

    public User(String login) {
        setId(login);
    }

    public Boolean isAdmin() {
        return role == RoleType.ADMIN;
    }

    public Boolean isActive() {
        return state == StateType.ACTIVE;
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

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(Date signUpDate) {
        this.signUpDate = signUpDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public StateType getState() {
        return state;
    }

    public void setState(StateType state) {
        this.state = state;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (gender != user.gender) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (birthDate != null ? !birthDate.equals(user.birthDate) : user.birthDate != null) return false;
        if (signUpDate != null ? !signUpDate.equals(user.signUpDate) : user.signUpDate != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (role != user.role) return false;
        if (state != user.state) return false;
        if (rating != null ? !rating.equals(user.rating) : user.rating != null) return false;
        return archive != null ? archive.equals(user.archive) : user.archive == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (signUpDate != null ? signUpDate.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (archive != null ? archive.hashCode() : 0);
        return result;
    }
}