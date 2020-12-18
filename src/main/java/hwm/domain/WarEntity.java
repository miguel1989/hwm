package hwm.domain;

import hwm.enums.WarStatus;
import hwm.enums.WarType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "wars")
@NoArgsConstructor
public class WarEntity extends BaseEntity {

	@Getter
	@Setter
	@Column(name = "type")
	WarType type;

	@Getter
	@Setter
	@Column(name = "type")
	WarStatus status = WarStatus.CREATED;


}
