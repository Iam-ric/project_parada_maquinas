package com.parada.maquinas.maquinaapi

import com.fasterxml.jackson.databind.ObjectMapper
import com.parada.maquinas.maquinaapi.model.Maquina
import com.parada.maquinas.maquinaapi.repository.MaquinaRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class MaquinaControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var maquinaRepository: MaquinaRepository

    @Test
    fun `test find all`(){
        maquinaRepository.save(
            Maquina(
            id = 100,
            machine_tag = "iam",
            start_time = "11:00",
            end_time = "12:00:00",
            reason = "queimou",
            interval_start = "13:00:00",
            interval_end = "14:00:00"

        )
        )
       mockMvc.perform(MockMvcRequestBuilders.get("/machine_halt"))
           .andExpect(MockMvcResultMatchers.status().isOk)
           .andExpect(MockMvcResultMatchers.jsonPath("\$").isArray)
           .andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isNumber)
           .andExpect(MockMvcResultMatchers.jsonPath("\$[0].machine_tag").isString)
           .andExpect(MockMvcResultMatchers.jsonPath("\$[0].start_time").isString)
           .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test find by id`(){
        val maquina = maquinaRepository.save(
            Maquina(
            id = 1,
            machine_tag = "iam",
            start_time = "11:00",
            end_time = "12:00:00",
            reason = "queimou",
            interval_start = "13:00:00",
            interval_end = "14:00:00"

        ))
        mockMvc.perform(MockMvcRequestBuilders.get("/machine_halt/${maquina.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(maquina.id))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.machine_tag").value(maquina.machine_tag))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.start_time").value(maquina.start_time))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test find create maquina`(){
        val maquina = Maquina(
            id = 100,
            machine_tag = "iam",
            start_time = "11:00",
            end_time = "12:00:00",
            reason = "queimou",
            interval_start = "13:00:00",
            interval_end = "14:00:00"
        )
        val json = ObjectMapper().writeValueAsString(maquina)
        maquinaRepository.deleteAll()

        mockMvc.perform(MockMvcRequestBuilders.post("/machine_halt")

            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.machine_tag").value(maquina.machine_tag))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.start_time").value(maquina.start_time))
            .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(maquinaRepository.findAll().isEmpty())
    }

    // verificar rota de chamadas, esta retornando not found

    @Test
    fun `test find update maquina`(){
        val maquina = maquinaRepository.save( Maquina(
            id = 100,
            machine_tag = "iam",
            start_time = "11:00",
            end_time = "12:00:00",
            reason = "queimou",
            interval_start = "13:00:00",
            interval_end = "14:00:00"
        )
        )
            .copy(id = 200, end_time = "11:30", reason = "em manutenção")
        val json = ObjectMapper().writeValueAsString(maquina)
        mockMvc.perform(MockMvcRequestBuilders.put("/machine_halt/${maquina.id}/${maquina.end_time}/${maquina.reason}")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(maquina.id))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.end_time").value(maquina.end_time))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.reason").value(maquina.reason))
            .andDo(MockMvcResultHandlers.print())

        val findById = maquinaRepository.findById(maquina.id!!)
        Assertions.assertFalse(findById.isPresent)
    }

    @Test
    fun `test find delete maquina`(){
        val maquina = maquinaRepository.save( Maquina(
            id = 100,
            machine_tag = "iam",
            start_time = "11:00",
            end_time = "12:00:00",
            reason = "queimou",
            interval_start = "13:00:00",
            interval_end = "14:00:00"
        )
        )
        mockMvc.perform(MockMvcRequestBuilders.delete("/machine_halt/${maquina.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())

        val findById = maquinaRepository.findById(maquina.id!!)
        Assertions.assertFalse(findById.isPresent)
    }

    @Test
    fun `test find  validation error empty machine_tag`(){
        val maquina = Maquina(
            id = 100,
            machine_tag = "",
            start_time = "11:00",
            end_time = "12:00:00",
            reason = "queimou",
            interval_start = "13:00:00",
            interval_end = "14:00:00"
        )
        val json = ObjectMapper().writeValueAsString(maquina)
        maquinaRepository.deleteAll()

        mockMvc.perform(MockMvcRequestBuilders.post("/machine_halt")

            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("[machine_tag] não pode estar em branco"))
            .andDo(MockMvcResultHandlers.print())

    }


}