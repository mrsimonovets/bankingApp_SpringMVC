package springMvc.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.Objects;

public class Loan {


    private Date registrationDate; //автоматически

    @Min(value = 5, message = "Sum should not be empty")
    private double sum; //вводит пользователь

    @NotEmpty(message = "Interest should not be empty")
    private double interestRate; // вводит пользователь

    @NotEmpty(message = "Term should not be empty")
    private int creditTerm; //вводит пользователь

    private double monthlyPayment; // автоматически

    @NotEmpty(message = "Description should not be empty")
    private String description; //вводит пользователь


    public Loan(){}

    public Loan(Date registrationDate, double sum, double interestRate, int creditTerm, double monthlyPayment, String description) {
        this.registrationDate = registrationDate;
        this.sum = sum;
        this.interestRate = interestRate;
        this.creditTerm = creditTerm;
        this.monthlyPayment = monthlyPayment;
        this.description = description;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getCreditTerm() {
        return creditTerm;
    }

    public void setCreditTerm(int creditTerm) {
        this.creditTerm = creditTerm;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loan)) return false;
        Loan loan = (Loan) o;
        return Double.compare(loan.getSum(), getSum()) == 0 && Double.compare(loan.getInterestRate(), getInterestRate()) == 0 && getCreditTerm() == loan.getCreditTerm() && Double.compare(loan.getMonthlyPayment(), getMonthlyPayment()) == 0 && getRegistrationDate().equals(loan.getRegistrationDate()) && Objects.equals(getDescription(), loan.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRegistrationDate(), getSum(), getInterestRate(), getCreditTerm(), getMonthlyPayment(), getDescription());
    }
}
