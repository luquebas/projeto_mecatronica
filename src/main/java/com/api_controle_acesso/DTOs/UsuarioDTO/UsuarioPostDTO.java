package com.api_controle_acesso.DTOs.UsuarioDTO;
import com.api_controle_acesso.models.Setor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioPostDTO(@NotBlank String nome, @NotNull Setor setor, @NotBlank String email, @NotBlank String senha) {

}
