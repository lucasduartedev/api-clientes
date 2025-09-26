package com.clientes.api_clientes.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoCliente(
    @NotNull
    Long id,
    String nome,
    String cpf
    ) {

}
