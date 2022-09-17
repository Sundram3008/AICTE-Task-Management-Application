package com.sundram.aictetaskmanagement.controller;

import java.util.logging.Logger;

import javax.security.auth.login.CredentialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sundram.aictetaskmanagement.config.JwtUtils;
import com.sundram.aictetaskmanagement.model.JwtRequest;
import com.sundram.aictetaskmanagement.model.JwtResponse;
import com.sundram.aictetaskmanagement.service.UserDetailsServiceImpl;

/**
 * @author Shubham.mishra
 */

@RestController
@CrossOrigin
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    Logger logger = Logger.getLogger("Authenticate controller logger");

    /**
     * Available for all user
     * 
     * @param jwtRequest
     * @return
     * @throws CredentialException
     */

    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws CredentialException {
        try {
            authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
            System.out.println("Done");
        } catch (UsernameNotFoundException e) {
            logger.severe(e.toString());
            System.out.println("login says: " + e.toString());
            throw new UsernameNotFoundException("Usrname not found");
            // return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.severe("Authentication api says" + e.toString());
            throw new CredentialException(e.toString());
        }
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    /**
     * just for authenticating the user
     * 
     * @param email
     * @param password
     * @throws Exception
     */

    private void authenticate(String email, String password) throws Exception {
        try {
            System.out.println(email + "and" + password);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            logger.severe("User is disabled");
            System.out.println("JwtAuthenticator says: User is disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials");
        }
    }


}
