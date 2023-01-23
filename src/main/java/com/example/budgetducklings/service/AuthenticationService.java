package com.example.budgetducklings.service;

import com.example.budgetducklings.repository.AuthenticationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private AuthenticationRepository authenticationRepository;

    public AuthenticationService(){
        authenticationRepository = new AuthenticationRepository();
    }

    //Gets password from database based on username and checks if the password was correct
    public boolean login(String username, String password){
        String foundPassword = authenticationRepository.login(username);
        if(foundPassword.equals(password))
        {
            return true;
        }
        return false;
    }
}
