package com.todolist.todolist.exceptions;

public class ErrorResponse {
    private int status;
    private String mensagem;
    private String metodo;
    private String caminho;

    public ErrorResponse(int status, String mensagem, String metodo, String caminho){
        this.status = status;
        this.mensagem = mensagem;
        this.metodo = metodo;
        this.caminho = caminho;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
