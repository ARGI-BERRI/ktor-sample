package dev.argia.ktorsample.service

import dev.argia.ktorsample.entity.User
import dev.argia.ktorsample.entity.UserDTO
import dev.argia.ktorsample.entity.toUserDTO
import dev.argia.ktorsample.exception.UsernameExistsException
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserService {
    fun getAllUsers(): List<UserDTO> {
        var users: List<UserDTO> = listOf()

        transaction {
            users = User.all().toList().map { toUserDTO(it) }
        }

        return users
    }

    fun getUserByUsername(username: String): UserDTO {
        var user: UserDTO? = null

        transaction {
            user = toUserDTO(User.all().first { it.username == username })
        }

        return user ?: throw NotFoundException("username $username doesn't exist")
    }

    fun updateUsername(username: String, newName: String): UserDTO {
        var user: User? = null

        transaction {
            user = User.all().first { it.username == username }
            user?.username = newName
        }

        if (user == null) {
            throw NotFoundException("username $username doesn't exist")
        }

        // FIXME: kotlinc に勝ったが null 安全に負けた
        return toUserDTO(user!!)
    }

    fun createUser(user: UserDTO) {
        try {
            transaction {
                User.new {
                    this.username = user.username
                    this.firstName = user.firstName
                    this.lastName = user.lastName
                }
            }
        } catch (e: Exception) {
            throw UsernameExistsException("username ${user.username} already exists")
        }
    }
}