package ru.neoflex.deal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.deal.exception.NotFoundException;
import ru.neoflex.deal.logging.Logging;
import ru.neoflex.deal.model.entity.Statement;
import ru.neoflex.deal.model.enums.ChangeType;
import ru.neoflex.deal.model.enums.Status;
import ru.neoflex.deal.model.jsonb.StatusHistoryElement;
import ru.neoflex.deal.repository.StatementRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Logging
@Service
@Transactional
@RequiredArgsConstructor
public class StatementService {
    private final StatementRepository statementRepository;

    public void setStatus(Statement statement, Status status, ChangeType changeType) {
        statement.setStatus(status);
        StatusHistoryElement statusHistoryElement = StatusHistoryElement.builder()
                .status(status)
                .changeType(changeType)
                .time(LocalDateTime.now())
                .build();
        List<StatusHistoryElement> statusHistory = statement.getStatusHistory();
        statusHistory.add(statusHistoryElement);
    }

    public Statement findStatementById(UUID uuid) {
        return statementRepository.findById(uuid).orElseThrow(
                () -> new NotFoundException(String.format("Заявка с id %s не найдена", uuid)));
    }

    public Statement save(Statement statement) {
        return statementRepository.save(statement);
    }
}
