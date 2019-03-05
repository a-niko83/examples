package com.ts.izh.lessons.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "autos")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Auto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auto")
    int id;

    @Column(name = "model")
    String model;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="id_user")
    User user;

}
