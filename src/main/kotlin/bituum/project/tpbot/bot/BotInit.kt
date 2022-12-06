package bituum.project.tpbot.bot

import bituum.project.tpbot.service.BotService
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession


@Component
class BotInit : InitializingBean {

//    @Throws(TelegramApiException::class)
//    fun telegramBotsApi(): TelegramBotsApi {
//
//        return TelegramBotsApi(DefaultBotSession::class.java)
//    }
    @Autowired
    private lateinit var botService: BotService
    override fun afterPropertiesSet() {
        connectTelegramBot();
    }

    fun setWebHookInstance(): SetWebhook {
        return SetWebhook.builder().url(botService.botPath).build()
    }

    private fun connectTelegramBot() {
        val tgBot = TelegramBotsApi(DefaultBotSession::class.java)
        tgBot.registerBot(botService, setWebHookInstance())
    }

}