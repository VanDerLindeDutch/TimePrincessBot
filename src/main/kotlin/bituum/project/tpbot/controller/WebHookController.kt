package bituum.project.tpbot.controller

import bituum.project.tpbot.service.BotService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update

@RestController
class WebHookController {
    @Autowired
    lateinit var botService: BotService

    @PostMapping("/")
    fun webHookUpdateReceiver(@RequestBody update: Update): BotApiMethod<*> {
        return botService.onWebhookUpdateReceived(update)
    }
}