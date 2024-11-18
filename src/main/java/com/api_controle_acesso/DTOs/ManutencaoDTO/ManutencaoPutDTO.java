package com.api_controle_acesso.DTOs.ManutencaoDTO;

import java.time.LocalDate;

import com.api_controle_acesso.models.Ativo;
import com.api_controle_acesso.models.Setor;
import com.api_controle_acesso.models.Subconjunto;
import com.api_controle_acesso.models.Usuario;
import com.api_controle_acesso.models.enums.TipoManutencao;

import jakarta.validation.constraints.NotNull;

public record ManutencaoPutDTO(@NotNull Long id, 
                               TipoManutencao tipoManutencao,
                               Ativo ativo, 
                               String descricao, 
                               boolean concluida, 
                               Setor setor, 
                               Usuario usuario, 
                               LocalDate prazo, 
                               Subconjunto subconjunto ) {
    
}
