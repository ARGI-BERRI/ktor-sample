package dev.argia.ktorsample.controller

import dev.argia.ktorsample.controller.response.ErrorResponse
import dev.argia.ktorsample.controller.response.OkResponse
import dev.argia.ktorsample.entity.UserDTO
import dev.argia.ktorsample.exception.NotFoundException
import dev.argia.ktorsample.exception.UsernameExistsException
import dev.argia.ktorsample.service.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.patch
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val service = UserService()

    routing {
        get<UsersRoute> {
            val users = service.getAllUsers()
            call.respond(users)
        }

        get<UsersRoute.Username> {
            try {
                call.respond(
                    HttpStatusCode.OK,
                    service.getUserByUsername(it.username)
                )
            } catch (e: NotFoundException) {
                call.respond(
                    HttpStatusCode.NotFound,
                    ErrorResponse(e.message)
                )
            }
        }

        post<UsersRoute> {
            val request = call.receive<UserDTO>()

            try {
                service.createUser(request)
            } catch (e: UsernameExistsException) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ErrorResponse(e.message)
                )
            }

            call.respond(
                HttpStatusCode.OK,
                OkResponse("successfully created: ${request.username}")
            )
        }

        patch<UsersRoute.Username> { }
        delete<UsersRoute.Username> { }
    }
}
