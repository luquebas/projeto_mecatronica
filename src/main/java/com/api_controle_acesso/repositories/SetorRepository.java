package com.api_controle_acesso.repositories;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.api_controle_acesso.models.Setor;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {
    @Query("""
            select s from Setor s
            where s.nome = :nome
            """)
    Optional<Boolean> verificarNomeExistente(String nome);
    
} 
