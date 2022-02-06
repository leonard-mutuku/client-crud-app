/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author leonard
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 369302676110327569L;
    
    private String username;
    private String password;
    private boolean isLoggedIn;
    private String lastLogin;
    
    public User(String username, boolean isloggedIn, String lastLogin) {
        this.username = username;
        this.isLoggedIn = isloggedIn;
        this.lastLogin = lastLogin;
    }
    
}
