package com.api_controle_acesso.DTOs.ManutencaoDTO;
import java.time.LocalDate;
import com.api_controle_acesso.models.Ativo;
import com.api_controle_acesso.models.Setor;
import com.api_controle_acesso.models.Subconjunto;
import com.api_controle_acesso.models.Usuario;
import com.api_controle_acesso.models.enums.TipoManutencao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ManutencaoPostDTO(@NotNull TipoManutencao tipoManutencao, @NotNull Ativo ativo, @NotBlank String descricao, @NotNull Setor setor, @NotNull Usuario usuario, @NotNull LocalDate prazo, Subconjunto subconjunto) {
    
}
