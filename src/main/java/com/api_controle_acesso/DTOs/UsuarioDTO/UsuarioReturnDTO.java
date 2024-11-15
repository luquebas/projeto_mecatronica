package com.api_controle_acesso.DTOs.UsuarioDTO;
import com.api_controle_acesso.DTOs.SetorDTO.SetorReturnDTO;
import com.api_controle_acesso.models.Usuario;

public record UsuarioReturnDTO(Long id, String nome, SetorReturnDTO setor, String email) {
    
    public UsuarioReturnDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), new SetorReturnDTO(usuario.getSetor()), usuario.getEmail());
    }
}
