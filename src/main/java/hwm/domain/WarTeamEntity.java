package hwm.domain;

import hwm.enums.PlayerType;
import hwm.enums.TeamType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "wars_team")
@NoArgsConstructor
@AllArgsConstructor
public class WarTeamEntity extends BaseEntity {
	@Getter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "war_id")
	WarEntity warEntity;

	@Getter
	@Column(name = "type")
	TeamType type;

	@Getter
	@Column(name = "player_type")
	PlayerType playerType;

	@Getter
	@Column(name = "player_id")
	String playerId;
}
