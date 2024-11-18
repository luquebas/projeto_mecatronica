package com.api_controle_acesso.DTOs.UsuarioDTO;
import com.api_controle_acesso.models.Usuario;

public record UsuarioReturnGetDTO(Long id, String nome, String email) {
    
    public UsuarioReturnGetDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
    
}
