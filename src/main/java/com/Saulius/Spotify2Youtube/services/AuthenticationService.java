package com.Saulius.Spotify2Youtube.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;
import java.util.Random;


@Service
@Slf4j
public class AuthenticationService {

    @Value("${config.client_id}")
    private String clientId;
    @Value("${config.client_secret}")
    private String clientSecret;
    private final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/api/getUserCode");
    // Will this work with multiple users at the same time???
    private SpotifyApi spotifyApi;
    private AuthorizationCodeUriRequest authorizationCodeUriRequest;
    private String state;

    public String authorizationCodeUri() {
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId.toString())
                .setClientSecret(clientSecret.toString())
                .setRedirectUri(redirectUri)
                .build();

        state = stateGenerator(10);

        authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
            .state(state)
                .build();

        final URI uri = authorizationCodeUriRequest.execute();

        log.info("URI:: {}", uri.toString());
        return uri.toString();
    }

    public boolean checkState(String state) {
        return this.state.equals(state) ? true : false;
    }

    public void setAccessAndRefreshTokens(String code) throws Exception{
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
                .build();

        AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
        spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
        spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
        //Refresh token
    }

    public String getAccessToken() {
        return spotifyApi.getAccessToken();
    }

    public SpotifyApi getSpotifyApi() {
        return spotifyApi;
    }


    private String stateGenerator(int length) {
        int upperBound = 26;
        Random rand = new Random();
        String generatedString = "";

        for (int i = 0; i < length; i++) {
            generatedString += Character.toString((char) (65 + rand.nextInt(upperBound)));
        }
        return generatedString;
    }
}


//TODO: implement refresh tokens
//TODO: write tests
//TODO: