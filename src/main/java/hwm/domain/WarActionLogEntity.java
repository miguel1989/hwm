package hwm.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "wars_action_log")
@NoArgsConstructor
@AllArgsConstructor
public class WarActionLogEntity extends BaseEntity {

	@Getter
	@Column(name = "war_id")
	UUID warId;

	@Getter
	@Column(name = "json")
	@Lob
	@Type(type = "org.hibernate.type.TextType")
	String json;
}
