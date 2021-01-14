package hwm.dao;

import hwm.domain.WarEntity;
import hwm.domain.WarHistoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WarHistoryEntityDao extends PagingAndSortingRepository<WarHistoryEntity, UUID> {
	WarHistoryEntity findTopByWarEntityOrderByCreatedAtDesc(WarEntity warEntity);
}
