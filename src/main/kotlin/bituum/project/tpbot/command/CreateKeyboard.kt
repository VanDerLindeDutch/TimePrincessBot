package bituum.project.tpbot.command

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
//мб поменять реализацию клавиатуру
fun createKeyboard(message: SendMessage):SendMessage{
    message.enableMarkdown(true)
    message.replyMarkup = getReplyMarkup(
        listOf(
            listOf("Отправить код", "Активировать коды")
        ))
    return message
}

private fun getReplyMarkup(buttons:List<List<String>>):ReplyKeyboardMarkup{
    val markup = ReplyKeyboardMarkup();
    markup.resizeKeyboard = true
    markup.keyboard = buttons.map {rowButtons ->
        val row = KeyboardRow()
        rowButtons.forEach{
            rowButton->row.add(rowButton)

        }
        row
    }
    return markup;


}