package bituum.project.tpbot.bot

import bituum.project.tpbot.db.Repository.UserRepository
import bituum.project.tpbot.mesage.GreetingMessage
import bituum.project.tpbot.mesage.greetingMessageFun
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue



@Component
class TimePrincessBot : TelegramLongPollingBot() {
    val queue: Queue<Message> = ConcurrentLinkedQueue()

    @PostConstruct
    fun register() {
        TelegramBotsApi(DefaultBotSession::class.java).registerBot(this)
    }


    @Autowired
    private lateinit var botProperties: BotProperties

    @Autowired
    private lateinit var userRepository: UserRepository;
    override fun getBotToken(): String {
        return botProperties.token
    }

    override fun getBotUsername(): String {
        return botProperties.name
    }

    //1380112697
    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            val message = update.message
            if (message.hasText()) {
                queue.add(message)
            }
            when (message.text) {
                "/start" -> greetingMessageFun(this, message, userRepository)
                "Отправить код" -> println()
            }
        }
    }
}


