package com.api_controle_acesso.DTOs.UsuarioDTO;
import com.api_controle_acesso.DTOs.SetorDTO.SetorReturnDTO;
import com.api_controle_acesso.models.Usuario;
import com.api_controle_acesso.models.enums.Role;

public record UsuarioReturnDTO(Long id, String nome, SetorReturnDTO setor, String email, Role role) {
    
    public UsuarioReturnDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), new SetorReturnDTO(usuario.getSetor()), usuario.getEmail(), usuario.getRole());
    }
}
