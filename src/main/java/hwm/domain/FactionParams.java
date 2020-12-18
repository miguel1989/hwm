package hwm.domain;

import hwm.game.enums.Faction;

import java.util.HashMap;
import java.util.Map;

public class FactionParams {
	public static final Map<Faction, BaseParams> map = new HashMap<>(Faction.values().length);

	static {
		BaseParams knightParams = new BaseParams(0, 1, 0, 0, 0, 0, 1);
		BaseParams necroParams = new BaseParams(0, 0, 0, 1, 0, 0, 0);
		BaseParams elfParams = new BaseParams(0, 0, 0, 0, 3, 0, 1);
		BaseParams darkElfParams = new BaseParams(0, 0, 0, 1, 0, 0, 1);
		BaseParams mageParams = new BaseParams(0, 0, 0, 1, 0, 0, 1);

		map.put(Faction.Knight, knightParams);
		map.put(Faction.Necro, necroParams);
		map.put(Faction.Elf, elfParams);
		map.put(Faction.DarkElf, darkElfParams);
		map.put(Faction.Mage, mageParams);
	}
}
