package hwm.dao;

import hwm.domain.PlayerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerEntityDao extends PagingAndSortingRepository<PlayerEntity, UUID> {
}
