package bituum.project.tpbot.service

import bituum.project.tpbot.bot.TimePrincessBot
import bituum.project.tpbot.command.Request
import bituum.project.tpbot.db.DTO.Code
import bituum.project.tpbot.db.Repository.CodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message

suspend fun sendCodeService(bot: TimePrincessBot, chatId: Long, codeRepository: CodeRepository) =
    coroutineScope {
        bot.queue.removeIf { it.chatId == chatId }
        lateinit var message: Message
        while (true) {
            if (!bot.queue.isEmpty()) {
                message = bot.queue.peek()
                if (message.chatId == chatId) {
                    bot.queue.remove(message)
                    val code: String = message.text
                    if(code == "/stop"){
                        bot.activeChatIdSet.remove(chatId)
                        break
                    }
                    if(withContext(Dispatchers.IO) {
                            codeRepository.existsCodeByCode(code)
                        }){
                        bot.activeChatIdSet.remove(chatId)
                        bot.executeAsync(SendMessage(chatId.toString(), "Код уже есть в нашей базе"))
                        break
                    }
                    if (Request().isCodeCorrect(1380112697, code)) {
                        withContext(Dispatchers.IO) {
                            codeRepository.save(Code(code = code))
                        }
                        bot.activeChatIdSet.remove(chatId)
                        bot.executeAsync(SendMessage(chatId.toString(), "Код успешно активирован"))
                        break
                    } else {
                        bot.executeAsync(SendMessage(chatId.toString(), "Неправильный код"))
                    }
                }
            }
            delay(1000L)
        }
    }