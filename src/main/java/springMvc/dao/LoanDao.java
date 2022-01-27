package springMvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springMvc.entity.Loan;

import java.util.List;

@Component
public class LoanDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LoanDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Loan> showLoans(){
        return jdbcTemplate.query("SELECT * FROM Loan", new BeanPropertyRowMapper<>(Loan.class));
    }

    public Loan showOneLoan(Double sum){
        return jdbcTemplate.query("SELECT * FROM Loan WHERE sum =?",
                        new Object[]{sum}, new BeanPropertyRowMapper<>(Loan.class))
                .stream().findAny().orElse(null);
    }

    public void addLoan(Loan loan){
        jdbcTemplate.update("INSERT INTO DebitCard VALUES(?, ?, ?, ?, ?, ?)", loan.getRegistrationDate(), loan.getSum(),
                loan.getInterestRate(), loan.getCreditTerm(), loan.getMonthlyPayment(), loan.getDescription());
    }

    public void updateLoan(Double sum, Loan updatedLoan){
        jdbcTemplate.update("UPDATE Loan SET interestRate=?, creditTerm=?, description=?",
                updatedLoan.getInterestRate(), updatedLoan.getCreditTerm(), updatedLoan.getDescription(), sum);
    }

    public void deleteLoan(Double sum){
        jdbcTemplate.update("DELETE FROM Loan WHERE sum=?", sum);
    }
}
