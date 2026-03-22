package com.nveladio.proposta_app.listener;

import com.nveladio.proposta_app.entity.Proposta;
import com.nveladio.proposta_app.mapper.PropostaMapper;
import com.nveladio.proposta_app.repository.PropostaRepository;
import com.nveladio.proposta_app.service.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private WebSocketService webSocketService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaConcluida(Proposta propostaRecebida) {
        propostaRepository.findById(propostaRecebida.getId()).ifPresent(proposta -> {
            proposta.setAprovada(propostaRecebida.getAprovada());
            proposta.setObservacao(propostaRecebida.getObservacao());
            proposta.setIntegrada(propostaRecebida.getIntegrada());
            atualizarProposta(proposta);
            webSocketService.notificar(PropostaMapper.INSTANCE.convertEntityToDto(proposta));
        });
    }

    private void atualizarProposta(Proposta proposta) {
        propostaRepository.atualizarProposta(proposta.getId(), proposta.getAprovada(), proposta.getObservacao());
    }
}
