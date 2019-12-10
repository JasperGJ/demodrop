package com.hexagon.demodrop.repository;

import com.hexagon.demodrop.model.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TokenRepository extends CrudRepository<Token, UUID> {
}
