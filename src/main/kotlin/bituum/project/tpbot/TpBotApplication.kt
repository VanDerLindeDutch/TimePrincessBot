package bituum.project.tpbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class TpBotApplication

fun main(args: Array<String>) {
    runApplication<TpBotApplication>(*args)
}
