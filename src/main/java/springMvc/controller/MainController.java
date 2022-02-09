package springMvc.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springMvc.dao.UserDao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import springMvc.entity.User;

import java.util.ArrayList;
import java.util.Date;


@Controller
public class MainController {

    private UserDao userDao;

    public static User staticUser;

    @Autowired
    public MainController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/main")
    public String mainPage(){
        return "main";
    }

    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("users", userDao.findAll());
        return "user/user_list";
    }

    @GetMapping("/")
    public String getLoginPage(Model model){
        model.addAttribute("user", new User());
        return "user/login";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute("user") User user){
        User logUser = userDao.findByEmail(user.getEmail());
        if (logUser!=null){
            String logPassword = logUser.getPassword();
            if (logPassword.equals(user.getPassword())){
                staticUser = logUser;
                return "main";
            } else{
                return "user/login";
            }
        }
        return "user/registration";
    }

    public static Date genDate(){
        Date registrationDate = new Date();
        return registrationDate;
    }

    @GetMapping("/users/new")
    public String getRegisterPage(Model model){
        model.addAttribute("user", new User());
        return "user/registration";
    }

    @PostMapping("/users")
    public String doRegister(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "user/registration";

        user.setRegistrationDate(genDate());
        user.setUserCards(new ArrayList<>());
        user.setUserLoans(new ArrayList<>());
        userDao.save(user);

        return "redirect:/";
    }

    @GetMapping("/users/{email}")
    public String showOneUser(@PathVariable("email") String email, Model model){
        model.addAttribute("user", userDao.findByEmail(email));
        return "user/show_user";
    }

    @GetMapping("/users/{email}/edit")
    public String editUser(Model model, @PathVariable("email") String email){
        model.addAttribute("user", userDao.findByEmail(email));
        return "user/edit_user";
    }

    @PatchMapping("/users/{email}")
    public String editUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,
                             @PathVariable("email") String email){
        if (bindingResult.hasErrors())
            return "user/edit_user";

        userDao.edit(user);
        return "redirect:/users";
    }

    @DeleteMapping("/users/{email}")
    public String deleteUser(@PathVariable("email") String email){
        userDao.delete(email);
        return "redirect:/users";
    }
}
