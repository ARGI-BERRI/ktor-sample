package dev.argia.ktorsample.controller.response

import kotlinx.serialization.Serializable

@Serializable
data class OkResponse(
    override val message: String
) : Response