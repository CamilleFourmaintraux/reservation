package com.fourmaintrauxcamille.reservation.service

import com.fourmaintrauxcamille.reservation.entity.User
import com.fourmaintrauxcamille.reservation.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }
}
