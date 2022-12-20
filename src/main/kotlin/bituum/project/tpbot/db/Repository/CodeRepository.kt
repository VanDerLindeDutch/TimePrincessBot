package bituum.project.tpbot.db.Repository

import bituum.project.tpbot.db.DTO.Code
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface CodeRepository  : CrudRepository<Code, Long> {
    fun existsCodeByCode(code:String):Boolean

}