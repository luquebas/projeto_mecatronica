package com.api_controle_acesso.DTOs.SetorDTO;
import jakarta.validation.constraints.NotBlank;

public record SetorPostDTO(@NotBlank String nome) {
} 
