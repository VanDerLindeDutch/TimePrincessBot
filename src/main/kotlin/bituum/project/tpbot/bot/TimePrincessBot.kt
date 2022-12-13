package bituum.project.tpbot.bot

import bituum.project.tpbot.command.createKeyboard
import bituum.project.tpbot.db.DTO.User
import bituum.project.tpbot.db.Repository.UserRepository
import bituum.project.tpbot.mesage.GreetingMessage
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession


@Component
class TimePrincessBot : TelegramLongPollingBot() {

    @PostConstruct
    fun register(){
        TelegramBotsApi(DefaultBotSession::class.java).registerBot(this)
    }


    @Autowired
    private lateinit var botProperties:BotProperties

    @Autowired
    private lateinit var userRepository: UserRepository;
    override fun getBotToken(): String {
        return botProperties.token
    }

    override fun getBotUsername(): String {
        return botProperties.name
    }

    override fun onUpdateReceived(update: Update) {
        if(update.hasMessage()){

            val message = update.message


            when(message.text){
                "/start"->executeAsync(
                    createKeyboard(
                    GreetingMessage(
                        message.chatId, userRepository)
                ))
                "Отправить код"->println("l")
            }
        }
    }


}