package com.api_controle_acesso.DTOs.AtivoDTO;
import com.api_controle_acesso.models.enums.Criticidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtivoPostDTO(@NotBlank String nome, @NotNull Criticidade criticidade) {
    
}
