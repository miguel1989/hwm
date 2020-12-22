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
		warPlayerBean.teamType = type;
		players.add(warPlayerBean);
	}

	public void beforeBattlePreparation() {
		this.players.forEach(WarPlayerBean::defaultPositionForCreatures);
	}
}
