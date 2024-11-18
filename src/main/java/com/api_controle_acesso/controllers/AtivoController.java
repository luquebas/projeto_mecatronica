package com.api_controle_acesso.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.api_controle_acesso.DTOs.AtivoDTO.AtivoPostDTO;
import com.api_controle_acesso.DTOs.AtivoDTO.AtivoPutDTO;
import com.api_controle_acesso.DTOs.AtivoDTO.AtivoReturnGetDTO;
import com.api_controle_acesso.services.AtivoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ativo")
public class AtivoController {

    @Autowired
    private AtivoService ativoService;

    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> criarAtivo(@RequestBody @Valid AtivoPostDTO ativoPostDTO, UriComponentsBuilder uriComponentsBuilder) {
        var ativo = ativoService.criarAtivo(ativoPostDTO);
        var uri = uriComponentsBuilder.path("ativo/{id}").buildAndExpand(ativo.getId()).toUri();

        return ResponseEntity.created(uri).body(new AtivoReturnGetDTO(ativo));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> visualizarAtivo(@PathVariable Long id) {
        
        var ativo = ativoService.visualizarAtivo(id);
        return ResponseEntity.ok().body(new AtivoReturnGetDTO(ativo));
    }
    
    @GetMapping("/subconjuntos/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> visualizarSubconjuntosPeloAtivo(@PathVariable Long id) {
        
        var subconjuntos = ativoService.visualizarSubconjuntos(id);
        return ResponseEntity.ok().body(subconjuntos);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<AtivoReturnGetDTO>> getAtivos(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {

        return ResponseEntity.ok(ativoService.visualizarAtivos(pageable));
    }

    @PutMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> atualizarAtivo(@RequestBody @Valid AtivoPutDTO ativoPutDTO) {

        var setor = ativoService.visualizarAtivo(ativoPutDTO.id());
        setor.update(ativoPutDTO);

        return ResponseEntity.ok().body(new AtivoReturnGetDTO(setor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteAtivo(@PathVariable Long id) {
        ativoService.deleteAtivo(id);

        return ResponseEntity.noContent().build();
    } 
}
