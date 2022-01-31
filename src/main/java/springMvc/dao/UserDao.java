package springMvc.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import springMvc.entity.User;

import java.util.List;

@Component
public class UserDao {

//    private final JdbcTemplate jdbcTemplate;

//    @Autowired
//    public UserDao(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

//    public List<User> showUsers(){
//        return jdbcTemplate.query("SELECT * FROM uniqueUser", new BeanPropertyRowMapper<>(User.class));
//    }
//
//    public User showOneUser(String email){
//        return jdbcTemplate.query("SELECT * FROM uniqueUser WHERE email=?",
//                new Object[]{email}, new BeanPropertyRowMapper<>(User.class))
//                .stream().findAny().orElse(null);
//    }
//
//    public void addUser(User user){
//        jdbcTemplate.update("INSERT INTO uniqueUser VALUES(?, ?, ?, ?, ?)", user.getFirstName(),
//                user.getLastName(), user.getEmail(), user.getPassword(), user.getRegistrationDate());
//    }
//
//    public void updateUser(String email, User updatedUser){
//        jdbcTemplate.update("UPDATE uniqueUser SET firstName=?, lastName=?, password=? WHERE email=?",
//                updatedUser.getFirstName(), updatedUser.getLastName(), updatedUser.getPassword(), email);
//    }
//
//    public void deleteUser(String email){
//        jdbcTemplate.update("DELETE FROM uniqueUser WHERE email=?", email);
//    }
    private SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public User findByEmail(String email) {
        return sessionFactory.getCurrentSession().get(User.class, email);
    }

    public List<User> findAll() {
        List<User> users = (List<User>) sessionFactory.getCurrentSession().createQuery("From User").list();
        return users;
    }

    public void update(String email) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        User user = sessionFactory.openSession().get(User.class, email);
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(String email) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx1 = session.beginTransaction();
        User user = sessionFactory.openSession().get(User.class, email);
        session.delete(user);
        tx1.commit();
        session.close();
    }
}
