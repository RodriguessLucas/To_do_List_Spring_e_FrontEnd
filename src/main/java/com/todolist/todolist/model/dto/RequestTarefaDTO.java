package com.todolist.todolist.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record RequestTarefaDTO(
        String nomeTarefa,
        String descricaoTarefa,
        boolean status,
        LocalDate dataTarefa,
        LocalTime horaTarefa
) {
}
