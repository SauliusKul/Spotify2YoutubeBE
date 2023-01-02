package com.Saulius.Spotify2Youtube.controllers;

import com.Saulius.Spotify2Youtube.dto.PlaylistDto;
import com.Saulius.Spotify2Youtube.services.PlaylistRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.util.List;


@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:5173/")
public class PlaylistController {

    @Autowired
    private PlaylistRequestService playlistRequestService;

    @GetMapping("/getUserPlaylists")
    public ResponseEntity<List<PlaylistDto>> getUserPlaylists() {
        return ResponseEntity.ok(playlistRequestService.getTopPlaylists());
    }

}
