package springMvc.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springMvc.dao.LoanDao;
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
        model.addAttribute("loans", loanDao.showLoans());
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
        loanDao.addLoan(loan);
        return "redirect:/loans";
    }

    public static double genMonthlyPayment(double sum, double interest, int credit){
        double total = sum * (1 + interest);
        double monthlyPayment = total / credit;
        return monthlyPayment;
    }

    public static Date genDate(){
        Date registrationDate = new Date();
        return registrationDate;
    }

    @GetMapping("/loans/{sum}")
    public String showOneLoan(@PathVariable("sum") Double sum, Model model){
        model.addAttribute("loan", loanDao.showOneLoan(sum));
        return "loan/show_loan";
    }

    @GetMapping("/loans/{sum}/edit")
    public String editLoan(Model model, @PathVariable("sum") Double sum){
        model.addAttribute("user", loanDao.showOneLoan(sum));
        return "loan/edit_loan";
    }

    @PatchMapping("/loans/{sum}")
    public String updateLoan(@ModelAttribute("loan") @Valid Loan loan,
                             BindingResult bindingResult,
                             @PathVariable("sum") Double sum){
        if (bindingResult.hasErrors())
            return "loan/edit_loan";

        loanDao.updateLoan(sum, loan);
        return "redirect:/loans";
    }

    @DeleteMapping("/loans/{sum}")
    public String deleteLoan(@PathVariable("sum") Double sum){
        loanDao.deleteLoan(sum);
        return"redirect:/loans";
    }
}
