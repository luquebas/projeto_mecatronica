package com.api_controle_acesso.DTOs.SubconjuntoDTO;

import com.api_controle_acesso.models.Ativo;

import jakarta.validation.constraints.NotNull;

public record SubconjuntoPutDTO(@NotNull Long id, String nome, Ativo ativo) {
    
}
