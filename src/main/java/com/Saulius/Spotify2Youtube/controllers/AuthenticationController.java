package com.Saulius.Spotify2Youtube.controllers;

import com.Saulius.Spotify2Youtube.services.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/api")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping ("/getSpotifyToken")
    public ResponseEntity<String> getAuthenticationURI() {
        String response = authenticationService.authorizationCodeUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "text/html");
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(response);
    }

    @GetMapping("/getUserCode")
    public void getUserCode(@RequestParam(required = false) String code,
                            @RequestParam(required = false) String error,
                            @RequestParam String state,
                            HttpServletResponse httpServletResponse) {

        try {
            if (error != null) {
                log.info("Spotify refused to provide user code due to the following reason:: {}", error);
                httpServletResponse.sendRedirect("http://localhost:5173/landing");
                return;
            }

            if (authenticationService.checkState(state) == false) {
                log.error("Passed state and returned states do not match");
                httpServletResponse.sendRedirect("http://localhost:5173/landing");
                return;
            }

            try {
                authenticationService.setAccessAndRefreshTokens(code);
                httpServletResponse.sendRedirect("http://localhost:5173/home");
                return;
            } catch (Exception e) {
                log.warn("Failed to set access and refresh tokens:: {}", e);
                httpServletResponse.sendRedirect("http://localhost:5173/landing");
            }

        } catch (IOException e) {
                log.error("Attepting to redirect front end failed:: {}", e);
        }
    }
}
