package hwm.dto;

import hwm.enums.TurnType;

public class TurnBean {
	public String playerId;
	public String creatureId;
	public TurnType type;
	public Integer x;
	public Integer y;
	public Integer moveX;//used for creature melee attack
	public Integer moveY;


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

	public static TurnBean move(String playerId, String creatureId, int x, int y) {
		TurnBean turnBean = new TurnBean();
		turnBean.playerId = playerId;
		turnBean.creatureId = creatureId;
		turnBean.type = TurnType.MOVE;
		turnBean.x = x;
		turnBean.y = y;
		return turnBean;
	}
}
