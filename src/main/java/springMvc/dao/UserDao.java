package springMvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springMvc.entity.User;

import java.util.List;

@Component
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> showUsers(){
        return jdbcTemplate.query("SELECT * FROM uniqueUser", new BeanPropertyRowMapper<>(User.class));
    }

    public User showOneUser(String email){
        return jdbcTemplate.query("SELECT * FROM uniqueUser WHERE email=?",
                new Object[]{email}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    public void addUser(User user){
        jdbcTemplate.update("INSERT INTO uniqueUser VALUES(?, ?, ?, ?, ?)", user.getFirstName(),
                user.getLastName(), user.getEmail(), user.getPassword(), user.getRegistrationDate());
    }

    public void updateUser(String email, User updatedUser){
        jdbcTemplate.update("UPDATE uniqueUser SET firstName=?, lastName=?, password=? WHERE email=?",
                updatedUser.getFirstName(), updatedUser.getLastName(), updatedUser.getPassword(), email);
    }

    public void deleteUser(String email){
        jdbcTemplate.update("DELETE FROM uniqueUser WHERE email=?", email);
    }

}
