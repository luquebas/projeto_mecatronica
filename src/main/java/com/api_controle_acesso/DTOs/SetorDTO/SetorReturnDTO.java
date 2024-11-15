package com.api_controle_acesso.DTOs.SetorDTO;

import com.api_controle_acesso.models.Setor;

public record SetorReturnDTO(Long id, String nome) {
    
    public SetorReturnDTO(Setor setor) {
        this(setor.getId(), setor.getNome());
    }
} 
