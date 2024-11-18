package com.api_controle_acesso.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.api_controle_acesso.DTOs.AtivoDTO.AtivoPostDTO;
import com.api_controle_acesso.DTOs.AtivoDTO.AtivoReturnGetDTO;
import com.api_controle_acesso.models.Ativo;
import com.api_controle_acesso.models.Subconjunto;
import com.api_controle_acesso.repositories.AtivoRepository;
import com.api_controle_acesso.repositories.SubconjuntoRepository;

@Service
public class AtivoService {
    
    @Autowired
    private AtivoRepository ativoRepository;

    @Autowired
    private SubconjuntoRepository subconjuntoRepository;

    public Ativo criarAtivo(AtivoPostDTO ativoPostDTO) {
        
        var ativo = new Ativo(ativoPostDTO);

        return ativoRepository.save(ativo);
    }

    public Ativo visualizarAtivo(Long id) {
        return ativoRepository.getReferenceById(id);
    }

    public List<Subconjunto> visualizarSubconjuntos(Long id) {
        return subconjuntoRepository.findSubconjuntosByAtivoId(id);
    }

    public Page<AtivoReturnGetDTO> visualizarAtivos(Pageable pageable) {
        var page = ativoRepository.findAll(pageable).map(AtivoReturnGetDTO::new);
        return page;
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
