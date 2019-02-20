package am.dreamteam.bookservice.rest;

import am.dreamteam.bookservice.entities.messages.Dialog;
import am.dreamteam.bookservice.entities.messages.Message;
import am.dreamteam.bookservice.service.DialogService;
import am.dreamteam.bookservice.service.MessageService;
import am.dreamteam.bookservice.util.MessageFormatHelper;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dialogs")
public class MessageRestController {

    private SimpMessagingTemplate simpMessagingTemplate;
    private MessageService messageService;
    private MessageFormatHelper msgHelper;
    private DialogService dialogService;

    public MessageRestController(SimpMessagingTemplate simpMessagingTemplate,
                                 MessageService messageService,
                                 MessageFormatHelper msgHelper,
                                 DialogService dialogService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
        this.msgHelper = msgHelper;
        this.dialogService = dialogService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@AuthenticationPrincipal Principal principal, @RequestBody String request){
        if(principal==null){
            return ResponseEntity.status(301).build();
        }

        JSONObject json = new JSONObject(request);
        String userTo = json.optString("user");
        String content = json.optString("content");
        System.out.println(request);

        messageService.saveMessage(principal.getName(), userTo,
                content);

        simpMessagingTemplate.convertAndSendToUser(
                userTo,
                "/message",
                msgHelper.messageToJson(principal.getName(), content));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/c")
    public ResponseEntity<List<String>> c(@AuthenticationPrincipal Principal principal){
        if(principal==null){
            return ResponseEntity.ok().build();
        }
        List<String> users = new ArrayList<>();

        List<Dialog> dialogs = dialogService.findAllByUserTo(principal.getName());

        for(Dialog dialog : dialogs){
            List<Message> messages = messageService.findAllByDialogAndIsRead(dialog, false);
            for(Message message : messages){
                users.add(message.getDialog().getUserFrom().getUsername());
                break;
            }
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
