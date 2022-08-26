package com.parada.maquinas.maquinaapi.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "Maquina")
data class Maquina (
    @Id @GeneratedValue
    var id: Long? = null,                        // identificador da parada (inteiro, não nulo)
    var machine_tag: String? = null,            // identificador da máquina (texto de até 24 caracteres, não nulo)
    var start_time: String? = null,            // tempo de início da parada (não nulo)
    var end_time: String,                     // tempo de finalização da parada
    var reason: String,                      // Descrição do motivo da parada (texto de até 128 caracteres)
    var interval_start: String,
    var interval_end: String
)