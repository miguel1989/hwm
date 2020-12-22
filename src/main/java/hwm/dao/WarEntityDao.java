package hwm.dao;

import hwm.domain.WarEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WarEntityDao extends PagingAndSortingRepository<WarEntity, UUID> {
}
