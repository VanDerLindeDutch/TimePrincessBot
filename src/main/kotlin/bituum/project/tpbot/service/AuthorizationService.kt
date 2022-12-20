package bituum.project.tpbot.service

import bituum.project.tpbot.bot.TimePrincessBot
import bituum.project.tpbot.command.Request
import bituum.project.tpbot.command.createKeyboard
import bituum.project.tpbot.db.DTO.User
import bituum.project.tpbot.db.Repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message

suspend fun authorization(bot:TimePrincessBot, chatId:Long, userRepository: UserRepository) = coroutineScope {
    bot.queue.removeIf {it.chatId==chatId}
    lateinit var message:Message
    while (true){
        if (!bot.queue.isEmpty()){
            message = bot.queue.peek()
            if(message.chatId == chatId){
                bot.queue.remove(message)
                try{
                    val iggID = message.text.toLong()
                    if(!Request().isIggIdCorrect(iggID)){
                        throw NumberFormatException()
                    }
                    withContext(Dispatchers.IO) {
                        userRepository.save(
                            User(
                                chatId = chatId,
                                iggId = iggID
                            )
                        )
                    }
                    bot.activeChatIdSet.remove(message.chatId)
                    bot.executeAsync(
                        createKeyboard(SendMessage(chatId.toString(), "Вы успешно зарегистрировались"))
                    )
                    break
                }catch (e:NumberFormatException ){
                    bot.executeAsync(SendMessage(chatId.toString(), "Введите корректный iggID"))
                }
            }
        }
        delay(1000L)
    }
    println("reg")


}