package com.Roo.demo.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RooExample {
    @Id
    Long id;

    String name;
    
    @OneToOne
    @JoinColumn(name = "babayroo")
    Roo2Example roo2Id;
}
