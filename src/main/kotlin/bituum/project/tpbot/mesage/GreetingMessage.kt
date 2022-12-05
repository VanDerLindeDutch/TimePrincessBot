package bituum.project.tpbot.mesage

import org.telegram.telegrambots.meta.api.methods.send.SendMessage

class GreetingMessage(chatId: String) : SendMessage(chatId, START_MESSAGE) {
    companion object {
        private const val fireEmojiUnicode = "\uD83D\uDD25"
        private val START_MESSAGE: String = """
            $fireEmojiUnicode Привет Принцесса! $fireEmojiUnicode
            Начни с команды /help, чтобы узнать функционал
        """.trimIndent()
    }

}