package com.api_controle_acesso.DTOs.AtivoDTO;
import com.api_controle_acesso.models.Ativo;
import com.api_controle_acesso.models.enums.Criticidade;

public record AtivoReturnDTO(Long id, String nome, Criticidade criticidade) {
    
    public AtivoReturnDTO(Ativo ativo) {
        this(ativo.getId(), ativo.getNome(), ativo.getCriticidade());
    }
}
