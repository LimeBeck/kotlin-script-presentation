@file:DependsOn("com.squareup.okhttp:okhttp:2.7.4")

import com.squareup.okhttp.*

job("Get example.com") {
    container(image = "amazoncorretto:17-alpine") {
        kotlinScript {
            val client = OkHttpClient()
            val request = Request.Builder().url("http://example.com").build()
            val response = client.newCall(request).execute()
            println(response)
        }
    }
}