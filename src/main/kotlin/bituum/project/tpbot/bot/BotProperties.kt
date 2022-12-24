package bituum.project.tpbot.bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:/application.properties")
class BotProperties {

    @Value("\${bot.name}")
    lateinit var name: String

    @Value("\${bot.token}")
    lateinit var token: String

}