package com.Saulius.Spotify2Youtube.services;

import com.Saulius.Spotify2Youtube.dto.PlaylistDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PlaylistRequestService {

    @Autowired
    private AuthenticationService authenticationService;

    public List<PlaylistDto> getTopPlaylists() {
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(authenticationService.getAccessToken())
                .build();

        GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest  = spotifyApi
            .getListOfCurrentUsersPlaylists()
            .limit(5)
            .build();

        try {
            Paging<PlaylistSimplified> spotifyPlaylists = getListOfCurrentUsersPlaylistsRequest.execute();

            PlaylistDto tempPlaylist = new PlaylistDto();
            List<PlaylistDto> playlists = new ArrayList<>();
            int i = 1;
            for (PlaylistSimplified playlist: spotifyPlaylists.getItems()) {
                tempPlaylist = PlaylistDto.builder()
                        .id(i)
                        .href(playlist.getHref())
                        .imgHref(Arrays.stream(playlist.getImages()).findFirst().get().getUrl())
                        .title(playlist.getName())
                        .owner(playlist.getOwner().getDisplayName())
                        .build();

                playlists.add(tempPlaylist);

                i++;
            }

            return playlists;
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            log.error("Error in fetching top 5 playlists from spotify API:: {}", e);
            return null;
        }

    }
}
