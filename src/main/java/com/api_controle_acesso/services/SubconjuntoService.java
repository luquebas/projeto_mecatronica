package com.api_controle_acesso.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api_controle_acesso.DTOs.SubconjuntoDTO.SubconjuntoPostDTO;
import com.api_controle_acesso.models.Subconjunto;
import com.api_controle_acesso.repositories.SubconjuntoRepository;

@Service
public class SubconjuntoService {
    
    @Autowired
    private SubconjuntoRepository subconjuntoRepository;

    public Subconjunto criarSubconjunto(SubconjuntoPostDTO subconjuntoPostDTO) {
        
        var conjunto = new Subconjunto(subconjuntoPostDTO);

        return subconjuntoRepository.save(conjunto);
    }

    public Subconjunto visualizarSubconjunto(Long id) {
        return subconjuntoRepository.getReferenceById(id);
    }

    public void deleteSubconjunto(Long id) {
        var ativo = subconjuntoRepository.getReferenceById(id);
        try {
            subconjuntoRepository.delete(ativo);
        } catch (Exception e) {
            throw new RuntimeException("Não foi Possível excluir o Subconjunto!");
        }
    }
}
