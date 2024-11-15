package com.api_controle_acesso.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.api_controle_acesso.models.Ativo;

public interface AtivoRepository extends JpaRepository<Ativo, Long> {

}
