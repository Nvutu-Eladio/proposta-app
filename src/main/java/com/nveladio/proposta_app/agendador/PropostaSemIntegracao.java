package com.nveladio.proposta_app.agendador;

import com.nveladio.proposta_app.dto.PropostaResponseDto;
import com.nveladio.proposta_app.mapper.PropostaMapper;
import com.nveladio.proposta_app.repository.PropostaRepository;
import com.nveladio.proposta_app.service.NotificacaoRabbitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
@Slf4j
@RequiredArgsConstructor
public class PropostaSemIntegracao {

    private final PropostaRepository propostaRepository;

    private final NotificacaoRabbitService notificacaoRabbitService;

    @Value("${rabbitmq.propostapendente.exchange}")
    private String exchange;

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void buscarPropostasSemIntegracao() {
        propostaRepository.findAllByIntegradaIsFalse().forEach(proposta -> {
            PropostaResponseDto propostaResponseDto = PropostaMapper.INSTANCE.convertEntityToDto(proposta);
            try {
                propostaResponseDto.setIntegrada(true);
                notificacaoRabbitService.notificar(propostaResponseDto, exchange);
                atualizarProposta(propostaResponseDto);
            } catch (RuntimeException ex) {
                log.error("Erro ao enviar mensagem para RabbitMQ: {}", ex.getMessage());
            }

        });
    }

    private void atualizarProposta(PropostaResponseDto propostaResponseDto) {
        propostaRepository.findById(propostaResponseDto.getId()).ifPresent(proposta -> {
            proposta.setIntegrada(true);
            propostaRepository.save(proposta);
        });
    }

}
