package dev.argia.ktorsample.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * DSL accessing object of User
 * Used in SchemeUtils also.
 */
object  Users: IntIdTable() {
    val username = varchar("username", 20).uniqueIndex()
    val firstName = varchar("firstName", 20)
    val lastName = varchar("lastName", 20)
}

/**
 * Entity object of User
 */
class User(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var username by Users.username
    var firstName by Users.firstName
    var lastName by Users.lastName
}

@Serializable
data class UserDTO(
    val username: String,
    val firstName: String,
    val lastName: String
)

fun toUserDTO(user: User): UserDTO {
    return UserDTO(
        username = user.username,
        firstName = user.firstName,
        lastName = user.lastName
    )
}