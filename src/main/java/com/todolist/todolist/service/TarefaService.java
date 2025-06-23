package com.todolist.todolist.service;

import com.todolist.todolist.exceptions.ConflitoTarefaException;
import com.todolist.todolist.exceptions.StatusTarefaException;
import com.todolist.todolist.exceptions.TarefaNotFoundException;
import com.todolist.todolist.model.dto.AtualizarTarefaStatusDTO;
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
    public ResponseTarefaDTO criarTarefa(RequestTarefaDTO requestTarefaDTO) {
        if(tarefaRepository.existsByDataTarefaAndHoraTarefa(requestTarefaDTO.dataTarefa(), requestTarefaDTO.horaTarefa())) {
            throw new ConflitoTarefaException("Já existe tarefa agendada na " +  requestTarefaDTO.dataTarefa() + " " +  requestTarefaDTO.horaTarefa());
        }

        Tarefa tarefa = toEntity(requestTarefaDTO);
        tarefaRepository.save(tarefa);

        return new ResponseTarefaDTO(
                tarefa.getId(),
                tarefa.getNomeTarefa(),
                tarefa.getDescricaoTarefa(),
                tarefa.isStatus(),
                tarefa.getDataTarefa(),
                tarefa.getHoraTarefa()
        );
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
        if(tarefaRepository.existsByDataTarefaAndHoraTarefa(tarefa.dataTarefa(), tarefa.horaTarefa()) &&
                !tarefaRepository.existsByIdAndDataTarefaAndHoraTarefa(id,  tarefa.dataTarefa(), tarefa.horaTarefa())) {
            throw new ConflitoTarefaException("Já existe tarefa agendada na " +  tarefa.dataTarefa() + " " +  tarefa.horaTarefa());
        }

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
        try {
            tarefaRepository.deleteById(id);
        }
        catch (TarefaNotFoundException e){
            throw new TarefaNotFoundException("Tarefa com id " + id + "não foi encontrado");
        }
    }

    //Atualizar status tarefa
    public boolean atualzizarStatusTarefa(AtualizarTarefaStatusDTO status){
        if(status.status().describeConstable().isEmpty() ) {
            throw new StatusTarefaException("Não é possivel atualizar status tarefa");
        }

        var tarefaAtual = tarefaRepository.findById(status.id())
                .orElseThrow( () -> new TarefaNotFoundException("Tarefa não encontrada!")
                );

        tarefaAtual.setStatus(status.status());
        tarefaRepository.save(tarefaAtual);
        return tarefaAtual.isStatus();
    }


}
