package com.api_controle_acesso.DTOs.UsuarioDTO;
import com.api_controle_acesso.models.Setor;

import jakarta.validation.constraints.NotNull;

public record UsuarioPutDTO(@NotNull Long id, String email, Setor setor, String nome) {
    
}
