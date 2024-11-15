package com.api_controle_acesso.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api_controle_acesso.DTOs.AtivoDTO.AtivoPostDTO;
import com.api_controle_acesso.models.Ativo;
import com.api_controle_acesso.repositories.AtivoRepository;

@Service
public class AtivoService {
    
    @Autowired
    private AtivoRepository ativoRepository;

    public Ativo criarAtivo(AtivoPostDTO ativoPostDTO) {
        
        var ativo = new Ativo(ativoPostDTO);

        return ativoRepository.save(ativo);
    }

    public Ativo visualizarAtivo(Long id) {
        return ativoRepository.getReferenceById(id);
    }

    public void deleteAtivo(Long id) {
        var ativo = ativoRepository.getReferenceById(id);
        try {
            ativoRepository.delete(ativo);
        } catch (Exception e) {
            throw new RuntimeException("Não foi Possível excluir o Ativo!");
        }
    }
}
