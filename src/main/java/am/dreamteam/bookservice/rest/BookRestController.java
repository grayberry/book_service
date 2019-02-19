package am.dreamteam.bookservice.rest;

import am.dreamteam.bookservice.service.UserBooksService;
import am.dreamteam.bookservice.util.GoogleBooksHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/books")
public class BookRestController {

    private GoogleBooksHelper googleBooksHelper;
    private UserBooksService userBooksService;

    public BookRestController(UserBooksService userBooksService, GoogleBooksHelper googleBooksHelper) {
        this.userBooksService = userBooksService;
        this.googleBooksHelper = googleBooksHelper;
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> search(@RequestParam String term) {
        System.out.println(term);
        return new ResponseEntity<>(
                Stream.concat(
                        userBooksService.searchByTitle(term).stream().map(x->
                                String.format("{ \"id\" : \"%s\", " +
                                                "\"book\" : %s}",
                                        x.getId().toString(),
                                        x.getBook().toString())
                        ),
                        userBooksService.searchByAuthor(term).stream().map(x->
                                String.format("{ \"id\" : \"%s\", " +
                                                "\"book\" : %s }",
                                        x.getId().toString(),
                                        x.getBook().toString())

                        )
                ).distinct().collect(Collectors.toList()),
                HttpStatus.OK);
    }


    @PostMapping(value = "/add")
    public ResponseEntity<?> addBooksPost(@AuthenticationPrincipal Principal principal, @RequestBody String addBooks){
        if(principal==null){
            return ResponseEntity.status(301).build();
        }
        userBooksService.addUserBooks(principal.getName(),
                googleBooksHelper.parseJsonToBook(addBooks));

        return ResponseEntity.ok().build();
    }
}
