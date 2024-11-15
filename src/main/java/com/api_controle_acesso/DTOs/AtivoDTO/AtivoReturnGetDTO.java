package com.api_controle_acesso.DTOs.AtivoDTO;
import com.api_controle_acesso.models.Ativo;
import com.api_controle_acesso.models.enums.Criticidade;

public record AtivoReturnGetDTO(Long id, String nome, Criticidade criticidade) {
    
    public AtivoReturnGetDTO(Ativo ativo) {
        this(ativo.getId(), ativo.getNome(), ativo.getCriticidade());
    }
}
