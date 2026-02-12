package com.fourmaintrauxcamille.reservation.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String
)
