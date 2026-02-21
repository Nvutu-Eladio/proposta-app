package com.nveladio.proposta_app.repository;

import com.nveladio.proposta_app.entity.Proposta;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PropostaRepository extends CrudRepository<Proposta, UUID> {
}
