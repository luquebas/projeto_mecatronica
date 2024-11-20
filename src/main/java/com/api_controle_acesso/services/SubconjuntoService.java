package com.api_controle_acesso.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.api_controle_acesso.DTOs.SubconjuntoDTO.SubconjuntoPostDTO;
import com.api_controle_acesso.DTOs.SubconjuntoDTO.SubconjuntoReturnGetDTO;
import com.api_controle_acesso.models.Ativo;
import com.api_controle_acesso.models.Subconjunto;
import com.api_controle_acesso.repositories.SubconjuntoRepository;

@Service
public class SubconjuntoService {
    
    @Autowired
    private SubconjuntoRepository subconjuntoRepository;

    public Subconjunto criarSubconjunto(SubconjuntoPostDTO subconjuntoPostDTO, Ativo ativo) {
        
        var subconjunto = new Subconjunto(subconjuntoPostDTO);
        subconjunto.setAtivo(ativo);

        return subconjuntoRepository.save(subconjunto);
    }

    public Subconjunto visualizarSubconjunto(Long id) {
        return subconjuntoRepository.getReferenceById(id);
    }

    public Page<SubconjuntoReturnGetDTO> visualizarSubconjuntos(Pageable pageable) {
        var page = subconjuntoRepository.findAll(pageable).map(SubconjuntoReturnGetDTO::new);
        return page;
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
