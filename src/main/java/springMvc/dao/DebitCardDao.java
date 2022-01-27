package springMvc.dao;

import springMvc.entity.DebitCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DebitCardDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DebitCardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DebitCard> showCards(){
        return jdbcTemplate.query("SELECT * FROM DebitCard", new BeanPropertyRowMapper<>(DebitCard.class));
    }

    public DebitCard showOneCard(String cardNumber){
        return jdbcTemplate.query("SELECT * FROM DebitCard WHERE cardNumber=?",
                        new Object[]{cardNumber}, new BeanPropertyRowMapper<>(DebitCard.class))
                .stream().findAny().orElse(null);
    }

    public void addCard(DebitCard debitCard){
        jdbcTemplate.update("INSERT INTO DebitCard VALUES(?, ?, ?, ?)", debitCard.getBalance(), debitCard.getCardNumber(),
                debitCard.getExpirationDate(), debitCard.getCvv());
    }

    public void updateCard(String cardNumber, DebitCard updatedCard){
        jdbcTemplate.update("UPDATE DebitCard SET balance=?, expirationDate=? WHERE cardNumber=?",
                updatedCard.getBalance(), updatedCard.getExpirationDate(), cardNumber);
    }

    public void deleteCard(String cardNumber){
        jdbcTemplate.update("DELETE FROM DebitCard WHERE cardNumber=?", cardNumber);
    }
}
