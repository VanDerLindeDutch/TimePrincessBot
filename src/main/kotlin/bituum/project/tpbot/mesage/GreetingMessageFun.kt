package bituum.project.tpbot.mesage

import bituum.project.tpbot.bot.TimePrincessBot
import bituum.project.tpbot.db.Repository.UserRepository
import bituum.project.tpbot.service.authorization
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.*
import org.telegram.telegrambots.meta.bots.AbsSender


fun greetingMessageFun(bot: TimePrincessBot, message: Message, userRepository: UserRepository){
    val fireEmojiUnicode = "\uD83D\uDD25"
    val START_MESSAGE: String = """
            $fireEmojiUnicode Привет Принцесса! $fireEmojiUnicode
        """.trimIndent()
    bot.execute(SendMessage(message.chatId.toString(), START_MESSAGE))
    if(userRepository.findUserByChatId(message.chatId)==null){
        println(bot.me)
        bot.execute(SendMessage(message.chatId.toString(), "Введи iggId, чтоббы зарегитстрироваться"))
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineScope.launch {
            authorization(bot, message.chatId, userRepository)
        }
    }
    else{
        return
    }


}

