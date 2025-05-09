package com.todolist.todolist.repositoy;

import com.todolist.todolist.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByDataTarefa(LocalDate dataTarefa);

}
