package hwm.dto;

import hwm.enums.TeamType;
import hwm.enums.WarType;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class WarBean {
	public WarType type;
	public BoardBean boardBean;

	public int turnTimeOut = 0;
	public int preparationTimeOut = 0;

	public TeamBean redTeam = new TeamBean(this, TeamType.RED);
	public TeamBean blueTeam = new TeamBean(this, TeamType.BLUE);

	public int currAtbSecond = 0;
	public List<WarCreatureBean> nextCreaturesToMove;
	public LocalDateTime lastActionTimeStamp;

	public WarBean(WarType type) {
		this.type = type;
	}

	public void beforeBattlePreparation() {
		redTeam.beforeBattlePreparation();
		blueTeam.beforeBattlePreparation();

		this.allCreatures().forEach(it -> {
			//todo add hero separately
			if (!it.isHero) {
				this.boardBean.addCreature(it);
			}
		});
	}

	public List<WarCreatureBean> allCreatures() {
		List<WarCreatureBean> creatures = new ArrayList<>(redTeam.allCreatures());
		creatures.addAll(blueTeam.allCreatures());
		return creatures;
	}

	public void findNextCreaturesToMove() {
		do {
			nextCreaturesToMove = atbTick();
		} while (nextCreaturesToMove.isEmpty());
		lastActionTimeStamp = LocalDateTime.now();
	}

	private List<WarCreatureBean> atbTick() {
		currAtbSecond++;
		List<WarCreatureBean> allCreatures = this.allCreatures();
		allCreatures.forEach(WarCreatureBean::atbTick);
		return allCreatures.stream()
				.filter(WarCreatureBean::isReadyToMakeTurn)
				.sorted(new WarCreatureAtbComparator())
				.collect(Collectors.toList());
	}
}
