package bituum.project.tpbot.mesage

import bituum.project.tpbot.bot.TimePrincessBot
import bituum.project.tpbot.db.Repository.UserRepository
import bituum.project.tpbot.service.authorization
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message

class GreetingMessage(
    private val bot: TimePrincessBot,
    private val message: Message,
    private val userRepository: UserRepository
) {
    fun greetingMessageFun() {
        val fireEmojiUnicode = "\uD83D\uDD25"
        val START_MESSAGE: String = """
            $fireEmojiUnicode Привет Принцесса! $fireEmojiUnicode
        """.trimIndent()
        bot.execute(SendMessage(message.chatId.toString(), START_MESSAGE))
        if (userRepository.findUserByChatId(message.chatId) == null) {
            bot.execute(
                SendMessage(
                    message.chatId.toString(),
                    "Введи iggId, чтоббы зарегитстрироваться"
                )
            )
        }else{
            bot.activeChatIdSet.remove(message.chatId)
            return
        }
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineScope.launch {
            authorization(bot, message.chatId, userRepository)
        }
        return
    }

}