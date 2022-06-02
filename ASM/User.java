/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ASM1;

import Slide6.*;

/**
 *
 * @author Teo
 */
public class User {
    String username;
    String password;
    String role;
    String email;

    public User() {
        this.username = "";
        this.password = "";
        this.role = "";
        this.email = "";
    }

    public User(String username, String password, String role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }
    
}
