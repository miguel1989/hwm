package hwm.dao;

import hwm.domain.WarActionLogEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface WarActionLogEntityDao extends PagingAndSortingRepository<WarActionLogEntity, UUID> {
	WarActionLogEntity findTopByWarIdOrderByCreatedAtDesc(UUID warId);

	Collection<WarActionLogEntity> findAllByWarIdOrderByCreatedAt(UUID warId);
}
