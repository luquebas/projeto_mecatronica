package com.api_controle_acesso.DTOs.SetorDTO;
import jakarta.validation.constraints.NotNull;

public record SetorPutDTO(@NotNull Long id, String nome) {
    
}
