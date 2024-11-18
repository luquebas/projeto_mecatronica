package com.api_controle_acesso.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api_controle_acesso.models.Ativo;

@Repository
public interface AtivoRepository extends JpaRepository<Ativo, Long> {

}
