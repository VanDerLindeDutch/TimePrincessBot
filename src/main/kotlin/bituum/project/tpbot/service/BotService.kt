package bituum.project.tpbot.service

import bituum.project.tpbot.bot.BotProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import java.nio.charset.UnsupportedCharsetException


@Component
class BotService() : TelegramWebhookBot() {

    @Autowired
    private lateinit var properties: BotProperties

    override fun getBotToken(): String {
        return properties.token
    }

    override fun getBotUsername(): String {
        return properties.name
    }

    override fun onWebhookUpdateReceived(update: Update): BotApiMethod<*> {
        if (update.hasMessage()) {
            //TODO обработать получение новых сообщений
        }
        throw UnsupportedCharsetException("Not yet implemented")
    }

    override fun getBotPath(): String {
        return properties.webhookPath
    }
}