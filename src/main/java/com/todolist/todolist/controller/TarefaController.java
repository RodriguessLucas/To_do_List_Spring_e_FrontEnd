package com.todolist.todolist.controller;

import com.todolist.todolist.exceptions.TarefaNotFoundException;
import com.todolist.todolist.model.dto.AtualizarTarefaStatusDTO;
import com.todolist.todolist.model.dto.RequestTarefaDTO;
import com.todolist.todolist.model.dto.ResponseTarefaDTO;
import com.todolist.todolist.service.TarefaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ResponseTarefaDTO>> listarTarefasDia(@RequestParam("dia") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dia){
        List<ResponseTarefaDTO> tarefas = tarefaService.listarTarefaDodia(dia);

        if(tarefas.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(tarefas);
    }

    @PostMapping("/salvar")
    public ResponseEntity<ResponseTarefaDTO> salvarTarefa(@RequestBody RequestTarefaDTO requestTarefaDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.criarTarefa(requestTarefaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTarefaDTO> atualizarTarefa(@PathVariable Long id, @RequestBody RequestTarefaDTO tarefa){
        return ResponseEntity.ok(tarefaService.atualizarTarefa(id, tarefa));
    }

    @PutMapping
    public ResponseEntity<Boolean> atualizarTarefaStatus(@RequestBody AtualizarTarefaStatusDTO status){
        return ResponseEntity.ok(tarefaService.atualzizarStatusTarefa(status));
    }


    // temos erro aqui, não deleta tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id){
        try {
            tarefaService.deletarTarefa(id);
            return ResponseEntity.noContent().build();
        }
        catch (TarefaNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }




}
