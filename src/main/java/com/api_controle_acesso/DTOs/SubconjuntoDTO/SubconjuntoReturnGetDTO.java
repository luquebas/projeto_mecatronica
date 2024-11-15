package com.api_controle_acesso.DTOs.SubconjuntoDTO;
import com.api_controle_acesso.DTOs.AtivoDTO.AtivoReturnDTO;
import com.api_controle_acesso.models.Subconjunto;

public record SubconjuntoReturnGetDTO(Long id, String nome, AtivoReturnDTO ativo) {
    
     public SubconjuntoReturnGetDTO(Subconjunto subconjunto) {
        this(subconjunto.getId(), subconjunto.getNome(), new AtivoReturnDTO(subconjunto.getAtivo()));
    }
}
