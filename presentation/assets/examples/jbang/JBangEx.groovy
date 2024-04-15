///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.fasterxml.jackson.core:jackson-databind:2.17.0

import com.fasterxml.jackson.databind.ObjectMapper

def value = new ObjectMapper().with {
    readValue('{"key":"Hello, World!"}', Map.class)["key"]
}
println value