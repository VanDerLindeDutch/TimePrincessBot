package bituum.project.tpbot.mesage

import bituum.project.tpbot.db.DTO.User
import bituum.project.tpbot.db.Repository.UserRepository
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
class GreetingMessage(chatID: Long, userRepository: UserRepository) : SendMessage(chatID.toString(), START_MESSAGE) {

    init {
        if(userRepository.findUserByChatId(chatID)==null){
            userRepository.save(User(chatId = chatID, iggId = 213L))
        }
    }

    companion object {

        private const val fireEmojiUnicode = "\uD83D\uDD25"
        val START_MESSAGE: String = """
            $fireEmojiUnicode Привет Принцесса! $fireEmojiUnicode
            Для регистрации введи IggID
        """.trimIndent()



    }

}
/*val START_MESSAGE: String = """
            $fireEmojiUnicode Привет Принцесса! $fireEmojiUnicode
            Начни с команды /help, чтобы узнать функционал
        """.trimIndent()*/