package hwm.dto;

import hwm.enums.TeamType;

public class TeamBean {
	public final TeamType team;
	public final int teamOrderNum;

	public TeamBean(TeamType team, int teamOrderNum) {
		this.team = team;
		this.teamOrderNum = teamOrderNum;
	}
}
