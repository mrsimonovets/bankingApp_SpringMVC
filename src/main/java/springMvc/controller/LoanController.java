package springMvc.controller;

import jakarta.validation.Valid;
import org.jboss.jandex.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springMvc.dao.LoanDao;
import springMvc.dao.UserDao;
import springMvc.entity.Loan;

import java.util.Date;

@Controller
public class LoanController {

    private LoanDao loanDao;

    public LoanController(LoanDao loanDao) {
        this.loanDao = loanDao;
    }

    //Loan
    @GetMapping("/loans")
    public String loans(Model model){
        model.addAttribute("loans", loanDao.showLoansByEmail(MainController.staticUser.getEmail()));
        return "loan/loan_list";
    }

    @GetMapping("/loans/new")
    public String newLoan(Model model){
        model.addAttribute("loan", new Loan());
        return "loan/new_loan";
    }

    @PostMapping("/loans")
    public String addLoan(@ModelAttribute("loan") @Valid Loan loan,
                          BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "loan/new_loan";

        loan.setMonthlyPayment(genMonthlyPayment(loan.getSum(), loan.getInterestRate(), loan.getCreditTerm()));
        loan.setRegistrationDate(genDate());

        loan.setUser(MainController.staticUser);
        loanDao.addLoan(loan);

        return "redirect:/loans";
    }

    public static double genMonthlyPayment(double sum, double interest, int credit){
        double total = sum * (1 + interest/100);
        double monthlyPayment = total / credit;
        return monthlyPayment;
    }

    public static Date genDate(){
        Date registrationDate = new Date();
        return registrationDate;
    }

    @GetMapping("/loans/{loanId}")
    public String showOneLoan(@PathVariable("loanId") Long id, Model model){
        model.addAttribute("loan", loanDao.showOneLoan(id));
        return "loan/show_loan";
    }

    @GetMapping("/loans/{loanId}/edit")
    public String editLoan(Model model, @PathVariable("loanId") Long loanId){
        model.addAttribute("loan", loanDao.showOneLoan(loanId));
        return "loan/edit_loan";
    }

    @PatchMapping("/loans/{loanId}")
    public String updateLoan(@ModelAttribute("loan") @Valid Loan loan,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "loan/edit_loan";

        loanDao.updateLoan(loan);
        return "redirect:/loans";
    }

    @DeleteMapping("/loans/{loanId}")
    public String deleteLoan(@PathVariable("loanId") Long id){
        loanDao.deleteLoan(id);
        return"redirect:/loans";
    }
}
