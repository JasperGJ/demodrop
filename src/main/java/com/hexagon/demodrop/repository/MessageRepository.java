package com.hexagon.demodrop.repository;

import com.hexagon.demodrop.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository  extends CrudRepository<Message,Long> {
}
