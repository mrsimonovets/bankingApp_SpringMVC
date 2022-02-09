package springMvc.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import springMvc.entity.Loan;

import javax.persistence.Query;
import java.util.List;

@Component
public class LoanDao {

    private SessionFactory sessionFactory;

    public LoanDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<Loan> showLoansByEmail(String email) {

        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        Query query = session.createQuery("FROM Loan WHERE user.email =:paramEmail");
        query.setParameter("paramEmail", email);
        List<Loan> loans = query.getResultList();
        tx1.commit();
        session.close();
        return loans;
    }

    public Loan showOneLoan(Long id){
        return sessionFactory.openSession().get(Loan.class, id);
    }

    public void addLoan(Loan loan){
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(loan);
        tx1.commit();
        session.close();
    }

    public void updateLoan(Loan updatedLoan){
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery("update Loan set sum = :sumParam, " +
                "interestRate = :interestRateParam, " +
                "creditTerm = :creditTermParam, " +
                "description = :descriptionParam where loanId = :loanIdParam");


        query.setParameter("loanIdParam", updatedLoan.getLoanId());
        query.setParameter("sumParam", updatedLoan.getSum());
        query.setParameter("interestRateParam", updatedLoan.getInterestRate());
        query.setParameter("creditTermParam", updatedLoan.getCreditTerm());
        query.setParameter("descriptionParam", updatedLoan.getDescription());
        query.executeUpdate();

        tx1.commit();
        session.close();
    }

    public void deleteLoan(Long id){
        Session session = sessionFactory.openSession();
        Transaction tx1 = session.beginTransaction();
        Loan loan = session.get(Loan.class, id);
        session.delete(loan);
        tx1.commit();
        session.close();
    }

}
