package bituum.project.tpbot.service

import bituum.project.tpbot.bot.TimePrincessBot
import bituum.project.tpbot.command.RESPONSE_CODE.SUCCESSFUL
import bituum.project.tpbot.command.RESPONSE_CODE.TO_DROP
import bituum.project.tpbot.command.Request
import bituum.project.tpbot.db.DTO.Code
import bituum.project.tpbot.db.Repository.CodeRepository
import bituum.project.tpbot.db.Repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import kotlin.random.Random

suspend fun getCodeService(bot: TimePrincessBot, chatId: Long, codeRepository: CodeRepository, userRepository: UserRepository) =
    coroutineScope {
        var count = 0
        val list:MutableList<Code> = withContext(Dispatchers.IO) {
            codeRepository.findAll()
        } as MutableList<Code>
        val iggId = withContext(Dispatchers.IO) {
            userRepository.findUserByChatId(chatId)!!.iggId
        }
        list.forEach{
            when(Request().activateCode(iggId, it.code)){
                SUCCESSFUL -> count++
                TO_DROP -> codeRepository.delete(Code(code = it.code))
                else -> {}
            }
            delay(Random.nextLong(540))
        }
        bot.executeAsync(SendMessage(chatId.toString(), "Было успешно активировано $count кодов"))
        bot.activeChatIdSet.remove(chatId)

    }