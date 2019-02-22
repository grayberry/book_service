package am.dreamteam.bookservice.controllers;

import am.dreamteam.bookservice.entities.users.UserBooks;
import am.dreamteam.bookservice.service.UserBooksService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/books")
public class BookController {

    private UserBooksService userBooksService;

    public BookController(UserBooksService userBooksService) {
        this.userBooksService = userBooksService;
    }

    @GetMapping
    public String getAllBooks(@AuthenticationPrincipal Principal principal,
                              @RequestParam(required = false) Integer id,
                              @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 12) Pageable page,
                              Model model){
        if(principal!=null){
            model.addAttribute("user", principal.getName());
        }
        if(id!=null){
            model.addAttribute("book", userBooksService.findUserBookById(id));
            model.addAttribute("randomBooks", userBooksService.getRand());
            return "single-book";
        }
        Page<UserBooks> books = userBooksService.findAllUsersBooks(page);
        int totalPages = books.getTotalPages();
        if(totalPages>1){
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("books", books);
        return "books";
    }
}
