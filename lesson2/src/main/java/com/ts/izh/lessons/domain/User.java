package com.ts.izh.lessons.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    int id;

    @Column(name = "name")
    String name;

}
