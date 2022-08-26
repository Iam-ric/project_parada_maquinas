package com.parada.maquinas.maquinaapi.service

import com.parada.maquinas.maquinaapi.model.Maquina
import com.parada.maquinas.maquinaapi.repository.MaquinaRepository
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import java.util.*

@Service
class MaquinaServiceImplemente(private val repository: MaquinaRepository) : MaquinaService {

    override fun create(maquina: Maquina): Maquina {
        Assert.hasLength(maquina.machine_tag, "[machine_tag] não pode estar em branco")
        //Assert.hasLength(maquina.start_time, "[start_time] não pode estar em branco")
        return repository.save(maquina)
    }

    override fun getAll(): List<Maquina> {
        return repository.findAll()
    }

    override fun get(machine_tag: String, interval_start: String, interval_end: String): List<Maquina> {
        return emptyList()
    }

    override fun getbyId(id: Long): Optional<Maquina> {
        return repository.findById(id)
    }

    override fun update(id: Long, end_time: String, reason: String, maquina: Maquina): Optional<Maquina> {
        val optional = getbyId(id)
        val endTimes = maquina.end_time
        val msg = maquina.reason

        if (optional.isEmpty || endTimes.isEmpty() || msg.isEmpty()) Optional.empty<Maquina>()

        return optional.map {
            val maquinaToUpdate = it.copy(
                id = maquina.id,
                machine_tag = maquina.machine_tag,
                start_time = maquina.start_time,
                end_time = maquina.end_time,
                reason = maquina.reason,
                interval_start = maquina.interval_start,
                interval_end = maquina.interval_end
            )
            (repository.save(maquinaToUpdate))

        }
    }

    override fun delete(id: Long) {
        repository.findById(id).map {
            repository.delete(it)
        }.orElseThrow { throw RuntimeException("Id not found $id") }
    }


}
