package com.Saulius.project.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api")
public class SpotifyAPIController {

    @PostMapping ("/getSpotifyToken")
    public Token getAuthenticationToken(@RequestBody String request) {
        return Token;
    }
}
