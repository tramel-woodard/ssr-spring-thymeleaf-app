package com.demo.ssrspringthyme.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        model.addAttribute("canonicalUrl", requestURL); // global fallback
    }
    
}
