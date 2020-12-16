package hwm.domain;

import hwm.game.enums.ArtifactType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "artifacts")
@NoArgsConstructor
public class Artifact extends BaseEntity {
	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player_entity_id")
	PlayerEntity playerEntity;

	@Getter
	@Setter
	@Column(name = "name", unique = true)
	String name;

	@Getter
	@Setter
	@Column(name = "for_level")
	int forLevel = 1;

	@Getter
	@Setter
	@Column(name = "attack")
	int attack;

	@Getter
	@Setter
	@Column(name = "defence")
	int defence;

	@Getter
	@Setter
	@Column(name = "initiative")
	int initiative;

	@Getter
	@Setter
	@Column(name = "luck")
	int luck;

	@Getter
	@Setter
	@Column(name = "morale")
	int morale;

	@Getter
	@Setter
	@Column(name = "type")
	ArtifactType type;

	//todo price

	//todo effects
}
