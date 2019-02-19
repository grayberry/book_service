package am.dreamteam.bookservice.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {


    @RequestMapping("/error")
    public ModelAndView handleErrorPage(HttpServletRequest request) {
        Object error = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(error != null) {
            Integer errorCode = Integer.valueOf(error.toString());

            if (errorCode == 404) {
                return new ModelAndView("redirect:/");
            }
        }

        return new ModelAndView("error");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}