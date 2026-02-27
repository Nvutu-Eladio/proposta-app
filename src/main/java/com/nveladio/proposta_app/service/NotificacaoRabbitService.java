package com.nveladio.proposta_app.service;

import com.nveladio.proposta_app.dto.PropostaResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificacaoRabbitService {

    private RabbitTemplate rabbitTemplate;

    public void notificar(PropostaResponseDto propostaResponseDto, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", propostaResponseDto);
    }
}
