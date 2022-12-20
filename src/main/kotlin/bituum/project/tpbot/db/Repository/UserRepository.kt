package bituum.project.tpbot.db.Repository

import bituum.project.tpbot.db.DTO.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findUserByChatId(userId: Long): User?
}