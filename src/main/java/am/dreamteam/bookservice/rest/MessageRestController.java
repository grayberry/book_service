package am.dreamteam.bookservice.rest;

import am.dreamteam.bookservice.service.MessageService;
import am.dreamteam.bookservice.util.MessageFormatHelper;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/dialogs")
public class MessageRestController {

    private SimpMessagingTemplate simpMessagingTemplate;
    private MessageService messageService;
    private MessageFormatHelper msgHelper;

    public MessageRestController(SimpMessagingTemplate simpMessagingTemplate,
                             MessageService messageService,
                             MessageFormatHelper msgHelper) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
        this.msgHelper = msgHelper;
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

}
