package dev.argia.ktorsample.controller

import io.ktor.resources.*

@Resource("/users")
class UsersRoute {
    @Resource("{username}")
    class Username(
        val parent: UsersRoute = UsersRoute(),
        val username: String
    )
}