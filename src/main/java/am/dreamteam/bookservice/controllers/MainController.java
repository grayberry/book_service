package am.dreamteam.bookservice.controllers;

import am.dreamteam.bookservice.dto.RegisterRequest;
import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UserBooks;
import am.dreamteam.bookservice.service.UserBooksService;
import am.dreamteam.bookservice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MainController {

    private UserService userService;
    private UserBooksService userBooksService;

    public MainController(UserService userService, UserBooksService userBooksService) {
        this.userService = userService;
        this.userBooksService = userBooksService;
    }

    @GetMapping
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities());
        Pageable pageable = PageRequest.of(0, 6, Sort.by("id").descending());
        Page<UserBooks> books = userBooksService.findAll(pageable);
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:";
    }
    @GetMapping("/register")
    public String signUpPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken) {
            return "register";
        }
        return "redirect:";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute RegisterRequest request, Model model){
        int i = userService.registerUser(request);
        if(i==-1){
            model.addAttribute("message", "username already exists");
            return "register";
        }

        if(i==0){
            model.addAttribute("message", "email already exists");
            return "register";
        }

        model.addAttribute("message", "check your email " + request.getEmail() +" for activate your account");

        return "login";
    }

    @GetMapping("/verification")
    public String verification(@RequestParam(name = "v") String verify, Model model){

        User user = userService.findUserByVerificationCode(verify);

        user.setActivationCode(null);
        user.setActive(true);
        userService.save(user);
        model.addAttribute("message", user.getUsername() + ", congrats!\n your account is activated");
        return "login";
    }

    @PostMapping("/resend")
    public String resend(@RequestParam String email, Model model){
        int i = userService.resend(email);
        if(i==-1){
            model.addAttribute("message", "Email - \"" + email + "\" is not correct");
        }
        if(i==0){
            model.addAttribute("message", "Admin blocked Your account");
        }
        if(i==1){
            model.addAttribute("message", "We sent You a verification key again. \nCheck Your email: " + email);
        }
        return "login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/about")
    public String about() {

        return "about";
    }

    @GetMapping("/contact")
    public String contact() {

        return "contact";
    }

}

