package bituum.project.tpbot.service

import bituum.project.tpbot.bot.BotProperties
import bituum.project.tpbot.command.StaticReplyCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.objects.Update
import java.nio.charset.UnsupportedCharsetException


@Component
class BotService() : TelegramWebhookBot() {

    @Autowired
    private lateinit var properties: BotProperties
    @Autowired
    private lateinit var handler: UpdateHandler
    override fun getBotToken(): String {
        return properties.token
    }

    override fun getBotUsername(): String {
        return properties.name
    }

    override fun onWebhookUpdateReceived(update: Update): BotApiMethod<*> {
        if (update.hasMessage()) {
            val message = update.message
            val sender = message.from
            val chatId: Long = message.chatId
            executeAsync(DeleteMessage(chatId.toString(), message.messageId))
        }
        throw UnsupportedCharsetException("Not yet implemented")
    }

    override fun getBotPath(): String {
        return properties.webhookPath
    }
}

