package com.nveladio.proposta_app.service;

import com.nveladio.proposta_app.dto.PropostaRequestDto;
import com.nveladio.proposta_app.dto.PropostaResponseDto;
import com.nveladio.proposta_app.entity.Proposta;
import com.nveladio.proposta_app.mapper.PropostaMapper;
import com.nveladio.proposta_app.repository.PropostaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PropostaService {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private NotificacaoRabbitService notificacaoRabbitService;

    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchange;

    public PropostaResponseDto criar(PropostaRequestDto requestDto) {
        Proposta proposta = PropostaMapper.INSTANCE.convertDtoToProposta(requestDto);

        propostaRepository.save(proposta);

        PropostaResponseDto response = PropostaMapper.INSTANCE.convertEntityToDto(proposta);
        notificarRabbitMQ(response);

        return response;
    }

    private void notificarRabbitMQ(PropostaResponseDto propostaResponseDto) {
        try {
            propostaResponseDto.setIntegrada(true);
            notificacaoRabbitService.notificar(propostaResponseDto, exchange);
        } catch (RuntimeException ex) {
            log.error("Erro ao enviar mensagem para RabbitMQ: {}", ex.getMessage());
            Proposta proposta = propostaRepository.findById(propostaResponseDto.getId()).orElseThrow();
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);
        }
    }


    public List<PropostaResponseDto> obterProposta() {
        return PropostaMapper.INSTANCE.convertListEntityToListDto(propostaRepository.findAll());
    }
}
