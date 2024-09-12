package com.shinhan.firstzone.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object error = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        model.addAttribute("status", status);
        model.addAttribute("message", message);
        model.addAttribute("error", error);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 404) {
                return "error/error404";
            } else if (statusCode == 500) {
                return "error/error500";
            }
        }
        return "error";
    }

    public String getErrorPath() {
        return "/error";
    }
}