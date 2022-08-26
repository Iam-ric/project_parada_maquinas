package com.parada.maquinas.maquinaapi.service

import com.parada.maquinas.maquinaapi.model.Maquina
import java.util.*

interface MaquinaService {
    fun create(maquina: Maquina): Maquina

    fun getAll(): List<Maquina>

    fun getbyId(id: Long) : Optional<Maquina>

    fun update(id: Long,end_time: String, reason: String, maquina: Maquina) : Optional<Maquina>

    fun delete(id: Long)

    fun get(machine_tag: String, interval_start: String, interval_end: String): List<Maquina>

}