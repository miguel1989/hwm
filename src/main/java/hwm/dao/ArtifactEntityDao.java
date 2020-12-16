package hwm.dao;

import hwm.domain.ArtifactEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArtifactEntityDao extends PagingAndSortingRepository<ArtifactEntity, UUID> {
}
