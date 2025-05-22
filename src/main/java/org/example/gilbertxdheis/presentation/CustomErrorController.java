package org.example.gilbertxdheis.presentation;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorTitle = "Oops! Something went wrong";
        String errorMessage = "We apologize for the inconvenience. Please try again later or contact our support team for assistance.";
        String errorCode = "500";

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            switch (statusCode) {
                case 404:
                    errorTitle = "Page Not Found";
                    errorMessage = "The page you're looking for doesn't exist or has been moved.";
                    errorCode = "404";
                    break;
                case 403:
                    errorTitle = "Access Denied";
                    errorMessage = "You don't have permission to access this resource.";
                    errorCode = "403";
                    break;
                case 400:
                    errorTitle = "Bad Request";
                    errorMessage = "The request could not be understood by the server.";
                    errorCode = "400";
                    break;
                case 401:
                    errorTitle = "Unauthorized";
                    errorMessage = "Please log in to access this resource.";
                    errorCode = "401";
                    break;
                case 500:
                    errorTitle = "Internal Server Error";
                    errorMessage = "Our server encountered an unexpected condition. Our team has been notified.";
                    errorCode = "500";
                    break;
                case 503:
                    errorTitle = "Service Unavailable";
                    errorMessage = "Our service is temporarily unavailable. Please try again later.";
                    errorCode = "503";
                    break;
            }
        }

        model.addAttribute("errorTitle", errorTitle);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorCode", errorCode);

        // Log the error (you can add your logging implementation here)

        return "error";
    }
}