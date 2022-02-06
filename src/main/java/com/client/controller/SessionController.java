/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.controller;


import com.client.exception.SystemException;
import com.client.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author leonard
 */
@RestController
public class SessionController {
    
    @Autowired
    private HttpServletRequest httpServletRequest;
    
    public HttpSession getSession() {
        HttpSession session = httpServletRequest.getSession(true);
        return session;
    }
    
    public void setLoggedInUser(User logged_user) {
        HttpSession session = getSession();
        session.setAttribute("logged_user", logged_user);
    }
    
    public User getLoggedInUser(boolean check) {
        HttpSession session = getSession();
        User session_user = (User) session.getAttribute("logged_user");
        if (session_user != null && session_user.isLoggedIn()) {
            return session_user;
        } else {
            if (check) {
                return new User();
            }
            throw new SystemException("Session expired!. Please login again.");
        }
    }
}
