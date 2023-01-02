package com.Saulius.Spotify2Youtube.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;

import java.io.IOException;

@Service
@Slf4j
public class PlaylistRequestService {

    @Autowired
    AuthenticationService authenticationService;

    public String getTopPlaylists() {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(authenticationService.getAccessToken())
                .build();

        GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest  = spotifyApi
            .getListOfCurrentUsersPlaylists()
            .limit(5)
            .build();

        try {
            Paging<PlaylistSimplified> playlists = getListOfCurrentUsersPlaylistsRequest.execute();
            log.info("Playlists:: {}", playlists);
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            log.error("Error in fetching top 5 playlists from spotify API:: {}", e);
        }

        return null;
    }
}
