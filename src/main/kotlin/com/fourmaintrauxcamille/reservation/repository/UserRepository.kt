package com.fourmaintrauxcamille.reservation.repository

import com.fourmaintrauxcamille.reservation.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}
