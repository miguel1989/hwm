package hwm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hwm.enums.TeamType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamBean {
	@JsonIgnore
	public final WarBean war;
	public final TeamType type;
	public List<WarPlayerBean> players = new ArrayList<>(4);

	public TeamBean(WarBean war, TeamType type) {
		this.war = war;
		this.type = type;
	}

	public void addPlayer(WarPlayerBean warPlayerBean) {
		warPlayerBean.war = war;
		players.add(warPlayerBean);
	}

	public void beforeBattlePreparation() {
		int availableHeightForPlayer = war.boardBean.height / this.players.size();
		for (int i = 0 ; i< this.players.size(); i++) {
			players.get(i).defaultPositionForCreatures(type, i, availableHeightForPlayer);
		}
	}

	public List<WarCreatureBean> allCreatures() {
		return this.players
				.stream()
				.flatMap(player -> player.creatures.stream())
				.collect(Collectors.toList());
	}
}
