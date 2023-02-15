package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "participates")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Participates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "post_id")
    private int postId;
}
