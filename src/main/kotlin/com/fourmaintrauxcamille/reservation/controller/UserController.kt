package com.fourmaintrauxcamille.reservation.controller

import com.fourmaintrauxcamille.reservation.entity.User
import com.fourmaintrauxcamille.reservation.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun create(@RequestBody user: User): User {
        return userService.createUser(user)
    }

    @GetMapping
    fun getAll(): List<User> {
        return userService.getAllUsers()
    }
}
