package com.nveladio.proposta_app.controller;

import com.nveladio.proposta_app.dto.PropostaRequestDto;
import com.nveladio.proposta_app.dto.PropostaResponseDto;
import com.nveladio.proposta_app.service.PropostaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proposta")
@RequiredArgsConstructor
public class PropostaController {

    private PropostaService propostaService;

    @PostMapping
    public ResponseEntity<PropostaResponseDto> criar(@RequestBody PropostaRequestDto requestDto) {
        PropostaResponseDto response = propostaService.criar(requestDto);
        return ResponseEntity.ok(response);
    }

}
