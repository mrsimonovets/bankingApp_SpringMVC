package entity;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

public class DebitCard {

    @NotEmpty(message = "Balance should not be empty")
    private double balance; // вводит пользователь

    private String cardNumber; // генерировать

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotEmpty(message = "Date should not be empty")
    private Date expirationDate; // вводит пользователь

    private int cvv; // генерировать


    public DebitCard(){}

    public DebitCard(double balance, String cardNumber, Date expirationDate, int cvv) {
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
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
