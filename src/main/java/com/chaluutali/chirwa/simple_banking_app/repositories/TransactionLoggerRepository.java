package com.chaluutali.chirwa.simple_banking_app.repositories;

import com.chaluutali.chirwa.simple_banking_app.dto.logger.TransactionLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionLoggerRepository extends CrudRepository<TransactionLog,UUID> {
}
