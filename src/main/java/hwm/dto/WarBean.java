package hwm.dto;

import hwm.enums.TeamType;
import hwm.enums.WarType;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class WarBean {
	public WarType type;
	public BoardBean boardBean;

	public TeamBean redTeam = new TeamBean(TeamType.RED);
	public TeamBean blueTeam = new TeamBean(TeamType.BLUE);

	public int currAtbSecond = 0;

	public WarBean(WarType type) {
		this.type = type;
	}

	public void beforeBattlePreparation() {
		redTeam.beforeBattlePreparation(boardBean);
		blueTeam.beforeBattlePreparation(boardBean);
	}

	public List<WarCreatureBean> allCreatures() {
		List<WarCreatureBean> creatures = new ArrayList<>(redTeam.allCreatures());
		creatures.addAll(blueTeam.allCreatures());
		return creatures;
	}

	public void atbTick() {

	}
}
