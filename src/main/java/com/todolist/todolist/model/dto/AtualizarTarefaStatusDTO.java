package com.todolist.todolist.model.dto;

public record AtualizarTarefaStatusDTO(
        Long id,
        boolean status
) {
}
