package hwm.dto;

import hwm.enums.TeamType;

import java.util.ArrayList;
import java.util.List;

public class TeamBean {
	public final TeamType type;
	public List<WarPlayerBean> players = new ArrayList<>(4);

	public TeamBean(TeamType type) {
		this.type = type;
	}

	public void addPlayer(WarPlayerBean warPlayerBean) {
		players.add(warPlayerBean);
	}

	public void beforeBattlePreparation(BoardBean boardBean) {
		this.players.forEach(it -> it.defaultPositionForCreatures(type, boardBean));
	}
}
