package com.nveladio.proposta_app.service;

import com.nveladio.proposta_app.dto.PropostaRequestDto;
import com.nveladio.proposta_app.dto.PropostaResponseDto;
import com.nveladio.proposta_app.entity.Proposta;
import com.nveladio.proposta_app.mapper.PropostaMapper;
import com.nveladio.proposta_app.repository.PropostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropostaService {

    private PropostaRepository propostaRepository;

    public PropostaResponseDto criar(PropostaRequestDto requestDto) {
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(requestDto);

        propostaRepository.save(proposta);

        return PropostaMapper.INSTANCE.convertEntityToDto(proposta);
    }

}
