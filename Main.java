package com.company;

import com.company.model.Artist;
import com.company.model.Datasource;
import com.company.model.SongArtist;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Datasource datasource = new Datasource();
        if (!datasource.open()) {
            System.out.println("Can't open datasource");
            return;
        }

        List<Artist> artists = datasource.queryArtists(Datasource.ORDER_BY_ASC);
        if (artists == null) {
            System.out.println("No artists!");
            return;
        }
        for (Artist artist : artists) {
            System.out.println("ID = " + artist.getId() + ", Name = " + artist.getName());
        }

        //===========================================================================
        System.out.println("");
        System.out.println("==================================================================================");
        List<String> albumsForArtist = datasource.queryAlbumsForArtist("Iron Maiden", Datasource.ORDER_BY_ASC);
        for (String album : albumsForArtist) {
            System.out.println(album);
        }

        //===========================================================================
        System.out.println("");
        System.out.println("==================================================================================");
        List<SongArtist> songArtists = datasource.queryArtistsForSongs("Heartless", Datasource.ORDER_BY_DESC);
        if (songArtists == null) {
            System.out.println("Couldn't find the artist for the song");
            return;
        }
        for (SongArtist artist : songArtists) {
            System.out.println("Artist name = " + artist.getArtistName() + ", Album name = " + artist.getAlbumName() + ", Track = " + artist.getTrack());
        }

        //===========================================================================
        System.out.println("");
        System.out.println("==================================================================================");
        datasource.querySongsMetadata();

        //===========================================================================
        System.out.println("");
        System.out.println("==================================================================================");
        int count = datasource.getCount(Datasource.TABLE_SONGS);
        System.out.println("Number of songs is: " + count);

        //===========================================================================
        System.out.println("");
        System.out.println("==================================================================================");
        datasource.createViewForSongArtists();

        //===========================================================================
        System.out.println("");
        System.out.println("==================================================================================");
        songArtists = datasource.querySongInfoView("Go Your Own Way");
        if (songArtists.isEmpty()) {
            System.out.println("Couldn't find the artist for the song!");
            return;
        }
        for (SongArtist songArtist : songArtists) {
            System.out.println("FROM VIEW - Artist name = " + songArtist.getArtistName() + " | Album name = " + songArtist.getAlbumName() + " | Track number = " + songArtist.getTrack());
        }

        //===========================================================================
        System.out.println("");
        System.out.println("==================================================================================");
        datasource.insertSong("Touch of Grey", "Grateful Dead", "In The Dark", 1);

        datasource.close();
    }
}
