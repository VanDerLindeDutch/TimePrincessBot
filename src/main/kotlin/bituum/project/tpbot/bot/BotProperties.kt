package bituum.project.tpbot.bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class BotProperties {

    @Value("\${telegram.webhook_path}")
    lateinit var webhookPath: String

    @Value("\${telegram.bot_name}")
    lateinit var name: String

    @Value("\${telegram.bot_token}")
    lateinit var token: String

}