package bituum.project.tpbot.bot

import bituum.project.tpbot.db.Repository.CodeRepository
import bituum.project.tpbot.db.Repository.UserRepository
import bituum.project.tpbot.mesage.GetCode
import bituum.project.tpbot.mesage.GreetingMessage
import bituum.project.tpbot.mesage.SendCode
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue


@Component
class TimePrincessBot : TelegramLongPollingBot() {
    val queue: Queue<Message> = ConcurrentLinkedQueue()
    var activeChatIdSet: MutableSet<Long> = Collections.synchronizedSet(HashSet<Long>())

    @Autowired
    private lateinit var botProperties: BotProperties

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var codeRepository: CodeRepository

    @PostConstruct
    fun register() {
        TelegramBotsApi(DefaultBotSession::class.java).registerBot(this)
    }


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
            when (message.text) {
                "/start" -> {
                    if (activeChatIdSet.contains(message.chatId)) {
                        executeAsync(SendMessage(message.chatId.toString(), "Вы уже задействовали команду"))
                    } else {
                        activeChatIdSet.add(message.chatId)
                        GreetingMessage(this, message, userRepository).greetingMessageFun()
                    }
                }

                "Отправить код" -> {
                    if (activeChatIdSet.contains(message.chatId)) {
                        executeAsync(SendMessage(message.chatId.toString(), "Вы уже задействовали команду"))
                    } else {
                        activeChatIdSet.add(message.chatId)
                        SendCode(this, message, userRepository, codeRepository).send()
                    }
                }

                "Активировать коды" ->{
                    if (activeChatIdSet.contains(message.chatId)) {
                        executeAsync(SendMessage(message.chatId.toString(), "Вы уже задействовали команду"))
                    } else {
                        activeChatIdSet.add(message.chatId)
                        GetCode(this, message, userRepository, codeRepository).get()
                    }
                }

                else -> queue.add(message)
            }
        }
    }
}


