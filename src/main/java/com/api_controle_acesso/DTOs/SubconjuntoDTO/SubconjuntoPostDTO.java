package com.api_controle_acesso.DTOs.SubconjuntoDTO;
import com.api_controle_acesso.models.Ativo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubconjuntoPostDTO(@NotBlank String nome, @NotNull Ativo ativo) {
    
}
