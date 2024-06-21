package com.mballem.demoparkapi.web.controller;

import com.mballem.demoparkapi.entity.Vaga;
import com.mballem.demoparkapi.service.VagaService;
import com.mballem.demoparkapi.web.dto.VagaCreateDto;
import com.mballem.demoparkapi.web.dto.VagaResponseDto;
import com.mballem.demoparkapi.web.dto.mapper.VagaMapper;
import com.mballem.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Controlador REST para operações relacionadas ao recurso de vagas.
 */
@Tag(name = "Vagas", description = "Contém todas as operações relativas ao recurso de uma vaga")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/vagas")
public class VagaController {

    private final VagaService vagaService;

    /**
     * Endpoint para criar uma nova vaga.
     *
     * @param dto Dados da vaga a ser criada
     * @return ResponseEntity sem conteúdo com status 201 se criada com sucesso
     */
    @Operation(summary = "Criar uma nova vaga", description = "Recurso para criar uma nova vaga." +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL do recurso criado")),
                    @ApiResponse(responseCode = "409", description = "Vaga já cadastrada",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid VagaCreateDto dto) {
        // Converte o DTO de criação de vaga para a entidade Vaga
        Vaga vaga = VagaMapper.toVaga(dto);
        // Chama o serviço para salvar a vaga no banco de dados
        vagaService.salvar(vaga);
        // Constrói a URI para a localização do recurso criado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(vaga.getCodigo())
                .toUri();
        // Retorna uma resposta com status 201 (Created) e a URI do recurso criado no cabeçalho Location
        return ResponseEntity.created(location).build();
    }

    /**
     * Endpoint para localizar uma vaga pelo código.
     *
     * @param codigo Código da vaga a ser localizada
     * @return ResponseEntity com o DTO da vaga encontrada
     */
    @Operation(summary = "Localizar uma vaga", description = "Recurso para retornar uma vaga pelo seu código" +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = VagaResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Vaga não localizada",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @GetMapping("/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VagaResponseDto> getByCodigo(@PathVariable String codigo) {
        // Busca a vaga pelo código usando o serviço
        Vaga vaga = vagaService.buscarPorCodigo(codigo);
        // Retorna a vaga encontrada como DTO no ResponseEntity com status 200 (OK)
        return ResponseEntity.ok(VagaMapper.toDto(vaga));
    }
}
