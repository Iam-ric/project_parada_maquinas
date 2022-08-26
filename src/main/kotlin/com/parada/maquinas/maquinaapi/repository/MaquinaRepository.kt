package com.parada.maquinas.maquinaapi.repository

import com.parada.maquinas.maquinaapi.model.Maquina
import org.springframework.data.jpa.repository.JpaRepository

interface MaquinaRepository : JpaRepository<Maquina, Long> {
}