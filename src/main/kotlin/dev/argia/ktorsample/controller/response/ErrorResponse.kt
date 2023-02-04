package dev.argia.ktorsample.controller.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    override val message: String
) : Response