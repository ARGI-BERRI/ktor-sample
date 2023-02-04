package dev.argia.ktorsample.exception

class UsernameExistsException(override val message: String): RuntimeException(message)