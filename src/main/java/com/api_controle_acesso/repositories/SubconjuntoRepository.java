package com.api_controle_acesso.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api_controle_acesso.models.Subconjunto;

@Repository
public interface SubconjuntoRepository extends JpaRepository<Subconjunto, Long> {
    
    @Query("SELECT s FROM Subconjunto s WHERE s.ativo.id = :ativoId")
    List<Subconjunto> findSubconjuntosByAtivoId(Long ativoId);
}
