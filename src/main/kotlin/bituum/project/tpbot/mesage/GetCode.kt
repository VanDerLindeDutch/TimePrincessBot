package bituum.project.tpbot.mesage

import bituum.project.tpbot.bot.TimePrincessBot
import bituum.project.tpbot.db.Repository.CodeRepository
import bituum.project.tpbot.db.Repository.UserRepository
import bituum.project.tpbot.service.getCodeService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message

class GetCode(
    private val bot: TimePrincessBot,
    private val message: Message,
    private val userRepository: UserRepository,
    private val codeRepository: CodeRepository
) {
    fun get() {
        val START_MESSAGE: String
        val iggId: Long? = this.userRepository.findUserByChatId(message.chatId)?.iggId
        if (iggId == null) {
            START_MESSAGE = "Сначало надо зарегистрироваться =( , введи команду /start"
            bot.execute(SendMessage(message.chatId.toString(), START_MESSAGE))
            bot.activeChatIdSet.remove(message.chatId)
            return
        }
        CoroutineScope(Dispatchers.Default).launch {
            getCodeService(bot, message.chatId, codeRepository, userRepository)
        }

    }
}