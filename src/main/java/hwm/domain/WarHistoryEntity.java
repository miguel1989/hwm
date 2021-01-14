package hwm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "wars_history")
@NoArgsConstructor
@AllArgsConstructor
public class WarHistoryEntity extends BaseEntity {

	@Getter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "war_id")
	WarEntity warEntity;

	@Getter
	@Setter
	@Column(name = "json")
	@Lob
	@Type(type = "org.hibernate.type.TextType")
	String json;
}
