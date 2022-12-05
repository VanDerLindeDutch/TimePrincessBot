package bituum.project.tpbot.bot

import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Component
class BotInit : InitializingBean {

    override fun afterPropertiesSet() {
        connectTelegramBot()
    }

    private fun connectTelegramBot() {
        val bot = TelegramBotsApi(DefaultBotSession::class.java)
        //TODO сделать метод асинхронного подключения с ретраем (3-5 раз)
        //bot.registerBot()
    }

}