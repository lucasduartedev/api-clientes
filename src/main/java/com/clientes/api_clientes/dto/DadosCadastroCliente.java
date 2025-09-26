package com.clientes.api_clientes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroCliente(
    @NotBlank
    String nome,
    @NotBlank
    @Pattern(regexp = "\\d{11,14}")
    String cpf
    ) {

}
