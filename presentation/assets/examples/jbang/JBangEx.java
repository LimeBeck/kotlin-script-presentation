///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.fasterxml.jackson.core:jackson-databind:2.17.0

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var value = mapper.readValue("{\"key\":\"Hello, World!\"}", Map.class);
        System.out.println(value.get("key"));
    }
}