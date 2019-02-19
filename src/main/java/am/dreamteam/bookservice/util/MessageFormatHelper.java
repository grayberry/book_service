package am.dreamteam.bookservice.util;

import am.dreamteam.bookservice.service.UserBooksService;
import org.springframework.stereotype.Component;

@Component
public class MessageFormatHelper {

    private UserBooksService userBooksService;

    public MessageFormatHelper(UserBooksService userBooksService) {
        this.userBooksService = userBooksService;
    }

    public String transferResponse(String userFrom, Integer bookId){
        String response = "{" +
                "\"user\" : \"" + userFrom  +"\"" +
                ",\"content\" : " + userBooksService.findUserBookById(bookId) +
                ",\"placeholder\" : \"transfer\"" +
                "}";
        return response;
    }

    public String messageToJson(String userFrom, String content){
        content =  content.replaceAll("\"", "'");

        return  "{ \"user\" : " +
                "\"" + userFrom + "\"," +
                "\"content\" :\""  + content  +
                "\",\"placeholder\" : \"message\"" +
                "}";
    }
}
