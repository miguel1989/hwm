package hwm.domain;

import hwm.enums.ActionType;
import hwm.enums.ArtifactType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
	@Setter
	@Column(name = "type")
	ActionType type = ActionType.ACTION;

	@Getter
	@Column(name = "json")
	@Lob
	@Type(type = "org.hibernate.type.TextType")
	String json;

	public WarActionLogEntity(UUID warId, String json) {
		this.warId = warId;
		this.json = json;
	}
}
