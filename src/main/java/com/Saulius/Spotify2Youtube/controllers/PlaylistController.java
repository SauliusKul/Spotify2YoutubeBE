package com.Saulius.Spotify2Youtube.controllers;

import com.Saulius.Spotify2Youtube.services.PlaylistRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:5173/")
public class PlaylistController {

    PlaylistRequestService playlistRequestService;

    @GetMapping("/getUserPlaylists")
    public ResponseEntity<String> getUserPlaylists() {
        return ResponseEntity.ok(playlistRequestService.getTopPlaylists());
    }

}
