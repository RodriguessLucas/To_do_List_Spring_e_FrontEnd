package com.todolist.todolist.service;

import com.todolist.todolist.model.dto.RequestTarefaDTO;
import com.todolist.todolist.model.dto.ResponseTarefaDTO;
import com.todolist.todolist.model.entity.Tarefa;
import com.todolist.todolist.repositoy.TarefaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class TarefaService {
    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository){
        this.tarefaRepository = tarefaRepository;
    }


    //Converter DTO para entity
    public Tarefa toEntity(RequestTarefaDTO requestTarefaDTO) {
        Tarefa tarefa = new Tarefa();
        tarefa.setNomeTarefa(requestTarefaDTO.nomeTarefa());
        tarefa.setDescricaoTarefa(requestTarefaDTO.descricaoTarefa());
        tarefa.setStatus(requestTarefaDTO.status());
        tarefa.setDataTarefa(requestTarefaDTO.dataTarefa());
        tarefa.setHoraTarefa(requestTarefaDTO.horaTarefa());
        return tarefa;
    }


    //CRUD DAS TAREFAS

    //Criar
    public Tarefa criarTarefa(Tarefa tarefa){
        return tarefaRepository.save(tarefa);
    }

    //Carregar tarefas do dia
    public List<ResponseTarefaDTO> listarTarefaDodia(LocalDate data){
        return tarefaRepository.findByDataTarefa(data)
                .stream()
                .sorted(Comparator.comparing(Tarefa::getHoraTarefa))
                .map(tarefa -> new ResponseTarefaDTO(
                        tarefa.getId(),
                        tarefa.getNomeTarefa(),
                        tarefa.getDescricaoTarefa(),
                        tarefa.isStatus(),
                        tarefa.getDataTarefa(),
                        tarefa.getHoraTarefa()
                ))
                .collect(Collectors.toList());
    }

    //Carregar todas as tarefas // sem motivo de uso
    public List<Tarefa> listarTodasTarefa(){
        return tarefaRepository.findAll();
    }

    //Atualizar tarefa
    public Tarefa atualizarTarefa(Long id,Tarefa tarefa){
        Tarefa tarefaAtual = tarefaRepository.findById(id).get();
        tarefaAtual.setNomeTarefa(tarefa.getNomeTarefa());
        tarefaAtual.setDescricaoTarefa(tarefa.getDescricaoTarefa());
        tarefaAtual.setDataTarefa(tarefa.getDataTarefa());
        tarefaAtual.setHoraTarefa(tarefa.getHoraTarefa());
        tarefaAtual.setStatus(tarefa.isStatus());
        return tarefaRepository.save(tarefaAtual);
    }

    //Deletar
    public void deletarTarefa(Long id){
        tarefaRepository.deleteById(id);
    }

    //Atualizar status tarefa
    public boolean atualzizarStatusTarefa(Long id, boolean status){
        Tarefa tarefaAtual = tarefaRepository.findById(id).get();
        tarefaAtual.setStatus(status);
        tarefaRepository.save(tarefaAtual);
        return tarefaAtual.isStatus();
    }


}
