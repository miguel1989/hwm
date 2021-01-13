package hwm.domain;

import com.google.common.collect.ImmutableSet;
import hwm.enums.PlayerType;
import hwm.enums.TeamType;
import hwm.enums.WarStatus;
import hwm.enums.WarType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
	@Column(name = "status")
	WarStatus status = WarStatus.CREATED;

	@Getter
	@Column(name = "started_at")
	LocalDateTime startedAt;

	@Getter
	@Setter
	@Column(name = "turn_timeout")
	int turnTimeOut = 0;

	@Getter
	@Setter
	@Column(name = "preparation_timeout")
	int preparationTimeOut = 0;

	//todo somekind of snapshot for the users in the battle

	@OneToMany(mappedBy = "warEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	final Set<WarTeamEntity> warTeams = new HashSet<>();

	public Collection<WarTeamEntity> warTeams() {
		return ImmutableSet.copyOf(warTeams);
	}

	public void addRedTeamPlayer(String id) {
		WarTeamEntity warTeamEntity = new WarTeamEntity(this, TeamType.RED, PlayerType.HUMAN, id);
		this.warTeams.add(warTeamEntity);
	}

	public void addBlueTeamPlayer(String id) {
		WarTeamEntity warTeamEntity = new WarTeamEntity(this, TeamType.BLUE, PlayerType.BOT, id);
		this.warTeams.add(warTeamEntity);
	}

	public void start() {
		this.status = WarStatus.STARTED;
		this.startedAt = LocalDateTime.now();
	}
}
