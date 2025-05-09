package com.todolist.todolist.entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeTarefa;

    @Column
    private String descricaoTarefa;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private LocalDate dataTarefa;

    @Column(nullable = false)
    private LocalTime horaTarefa;

    public Tarefa() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public String getDescricaoTarefa() {
        return descricaoTarefa;
    }

    public void setDescricaoTarefa(String descricaoTarefa) {
        this.descricaoTarefa = descricaoTarefa;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getDataTarefa() {
        return dataTarefa;
    }

    public void setDataTarefa(LocalDate dataTarefa) {
        this.dataTarefa = dataTarefa;
    }

    public LocalTime getHoraTarefa() {
        return horaTarefa;
    }

    public void setHoraTarefa(LocalTime horaTarefa) {
        this.horaTarefa = horaTarefa;
    }
}
