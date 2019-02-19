package am.dreamteam.bookservice.controllers;

import am.dreamteam.bookservice.entities.messages.Dialog;
import am.dreamteam.bookservice.entities.messages.Message;
import am.dreamteam.bookservice.service.DialogService;
import am.dreamteam.bookservice.service.MessageService;
import am.dreamteam.bookservice.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/dialogs")
public class MessageController {

    private UserService userService;
    private DialogService dialogService;
    private MessageService messageService;
    public MessageController(UserService userService,
                             MessageService messageService, DialogService dialogService) {
        this.userService = userService;
        this.dialogService = dialogService;
        this.messageService = messageService;
    }

    @GetMapping
    public String dialogs(@AuthenticationPrincipal Principal principal,
                          @RequestParam(name = "u", required = false) String userTo,
                          Model model){
        if(principal==null){
            return "redirect:/login";
        }
        if(userTo!=null && userService.findByUsername(userTo)!=null){
            Dialog myDialog = dialogService.findByUserFromAndUserTo(principal.getName(), userTo);
            Dialog hisDialog;
            model.addAttribute("locate", "/dialogs");
            model.addAttribute("username", userTo);
            model.addAttribute("print", (hisDialog = dialogService.findByUserFromAndUserTo(userTo, principal.getName()))!=null);
            List<Message> h1 = messageService.findAllMessageByDialog(hisDialog);
            List<Message> m1 = messageService.findAllMessageByDialog(myDialog);
            m1.addAll(h1);
            Collections.sort(m1);
            model.addAttribute("messages", m1);
        }
        model.addAttribute("page", "Dialogs");
        model.addAttribute("dialogs",dialogService.findAllByUserTo(principal.getName()));
        return "dialogs";
    }
}
