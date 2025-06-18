package com.todolist.todolist.service;

import com.todolist.todolist.exceptions.ConflitoTarefaException;
import com.todolist.todolist.model.dto.RequestTarefaDTO;
import com.todolist.todolist.model.dto.ResponseTarefaDTO;
import com.todolist.todolist.model.entity.Tarefa;
import com.todolist.todolist.repositoy.TarefaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
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
    public Tarefa criarTarefa(RequestTarefaDTO requestTarefaDTO) {
        if(tarefaRepository.existsByDataTarefaAndHoraTarefa(requestTarefaDTO.dataTarefa(), requestTarefaDTO.horaTarefa())) {
            throw new ConflitoTarefaException("Já existe tarefa agendada na " +  requestTarefaDTO.dataTarefa() + " " +  requestTarefaDTO.horaTarefa());
        }
        Tarefa tarefa = toEntity(requestTarefaDTO);
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

    //Atualizar tarefa
    public ResponseTarefaDTO atualizarTarefa(Long id, RequestTarefaDTO tarefa) {
        Tarefa tarefaAtual = tarefaRepository.findById(id).get();
        tarefaAtual.setNomeTarefa(tarefa.nomeTarefa());
        tarefaAtual.setDescricaoTarefa(tarefa.descricaoTarefa());
        tarefaAtual.setDataTarefa(tarefa.dataTarefa());
        tarefaAtual.setHoraTarefa(tarefa.horaTarefa());
        tarefaAtual.setStatus(tarefa.status());
        tarefaRepository.save(tarefaAtual);

       return new ResponseTarefaDTO(
               id,
               tarefa.nomeTarefa(),
               tarefa.descricaoTarefa(),
               tarefa.status(),
               tarefa.dataTarefa(),
               tarefa.horaTarefa()
       );
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
