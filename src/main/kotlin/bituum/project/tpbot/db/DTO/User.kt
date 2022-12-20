package bituum.project.tpbot.db.DTO

import jakarta.persistence.*

@Table(name = "UserTP")
@Entity
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0,
    @Column(name = "chat_id", nullable = false, unique = true)
    var chatId: Long,
    @Column(name = "igg_id", nullable = false, unique = true)
    var iggId:Long,


)