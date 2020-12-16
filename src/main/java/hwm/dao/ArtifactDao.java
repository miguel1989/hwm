package hwm.dao;

import hwm.domain.Artifact;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArtifactDao extends PagingAndSortingRepository<Artifact, UUID> {
}
