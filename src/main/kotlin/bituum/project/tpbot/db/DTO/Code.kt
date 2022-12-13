package bituum.project.tpbot.db.DTO

import jakarta.persistence.*


@Table(name = "code")
@Entity
data class Code (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(name = "code", nullable = false, unique = true , length = 30)
    var code: String? = null,
)