package com.api_controle_acesso.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api_controle_acesso.models.Manutencao;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    
    @Query("SELECT m FROM Manutencao m WHERE FUNCTION('MONTH', m.prazo) = :mes AND FUNCTION('YEAR', m.prazo) = :ano")
    Page<Manutencao> findByMonthAndYear(@Param("mes") int mes, @Param("ano") int ano, Pageable pageable);

    @Query("SELECT m FROM Manutencao m WHERE m.usuario.id = :usuarioId")
    Page<Manutencao> findByUsuarioId(Long usuarioId, Pageable page);

    @Query("SELECT m FROM Manutencao m WHERE m.ativo.id = :ativoId")
    Page<Manutencao> findByAtivoId(Long ativoId, Pageable page);

    @Query("SELECT m FROM Manutencao m WHERE m.usuario.setor.id = :setorId")
    Page<Manutencao> findBySetorId(Long setorId, Pageable page);
}
