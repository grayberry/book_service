package am.dreamteam.bookservice.rest;

import am.dreamteam.bookservice.entities.users.Transfer;
import am.dreamteam.bookservice.entities.users.UserBooks;
import am.dreamteam.bookservice.service.TransferService;
import am.dreamteam.bookservice.service.UserBooksService;
import am.dreamteam.bookservice.util.MessageFormatHelper;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferRestController {

    private MessageFormatHelper msgHelper;
    private UserBooksService userBooksService;
    private TransferService transferService;
    private SimpMessagingTemplate simpMessagingTemplate;

    public TransferRestController(MessageFormatHelper msgHelper,
                              UserBooksService userBooksService,
                              TransferService transferService,
                              SimpMessagingTemplate simpMessagingTemplate) {
        this.msgHelper = msgHelper;
        this.userBooksService = userBooksService;
        this.transferService = transferService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    @PostMapping("/send")
    public ResponseEntity<?> send(@AuthenticationPrincipal Principal principal, @RequestBody String request){
        if(principal==null){
            return ResponseEntity.status(301).build();
        }

        if(userBooksService.findAllUserBooksByUsername(principal.getName()).isEmpty()){
            return ResponseEntity.notFound().build();
        }

        System.out.println(request);
        JSONObject json = new JSONObject(request);
        String userTo = json.optString("userTo");
        Integer bookId = json.getInt("bookId");
        if(transferService.findTransfer(principal.getName(), userTo, bookId )){
            return ResponseEntity.badRequest().build();
        }
        transferService.createOneTransfer(principal.getName(), userTo, bookId);

        simpMessagingTemplate.convertAndSendToUser(
                userTo,
                "/transfer",
                msgHelper.transferResponse(principal.getName(), bookId));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@AuthenticationPrincipal Principal principal, @RequestBody String request){
        System.out.println(request);
        if(principal==null){
            return ResponseEntity.status(301).build();
        }
        JSONObject json = new JSONObject(request);
        transferService.cancelTransfer(json.optString("user"), principal.getName(), json.getInt("bookId"));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkbook")
    public ResponseEntity<?> checkBook(@AuthenticationPrincipal Principal principal, @RequestParam(name = "u") String username, @RequestParam String bookId){
        if(principal==null){
            return ResponseEntity.status(301).build();
        }

        System.out.println(username);
        UserBooks book = userBooksService.findUserBookById(Integer.valueOf(bookId));
        System.out.println(book);

        if(!book.getUser().getUsername().equals(principal.getName())){
            
            transferService.cancelTransfer(username, principal.getName(), Integer.valueOf(bookId));
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getbooks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeGet(@AuthenticationPrincipal Principal principal, @RequestParam String username){
        if(principal==null){
            return ResponseEntity.status(301).build();
        }
        System.out.println(username);
        return new ResponseEntity<>(userBooksService.findAllUserBooksByUsername(username).toString(), HttpStatus.OK);
    }

    @PostMapping("/change")
    public ResponseEntity<?> changePost(@AuthenticationPrincipal Principal principal, @RequestBody String request){
        if(principal==null){
            return ResponseEntity.status(301).build();
        }
        System.out.println(request);
        JSONObject json = new JSONObject(request);

        transferService.transferBooks(principal.getName(), json.optString("user"), json.getInt("mybook"), json.getInt("userBook"));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/c")
    public ResponseEntity<List<String>> c(@AuthenticationPrincipal Principal principal){
        if(principal==null){
            return ResponseEntity.ok().build();
        }

        List<Transfer> transfers = transferService.findAllByUserToAndDone(principal.getName(), false);
        List<String> users = new ArrayList<>();

        for(Transfer transfer : transfers){
            users.add(transfer.getUserFrom().getUsername());
        }
        return new ResponseEntity<>(users, HttpStatus.OK);

    }
}
