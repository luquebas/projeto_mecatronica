package com.api_controle_acesso.DTOs.ManutencaoDTO;
import java.time.LocalDate;
import com.api_controle_acesso.DTOs.AtivoDTO.AtivoReturnDTO;
import com.api_controle_acesso.DTOs.SetorDTO.SetorReturnDTO;
import com.api_controle_acesso.DTOs.SubconjuntoDTO.SubconjuntoReturnDTO;
import com.api_controle_acesso.DTOs.UsuarioDTO.UsuarioReturnGetDTO;
import com.api_controle_acesso.models.Manutencao;
import com.api_controle_acesso.models.enums.TipoManutencao;

public record ManutencaoReturnGetDTO(Long id, 
                                     TipoManutencao tipoManutencao, 
                                     AtivoReturnDTO ativo, 
                                     String descricao, 
                                     boolean concluida, 
                                     SetorReturnDTO setor, 
                                     UsuarioReturnGetDTO usuario, 
                                     LocalDate prazo, 
                                     SubconjuntoReturnDTO subconjunto) {

     public ManutencaoReturnGetDTO(Manutencao manutencao) {
        this(manutencao.getId(),
             manutencao.getTipoManutencao(),
             new AtivoReturnDTO(manutencao.getAtivo()), 
             manutencao.getDescricao(), 
             manutencao.isConcluida(), 
             new SetorReturnDTO(manutencao.getSetor()), 
             new UsuarioReturnGetDTO(manutencao.getUsuario()), 
             manutencao.getPrazo(), 
             manutencao.getSubconjunto() != null ? new SubconjuntoReturnDTO(manutencao.getSubconjunto()) : null);

    }
}
