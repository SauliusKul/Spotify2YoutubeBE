package com.Saulius.Spotify2Youtube.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
public class PlaylistDto {

    private List<String> songs;
}
