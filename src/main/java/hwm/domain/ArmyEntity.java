package hwm.domain;

import hwm.enums.Faction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "armies")
@NoArgsConstructor
public class ArmyEntity extends BaseEntity {

	@Getter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player_id")
	PlayerEntity playerEntity;

	@Getter
	@Column(name = "faction")
	Faction faction;

	@Getter
	@Setter
	@Column(name = "level_1_count")
	int level1Count;

	@Getter
	@Setter
	@Column(name = "level_2_count")
	int level2Count;

	public ArmyEntity(PlayerEntity playerEntity, Faction faction) {
		this.playerEntity = playerEntity;
		this.faction = faction;
	}
}
