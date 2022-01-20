package controller;

import entity.DebitCard;
import entity.Loan;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    private List<Loan> loans = new ArrayList<>();
    private List<DebitCard> cards = new ArrayList<>();


    @GetMapping("/")
    public String mainPage(){
        return "main";
    }

    //DebitCard
    @GetMapping("/cards")
    public String cards(Model model){
        model.addAttribute("cards", cards);
        return "card_list";
    }

    @GetMapping("/new-card")
    public String newCard(Model model){
        model.addAttribute("debitCard", new DebitCard());
        return "new_card";
    }

    @PostMapping("/cards")
    public String addCard(@ModelAttribute("debitCard") @Valid DebitCard debitCard,
                          BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "new_card";

        debitCard.setCvv(genCvv());
        debitCard.setCardNumber(genCardNumber());
        cards.add(debitCard);

        return "redirect:/cards";
    }

    public static int genCvv(){
        int cvv = (int) (Math.random() * 900) + 100;
        return cvv;
    }

    public static String genCardNumber(){
        long max = 4000000000000000L;
        Long cardNumberLong = (long) (Math.random() * 1000000000000000L) + ++max;

        String cardNumberStr = Long.toString(cardNumberLong);
        return cardNumberStr;
    }

    //Loan
    @GetMapping("/loans")
    public String loans(Model model){
        model.addAttribute("loans", loans);
        return "loan_list";
    }

    @GetMapping("/new-loan")
    public String newLoan(Model model){
        model.addAttribute("loan", new Loan());
        return "new_loan";
    }

    @PostMapping("/loans")
    public String addLoan(@ModelAttribute("loan") @Valid Loan loan,
                          BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "new_loan";

        loan.setMonthlyPayment(genMonthlyPayment(loan.getSum(), loan.getInterestRate(), loan.getCreditTerm()));
        loan.setRegistrationDate(genDate());
        loans.add(loan);
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
}
