package am.dreamteam.bookservice.controllers;

import am.dreamteam.bookservice.entities.users.Transfer;
import am.dreamteam.bookservice.service.DialogService;
import am.dreamteam.bookservice.service.TransferService;
import am.dreamteam.bookservice.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/transfers")
public class TransferController {

    private UserService userService;
    private DialogService dialogService;
    private TransferService transferService;

    public TransferController(UserService userService,
                              DialogService dialogService,
                              TransferService transferService) {
        this.userService = userService;
        this.dialogService = dialogService;
        this.transferService = transferService;
    }

    @GetMapping
    public String get(@AuthenticationPrincipal Principal principal,
                      @RequestParam(name = "u", required = false) String getUser, Model model){
        if(principal==null){
            return "redirect:/login";
        }
        if(getUser!=null && userService.findByUsername(getUser)!=null){
            model.addAttribute("locate", "/transfers");
            model.addAttribute("username", getUser);
            model.addAttribute("print", false);
            List<Transfer> transfers = transferService.findAllByUserFromAndUserToAndDone(getUser, principal.getName(), false);
            model.addAttribute("transfers", transfers);
        }
        model.addAttribute("page", "Transfers");
        model.addAttribute("dialogs",dialogService.findAllByUserTo(principal.getName()));
        return "dialogs";
    }

}
