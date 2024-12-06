package com.northcoders.record_inventory_mgr_api.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="album_id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, unique=true)
    private String title;

    @Column
    private String description;


    @Column(nullable = false)
    //    @Enumerated(EnumType.STRING)  - as album may have m:m relationship, thus cant represent as string
    private Genre genre;

    @Column
    private LocalDate release_date;


    @Column(name = "cover_img_url")
    private String coverImg;

    @Column(name = "stock_quantity")
    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "album", fetch = FetchType.LAZY)
    private Stock stockQuantity;
    // TODO Seperate table with 1-1 bi directional relationship

    @Column(nullable = false)
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })   // as dont want any album deletes cascading down and deleting artists
    @JoinTable(name="artist_album", joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private Set<Artist> artists;   // Set to ensure no dupes links between albums and artists
    // private String artist;
    // https://asbnotebook.com/jpa-many-to-many-example-spring-boot/#create-repository-service-and-controller-layers


    // TODO: Do we need these? for updating many to many mappings?
    public void addArtist(Artist artist) {
        // Allows artist to be added to an existing album
        // creates association with the artist entity for a partitcular album entity
        this.artists.add(artist);
        artist.getAlbums().add(this);
    }

    public void removeArtist(Artist artist) {
        // removes association with the artist entity for a particular album entity
        this.getArtists().remove(artist);
        artist.getAlbums().remove(this);
    }

    public void removeArtists() {
        // remove mapping of existing artists with the album entity before deleting it
        for (Artist artist : new HashSet<>(artists)) {
            removeArtist(artist);
        }
    }

}
