package springMvc.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springMvc.dao.UserDao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import springMvc.entity.User;

import java.util.Date;


@Controller
public class MainController {

    private UserDao userDao;

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
        model.addAttribute("users", userDao.showUsers());
        return "user/user_list";
    }

    @GetMapping("/")
    public String getLoginPage(Model model){
        model.addAttribute("user", new User());
        return "user/login";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute("user") User user){
        if (userDao.showOneUser(user.getEmail())!=null){
            User logUser = userDao.showOneUser(user.getEmail());
            String logPassword = logUser.getPassword();
            if (logPassword.equals(user.getPassword())){
                return "main";
            } else{
                return "user/login";
            }
        }
        return "redirect:/register";
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
        userDao.addUser(user);

        return "redirect:/";
    }

    @GetMapping("/users/{email}")
    public String showOneUser(@PathVariable("email") String email, Model model){
        model.addAttribute("user", userDao.showOneUser(email));
        return "user/show_user";
    }

    @GetMapping("/users/{email}/edit")
    public String editUser(Model model, @PathVariable("email") String email){
        model.addAttribute("user", userDao.showOneUser(email));
        return "user/edit_user";
    }

    @PatchMapping("/users/{email}")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,
                             @PathVariable("email") String email){
        if (bindingResult.hasErrors())
            return "user/edit_user";

        userDao.updateUser(email, user);
        return "redirect:/users";
    }

    @DeleteMapping("/users/{email}")
    public String deleteUser(@PathVariable("email") String email){
        userDao.deleteUser(email);
        return"redirect:/users";
    }
}
