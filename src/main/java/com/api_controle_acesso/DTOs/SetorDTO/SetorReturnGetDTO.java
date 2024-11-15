package com.api_controle_acesso.DTOs.SetorDTO;
import java.util.List;
import com.api_controle_acesso.DTOs.UsuarioDTO.UsuarioReturnDTO;
import com.api_controle_acesso.models.Setor;

public record SetorReturnGetDTO(Long id, String nome, List<UsuarioReturnDTO> usuarios) {
    
    public SetorReturnGetDTO(Setor setor) {
        this(setor.getId(), setor.getNome(), setor.getUsuarios().stream().map(UsuarioReturnDTO::new).toList());
    }
}
