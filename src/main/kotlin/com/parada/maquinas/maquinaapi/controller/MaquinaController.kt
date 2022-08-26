package com.parada.maquinas.maquinaapi.controller

import com.parada.maquinas.maquinaapi.model.Maquina
import com.parada.maquinas.maquinaapi.service.MaquinaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/machine_halt")
class MaquinaController (private val service: MaquinaService) {

    @PostMapping("/{machine_tag}/{start_time}")
    @ResponseStatus(HttpStatus.CREATED)
    fun create( @RequestBody maquina: Maquina): Maquina = service.create(maquina)

    @GetMapping
    fun getAll(): List<Maquina> = service.getAll()

    @GetMapping("/{machine_tag}/{interval_start}/{interval_end}")
    fun get( @PathVariable machine_tag: String, @PathVariable interval_start: String, @PathVariable interval_end: String): List<Maquina> = service.getAll()


    @GetMapping("/{id}")
    fun getbyId(@PathVariable id: Long) : ResponseEntity<Maquina> =
        service.getbyId(id).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())

    @PutMapping("/{id}/{end_time}/{reason}")
    fun update(@PathVariable id: Long, @PathVariable end_time: String, @PathVariable reason: String, @RequestBody maquina: Maquina) : ResponseEntity<Maquina> =
        service.update(id, end_time, reason, maquina).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) : ResponseEntity<Void> {
        service.delete(id)
         return ResponseEntity<Void>(HttpStatus.OK)
    }


}