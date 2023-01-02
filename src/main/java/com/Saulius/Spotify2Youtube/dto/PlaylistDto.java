package com.Saulius.Spotify2Youtube.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDto {

    private int id;
    private String href;
    private String imgHref;
    private String title;
    private String owner;
}
