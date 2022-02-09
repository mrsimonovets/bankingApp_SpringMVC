package springMvc.entity;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name ="debitcards")
public class DebitCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    @NotEmpty(message = "Balance should not be empty")
    private double balance; // вводит пользователь

    private String cardNumber; // генерировать

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotEmpty(message = "Date should not be empty")
    private Date expirationDate; // вводит пользователь

    private int cvv; // генерировать

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", nullable = false)
    private User user;

    public DebitCard(){}

    public DebitCard(double balance, String cardNumber, Date expirationDate, int cvv) {
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }


    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DebitCard)) return false;
        DebitCard debitCard = (DebitCard) o;
        return Double.compare(debitCard.getBalance(), getBalance()) == 0 && getCvv() == debitCard.getCvv() && Objects.equals(getCardNumber(), debitCard.getCardNumber()) && Objects.equals(getExpirationDate(), debitCard.getExpirationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBalance(), getCardNumber(), getExpirationDate(), getCvv());
    }
}
