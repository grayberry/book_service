package am.dreamteam.bookservice.rest;

import am.dreamteam.bookservice.entities.users.User;
import am.dreamteam.bookservice.service.UserService;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/user/mypage")
public class UserRestController {

    private final String UPLOAD_FOLDER = "/home/tambovflow/IntelliJIDEAProjects/book_service/cloud";

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

    @PostMapping(value = "/img")
    public ResponseEntity<?> img(@AuthenticationPrincipal Principal principal,
                                 @RequestParam(name = "file") MultipartFile file){
        if(principal==null){
            return ResponseEntity.badRequest().build();
        }
        if(file!=null && !file.getOriginalFilename().equals("")){
            File uploadDir = new File(UPLOAD_FOLDER);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuid = UUID.randomUUID().toString();
            String resultFileName = uuid + "." + file.getOriginalFilename();
            try {
                file.transferTo(new File(UPLOAD_FOLDER + "/" + resultFileName));
                User user = userService.findByUsername(principal.getName());
                user.setPhoto(resultFileName);
                userService.save(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/user/mypage");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
}
