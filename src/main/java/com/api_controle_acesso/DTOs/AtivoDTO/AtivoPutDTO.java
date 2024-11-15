package com.api_controle_acesso.DTOs.AtivoDTO;
import com.api_controle_acesso.models.enums.Criticidade;
import jakarta.validation.constraints.NotNull;
public record AtivoPutDTO(@NotNull Long id, String nome, Criticidade criticidade) {
    
}
