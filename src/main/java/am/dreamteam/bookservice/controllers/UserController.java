package am.dreamteam.bookservice.controllers;

import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.entities.users.UserBooks;
import am.dreamteam.bookservice.service.UserBooksService;
import am.dreamteam.bookservice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private UserBooksService userBooksService;

    public UserController(UserService usersRepoService, UserBooksService usersBooksRepoService) {
        this.userService = usersRepoService;
        this.userBooksService = usersBooksRepoService;
    }

    @GetMapping("/mypage")
    public String main(@AuthenticationPrincipal Principal principal,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 8) Pageable page,
                       Model model){
        if(principal==null){
            System.out.println("redirect");
            return "redirect:/login";
        }
        User user = userService.findByUsername(principal.getName());
        Page<UserBooks> books = userBooksService.findAllUserBooksByUserId(user.getId(), page);
        int totalPages = books.getTotalPages();
        if(totalPages>1){
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("user", user);
        model.addAttribute("books", userBooksService.findAllUserBooksByUserId(user.getId(), page));
        model.addAttribute("main", true);
        return "userpage";
    }

    @GetMapping("/{u}")
    public String getUsers( Principal principal, @PathVariable("u") String username,
                            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 8) Pageable page, Model model){

        User user = userService.findByUsername(username);
        if(principal!=null && principal.getName().equals(user.getUsername())){
            return "redirect:/user/mypage";
        }
        Page<UserBooks> books = userBooksService.findAllUserBooksByUserId(user.getId(),page);
        int totalPages = books.getTotalPages();
        if(totalPages>1){
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("user", user);
        model.addAttribute("books", userBooksService.findAllUserBooksByUserId(user.getId(),page));

        return "userpage";
    }
}

