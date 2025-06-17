package com.todolist.todolist.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ResponseTarefaDTO(
        Long id,
        String nomeTarefa,
        String descricaoTarefa,
        boolean status,
        LocalDate dataTarefa,
        LocalTime horaTarefa
) {
}
