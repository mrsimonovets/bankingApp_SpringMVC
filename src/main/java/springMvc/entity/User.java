package springMvc.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "users")
public class User {

//    @NotEmpty(message = "Name should not be empty")
//    @Size(min = 2, max = 15, message = "Check your name's size")
    private String firstName;

//    @NotEmpty(message = "Last name should not be empty")
    private String lastName;

    @Id
//    @NotEmpty(message = "email is empty!")
//    @Email(message = "email should be valid")
    private String email;

    private String password;

    private Date registrationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> userLoans;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DebitCard> userCards;

    public User() {}

    public User(String firstName, String lastName, String email, String password, Date registrationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
    }

    public List<Loan> getUserLoans() {
        return userLoans;
    }

    public void setUserLoans(List<Loan> userLoans) {
        this.userLoans = userLoans;
    }

    public List<DebitCard> getUserCards() {
        return userCards;
    }

    public void setUserCards(List<DebitCard> userCards) {
        this.userCards = userCards;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getRegistrationDate(), user.getRegistrationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getEmail(), getPassword(), getRegistrationDate());
    }
}
