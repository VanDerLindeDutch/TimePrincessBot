package bituum.project.tpbot.service

import bituum.project.tpbot.bot.TimePrincessBot
import bituum.project.tpbot.command.createKeyboard
import bituum.project.tpbot.db.DTO.User
import bituum.project.tpbot.db.Repository.UserRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import java.lang.NumberFormatException
import java.util.Queue
import kotlin.coroutines.coroutineContext

suspend fun authorization(bot:TimePrincessBot, chatId:Long, userRepository: UserRepository) = coroutineScope {
    lateinit var message:Message
    while (true){
        if (!bot.queue.isEmpty()){
            if(bot.queue.peek().chatId == chatId){
                message = bot.queue.remove()
                try{
                    userRepository.save(User(chatId = chatId,
                        iggId = message.text.toLong()))
                    bot.executeAsync(
                        createKeyboard(SendMessage(chatId.toString(), "Вы успешно зарегистрировались")))
                    break
                }catch (e:NumberFormatException){
                    bot.executeAsync(SendMessage(chatId.toString(), "Введите корректный iggID"))
                }
            }
        }
        delay(1000L)
    }


}