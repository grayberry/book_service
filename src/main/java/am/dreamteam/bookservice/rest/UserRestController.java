package am.dreamteam.bookservice.rest;

import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.service.UserService;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/user/mypage")
public class UserRestController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserRestController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@AuthenticationPrincipal Principal principal, @RequestBody String request){
        if(principal==null){
            return ResponseEntity.status(301).build();
        }
        User user = userService.findByUsername(principal.getName());
        JSONObject json = new JSONObject(request);
        String altername = json.optString("altername");
        String pass = json.optString("password");

        if(altername.length()!=0){
            user.setAltername(altername);
        }

        if(pass.length()!=0){
            user.setPassword(passwordEncoder.encode(pass));
        }
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/img")
    public ResponseEntity<?> img(@AuthenticationPrincipal Principal principal,
                                 @RequestParam("photo") MultipartFile photo){
        if(principal==null){
            return ResponseEntity.badRequest().build();
        }
        System.out.println(photo);
        return ResponseEntity.ok().build();
        }
}
