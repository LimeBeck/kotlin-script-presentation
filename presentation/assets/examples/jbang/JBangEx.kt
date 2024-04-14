///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.fasterxml.jackson.module:jackson-module-kotlin:2.17.0

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

val value = jacksonObjectMapper().readValue<Map<String, String>>(
    """{"key":"Hello, World!"}"""
)

fun main() {
    println(value.get("key"))
}
