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

	@OneToMany(mappedBy = "warEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	final Set<WarTeamEntity> teams = new HashSet<>();

//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//	@JoinColumn(name = "war_id")
//	final Set<WarHistoryEntity> history = new HashSet<>();

	public Collection<WarTeamEntity> teams() {
		return ImmutableSet.copyOf(teams);
	}

//	public Collection<WarHistoryEntity> history() {
//		return ImmutableSet.copyOf(history);
//	}

	public void addRedTeamPlayer(String playerId) {
		WarTeamEntity warTeamEntity = new WarTeamEntity(this, TeamType.RED, PlayerType.HUMAN, playerId);
		this.teams.add(warTeamEntity);
	}

	public void addBlueTeamPlayer(String playerId) {
		WarTeamEntity warTeamEntity = new WarTeamEntity(this, TeamType.BLUE, PlayerType.BOT, playerId);
		this.teams.add(warTeamEntity);
	}

//	public void addHistory(String json) {
//		WarHistoryEntity warHistoryEntity = new WarHistoryEntity(this, json);
//		this.history.add(warHistoryEntity);
//	}

	public void start() {
		this.status = WarStatus.STARTED;
		this.startedAt = LocalDateTime.now();
	}

	public boolean isNotStarted() {
		return !WarStatus.STARTED.equals(this.status);
	}
}
