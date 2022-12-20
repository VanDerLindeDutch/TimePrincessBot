package bituum.project.tpbot.command

import bituum.project.tpbot.db.Repository.UserRepository
import com.fasterxml.jackson.annotation.JsonManagedReference
import org.apache.catalina.User
import org.json.JSONObject
import org.json.JSONTokener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/*
* {"code":-1,"msg":"Invalid IGG ID or game currently under maintenance."}-incorrect iggID
* */

//1380112697

@Component
class Request {
    fun isIggIdCorrect(iggid: Long): Boolean {
        val response: JSONObject = request(iggid)
        println(response)
        if (response.get("msg").toString().contains("Invalid IGG ID")) {
            return false
        }
        return true
    }

    fun activateCode(iggid: Long, cdkey: String): RESPONSE_CODE {
        val response: JSONObject = request(iggid, cdkey)
        println(response)
        val msg = response.get("msg").toString()
        return if (msg.contains("-51") || msg.contains("-53") || msg.contains("-57"))
            RESPONSE_CODE.TO_DROP
        else if (msg.contains("-54"))
            RESPONSE_CODE.UNSUCCESSFUL
        else
            RESPONSE_CODE.SUCCESSFUL
    }

    fun isCodeCorrect(iggid: Long, cdkey: String): Boolean {
        val response: JSONObject = request(iggid, cdkey)
        val msg = response.get("msg").toString();
        println(response)
        if (msg.contains("-51") || msg.contains("-53") || msg.contains("-57") || msg.contains("-54")) {
            return false
        }
        return true
    }

    private fun request(iggid: Long, cdkey: String = "jOpA"): JSONObject {
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://dut.igg.com/event/code?lang=rus/"))
            .POST(HttpRequest.BodyPublishers.ofString(RequestBody(iggid, cdkey).toString()))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return JSONObject(response.body())
    }
}

enum class RESPONSE_CODE {
    SUCCESSFUL,
    UNSUCCESSFUL,
    TO_DROP
}

data class RequestBody(
    val iggid: Long,
    val cdkey: String
) {
    override fun toString(): String {
        return "iggid=$iggid&cdkey=$cdkey&username=&sign=0"
    }
}