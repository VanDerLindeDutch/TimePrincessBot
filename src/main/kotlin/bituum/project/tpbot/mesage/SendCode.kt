package bituum.project.tpbot.mesage

import bituum.project.tpbot.bot.TimePrincessBot
import bituum.project.tpbot.db.Repository.CodeRepository
import bituum.project.tpbot.db.Repository.UserRepository
import bituum.project.tpbot.service.sendCodeService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message

class SendCode(
    private val bot: TimePrincessBot,
    private val message: Message,
    private val userRepository: UserRepository,
    private val codeRepository: CodeRepository
) {

    fun send() {
        val START_MESSAGE: String
        val iggId: Long? = this.userRepository.findUserByChatId(message.chatId)?.iggId
        if (iggId == null) {
            START_MESSAGE = "Сначало надо зарегистрироваться =( , введи команду /start"
            bot.execute(SendMessage(message.chatId.toString(), START_MESSAGE))
            bot.activeChatIdSet.remove(message.chatId)
            return
        }
        val STAR_UNICODE = "\u0000\u2b50"
        START_MESSAGE = """
        $STAR_UNICODE Ты можешь поддержать сообщество игры, отправив код бонуса! $STAR_UNICODE
        Введите код в следующем сообщении
        """.trimIndent()
        bot.execute(SendMessage(message.chatId.toString(), START_MESSAGE))
        bot.execute(
            SendMessage(
                message.chatId.toString(),
                "Чтобы остановить выполнение команды, введите команду /stop"
            )
        )
        CoroutineScope(Dispatchers.Default).launch {
            sendCodeService(bot, message.chatId, codeRepository)
        }
    }
}