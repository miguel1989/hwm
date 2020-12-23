package hwm.dto;

import hwm.enums.TeamType;
import hwm.enums.WarType;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WarBean {
	public WarType type;
	public BoardBean boardBean;

	public TeamBean redTeam = new TeamBean(TeamType.RED);
	public TeamBean blueTeam = new TeamBean(TeamType.BLUE);

	public WarBean(WarType type) {
		this.type = type;
	}

	public void beforeBattlePreparation() {
		redTeam.beforeBattlePreparation(boardBean);
		blueTeam.beforeBattlePreparation(boardBean);
	}
}
