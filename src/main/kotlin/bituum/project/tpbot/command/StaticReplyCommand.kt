package bituum.project.tpbot.command

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender


class StaticReplyCommand<T : BotApiMethodMessage?>(
    commandIdentifier: String?,
    description: String?,
    var cls: Class<T>
) : BotCommand(commandIdentifier, description) {

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val future =
            absSender.executeAsync(cls.getConstructor(String::class.java).newInstance(chat.id.toString()))

        if (future.isCancelled) {
            future.handle { fn, err ->
                if (err != null) {
                    //TODO прикрутить норм логгер
                    println("UNKNOWN EXCEPTION")
                    return@handle null
                }
                return@handle fn.isCommand
            }.thenAccept {
                println("command been provided")
            }
        }
    }

}