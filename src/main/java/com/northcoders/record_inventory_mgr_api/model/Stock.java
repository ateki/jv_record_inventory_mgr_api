package com.northcoders.record_inventory_mgr_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="stock_id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    @OneToOne(cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
    @JoinColumn(name = "album_id",  referencedColumnName = "id")
    private Album album;
    // This means Foreign key will be created only in the Album table i.e. extra column 'album_id' will be created in the stock table
    // 1-1 bidirectional relationship
    // FetchType.EAGER will fetch details of the Child along with the Parent ie: both stock and album details
    //      @JoinColumn annotation to configure name of column in Stock table that maps to the primary key in Album table
    //      ie fk of the Stock table  ie: album_id
    //      On owner side of relationship to specify the fk column


    @Column(nullable = false)
    // TODO add CHECK constraint >=0
    private long quantity;
}
