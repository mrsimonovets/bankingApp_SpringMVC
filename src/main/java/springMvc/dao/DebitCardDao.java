package springMvc.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import springMvc.entity.DebitCard;

import javax.persistence.Query;
import java.util.List;

@Component
public class DebitCardDao {

    private SessionFactory sessionFactory;

    public DebitCardDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<DebitCard> showCards(String email){
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery("FROM DebitCard WHERE user.email =:paramEmail");
        query.setParameter("paramEmail", email);
        List<DebitCard> cards = query.getResultList();
        tx1.commit();
        session.close();
        return cards;
    }

    public DebitCard showOneCard(Long cardId){
        return sessionFactory.openSession().get(DebitCard.class, cardId);
    }

    public void addCard(DebitCard debitCard){
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(debitCard);
        tx1.commit();
        session.close();
    }

    public void updateCard(DebitCard debitCard){
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery("update DebitCard set balance = :balanceParam, " +
                "expirationDate = :expirationDateParam where cardId = :cardIdParam");
        query.setParameter("cardIdParam", debitCard.getCardId());
        query.setParameter("balanceParam", debitCard.getBalance());
        query.setParameter("expirationDateParam", debitCard.getExpirationDate());
        query.executeUpdate();

        tx1.commit();
        session.close();
    }

    public void deleteCard(Long cardId){
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        DebitCard debitCard = session.get(DebitCard.class, cardId);
        session.delete(debitCard);
        tx1.commit();
        session.close();
    }
}
