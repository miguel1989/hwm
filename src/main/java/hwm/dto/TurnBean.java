package hwm.dto;

import hwm.enums.TurnType;

public class TurnBean {
	public String playerId;
	public String creatureId;
	public TurnType type;
	public int x;
	public int y;
	public int moveX;//used for creature melee attack
	public int moveY;


	public static TurnBean await(String playerId, String creatureId) {
		TurnBean turnBean = new TurnBean();
		turnBean.playerId = playerId;
		turnBean.creatureId = creatureId;
		turnBean.type = TurnType.WAIT;
		return turnBean;
	}

	public static TurnBean defence(String playerId, String creatureId) {
		TurnBean turnBean = new TurnBean();
		turnBean.playerId = playerId;
		turnBean.creatureId = creatureId;
		turnBean.type = TurnType.DEFENCE;
		return turnBean;
	}
}
