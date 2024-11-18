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
import com.api_controle_acesso.DTOs.SubconjuntoDTO.SubconjuntoPostDTO;
import com.api_controle_acesso.DTOs.SubconjuntoDTO.SubconjuntoPutDTO;
import com.api_controle_acesso.DTOs.SubconjuntoDTO.SubconjuntoReturnGetDTO;
import com.api_controle_acesso.services.SubconjuntoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/subconjunto")
public class SubconjuntoController {
    
    @Autowired
    private SubconjuntoService subconjuntoService;

    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> criarSubconjunto(@RequestBody @Valid SubconjuntoPostDTO subconjuntoPostDTO, UriComponentsBuilder uriComponentsBuilder) {
        var ativo = subconjuntoService.criarSubconjunto(subconjuntoPostDTO);
        var uri = uriComponentsBuilder.path("subconjunto/{id}").buildAndExpand(ativo.getId()).toUri();

        return ResponseEntity.created(uri).body(new SubconjuntoReturnGetDTO(ativo));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INTERMEDIATE')")
    public ResponseEntity<Object> visualizarSubconjunto(@PathVariable Long id) {
        
        var ativo = subconjuntoService.visualizarSubconjunto(id);
        return ResponseEntity.ok().body(new SubconjuntoReturnGetDTO(ativo));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<SubconjuntoReturnGetDTO>> getSubconjuntos(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {

        return ResponseEntity.ok(subconjuntoService.visualizarSubconjuntos(pageable));
    }

    @PutMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INTERMEDIATE')")
    public ResponseEntity<Object> atualizarSubconjunto(@RequestBody @Valid SubconjuntoPutDTO subconjuntoPutDTO) {

        var subconjunto = subconjuntoService.visualizarSubconjunto(subconjuntoPutDTO.id());
        subconjunto.update(subconjuntoPutDTO);

        return ResponseEntity.ok().body(new SubconjuntoReturnGetDTO(subconjunto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteSubconjunto(@PathVariable Long id) {
        subconjuntoService.deleteSubconjunto(id);

        return ResponseEntity.noContent().build();
    } 
}