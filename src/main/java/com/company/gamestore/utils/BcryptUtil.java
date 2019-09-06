package com.company.gamestore.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class BcryptUtil {
  public static void main(String[] args) {
    PasswordEncoder enc = new BCryptPasswordEncoder();
    String[] users = new String[]{"plainUser", "managerUser", "adminUser", "staffUser"};
    for(String user: users ){
      String encodedPassword = enc.encode(user);
      System.out.println( user + ": " + encodedPassword);
    }
  }
}
