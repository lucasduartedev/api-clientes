package com.clientes.api_clientes.dto;

import com.clientes.api_clientes.entity.Cliente;

public record DadosListagemClientes(Long id, String nome) {

    public DadosListagemClientes(Cliente cliente) {
        this(cliente.getId(),cliente.getNome());
    }

}
