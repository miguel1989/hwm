package hwm.domain;

import com.datastax.driver.core.utils.UUIDs;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@Column(name = "id")
	protected UUID id = UUIDs.timeBased();

	@Column(name = "created_at")
	protected LocalTime createdAt = LocalTime.now();

	public UUID id() {
		return id;
	}

	public LocalTime createdAt() {
		return createdAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BaseEntity that = (BaseEntity) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
