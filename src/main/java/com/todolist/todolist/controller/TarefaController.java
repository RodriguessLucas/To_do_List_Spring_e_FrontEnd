package com.todolist.todolist.controller;

import com.todolist.todolist.entity.Tarefa;
import com.todolist.todolist.service.TarefaService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService){
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public List<Tarefa> listarTarefasDia(LocalDate dia){
        return tarefaService.listarTarefaDodia(dia);
    }

    @PostMapping
    public Tarefa salvarTarefa(@RequestBody Tarefa tarefa){
        return tarefaService.criarTarefa(tarefa);
    }

    @PostMapping("/{id}")
    public Tarefa atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefa){
        return tarefaService.atualizarTarefa(id, tarefa);
    }

    @PostMapping("/{status}")
    public boolean atualizarTarefaStatus(@PathVariable Long id, @RequestParam boolean status){
        return tarefaService.atualzizarStatusTarefa(id,status);
    }

    @DeleteMapping("/{id}")
    public void deletarTarefa(@PathVariable Long id){
        tarefaService.deletarTarefa(id);
    }




}
