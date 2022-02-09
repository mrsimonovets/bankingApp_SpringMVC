package springMvc.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springMvc.dao.DebitCardDao;
import springMvc.entity.DebitCard;

@Controller
public class DebitCardController {

    private DebitCardDao debitCardDao;

    @Autowired
    public DebitCardController(DebitCardDao debitCardDao) {
        this.debitCardDao = debitCardDao;
    }

    @GetMapping("/cards")
    public String cards(Model model){
        model.addAttribute("cards", debitCardDao.showCards(MainController.staticUser.getEmail()));
        return "card/card_list";
    }

    @GetMapping("/new-card")
    public String newCard(Model model){
        model.addAttribute("debitCard", new DebitCard());
        return "card/new_card";
    }

    @PostMapping("/cards")
    public String addCard(@ModelAttribute("debitCard") @Valid DebitCard debitCard,
                          BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "card/new_card";

        debitCard.setCvv(genCvv());
        debitCard.setCardNumber(genCardNumber());

        debitCard.setUser(MainController.staticUser);
        debitCardDao.addCard(debitCard);

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

    @GetMapping("/cards/{cardId}")
    public String showOneCard(@PathVariable("cardId") Long cardId, Model model){
        model.addAttribute("debitCard", debitCardDao.showOneCard(cardId));
        return "card/show_card";
    }

    @GetMapping("/cards/{cardId}/edit")
    public String editCard(Model model, @PathVariable("cardId") Long cardId){
        model.addAttribute("debitCard", debitCardDao.showOneCard(cardId));
        return "card/edit_card";
    }

    @PatchMapping("/cards/{cardId}")
    public String updateCard(@ModelAttribute("debitCard") DebitCard debitCard){
        debitCardDao.updateCard(debitCard);
        return "redirect:/cards";
    }

    @DeleteMapping("/cards/{cardId}")
    public String deleteCard(@PathVariable("cardId") Long cardId){
        debitCardDao.deleteCard(cardId);
        return "redirect:/cards";
    }

}
