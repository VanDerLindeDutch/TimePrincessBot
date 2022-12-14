package bituum.project.tpbot.command

import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


class Request {

    fun request(){
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://dut.igg.com/event/code?lang=rus/"))
            .POST(HttpRequest.BodyPublishers.ofString(RequestBody(1380112697,"dsas").toString()))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        println(response.body())


    }
}

data class RequestBody(val iggid:Int,
    val cdkey:String){
    override fun toString(): String {
        return "iggid=$iggid&cdkey=$cdkey&username=&sign=0"
    }
}