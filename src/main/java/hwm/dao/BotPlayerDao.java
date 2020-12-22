package hwm.dao;

import hwm.domain.BotPlayerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BotPlayerDao extends PagingAndSortingRepository<BotPlayerEntity, UUID> {
}
