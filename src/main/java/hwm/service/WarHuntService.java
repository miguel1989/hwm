package hwm.service;

import hwm.creatures.Peasant;
import hwm.dao.*;
import hwm.domain.*;
import hwm.dto.*;
import hwm.enums.TurnType;
import hwm.enums.WarType;
import hwm.util.JacksonJsonSerializer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
public class WarHuntService {
	private final PlayerEntityDao playerEntityDao;
	private final BotPlayerDao botPlayerDao;
	private final WarEntityDao warEntityDao;
	private final WarHistoryEntityDao warHistoryEntityDao;
	private final WarActionLogEntityDao warActionLogEntityDao;
	private final JacksonJsonSerializer jacksonJsonSerializer;

	public String create(String playerId) {
		PlayerEntity playerEntity = playerEntityDao.findById(UUID.fromString(playerId)).get();

		BotPlayerEntity botPlayer = createBot();

		WarBean warBean = new WarBean(WarType.HUNT);
		warBean.boardBean = new BoardBean(12, 12);
		WarPlayerBean warPlayerBean1 = new WarPlayerBean(playerEntity);
		WarPlayerBean warPlayerBean2 = new WarPlayerBean(botPlayer);
		warBean.redTeam.addPlayer(warPlayerBean1);
		warBean.blueTeam.addPlayer(warPlayerBean2);
		warBean.beforeBattlePreparation();

		String initialJson = jacksonJsonSerializer.toJson(warBean);

		WarEntity warEntity = new WarEntity();
		warEntity.setType(warBean.type);
		warEntity.setPreparationTimeOut(warBean.preparationTimeOut);
		warEntity.setTurnTimeOut(warBean.turnTimeOut);
//		warEntity.addHistory(initialJson);
		warBean.redTeam.players.forEach(it -> warEntity.addRedTeamPlayer(it.id));
		warBean.blueTeam.players.forEach(it -> warEntity.addBlueTeamPlayer(it.id));
		warEntityDao.save(warEntity);

		WarHistoryEntity historyEntity = new WarHistoryEntity(warEntity.id(), initialJson);
		warHistoryEntityDao.save(historyEntity);

		return warEntity.id().toString();
	}

	public void start(String warId) {
		WarEntity warEntity = warEntityDao.findById(UUID.fromString(warId)).get();

		WarHistoryEntity lastHistoryEntry = warHistoryEntityDao.findTopByWarIdOrderByCreatedAtDesc(warEntity.id());

		WarBean warBean = jacksonJsonSerializer.restoreWar(lastHistoryEntry.getJson());
		warEntity.start();
		warBean.findNextCreaturesToMove();
//		warEntity.addHistory(jacksonJsonSerializer.toJson(warBean));
		warEntityDao.save(warEntity);

		WarHistoryEntity historyEntity = new WarHistoryEntity(warEntity.id(), jacksonJsonSerializer.toJson(warBean));
		warHistoryEntityDao.save(historyEntity);
	}

	public boolean playerTurn(String warId, TurnBean turnBean) {
		WarEntity warEntity = warEntityDao.findById(UUID.fromString(warId)).get();
		WarHistoryEntity lastHistoryEntry = warHistoryEntityDao.findTopByWarIdOrderByCreatedAtDesc(warEntity.id());
		WarBean warBean = jacksonJsonSerializer.restoreWar(lastHistoryEntry.getJson());

		if (warEntity.isNotStarted()) {
			return false;
		}
		if (warBean.nextCreaturesToMove.isEmpty()) {
			return false;
		}
		boolean isNotInRedTeam = warBean.redTeam.players.stream().noneMatch(it -> it.id.equals(turnBean.playerId));
		boolean isNotInBlueTeam = warBean.blueTeam.players.stream().noneMatch(it -> it.id.equals(turnBean.playerId));
		if (isNotInRedTeam && isNotInBlueTeam) {
			return false;
		}

		//wrong creature to move
		if (!warBean.nextCreaturesToMove.get(0).id.toString().equals(turnBean.creatureId)) {
			return false;
		}
		//wrong player turn
		if (!warBean.nextCreaturesToMove.get(0).player.id.equals(turnBean.playerId)) {
			return false;
		}

		WarCreatureBean tmpCreatureForTurn = warBean.nextCreaturesToMove.remove(0);
		WarCreatureBean creatureForTurn = warBean.allCreatures().stream()
				.filter(tmpCreature -> tmpCreature.id.equals(tmpCreatureForTurn.id))
				.findFirst().get();
		//todo other checks for creature that it can make that turn

		if (TurnType.WAIT.equals(turnBean.type)) {
			creatureForTurn.await();

			warActionLogEntityDao.save(new WarActionLogEntity(warEntity.id(), jacksonJsonSerializer.toJson(turnBean)));
		}

		if (TurnType.DEFENCE.equals(turnBean.type)) {
			creatureForTurn.def();

			warActionLogEntityDao.save(new WarActionLogEntity(warEntity.id(), jacksonJsonSerializer.toJson(turnBean)));
		}

		if (TurnType.MOVE.equals(turnBean.type)) {
			//1. generate move path
			//2. make a subAction to move one by one cell
		}

		warBean.findNextCreaturesToMove();
		WarHistoryEntity historyEntity = new WarHistoryEntity(warEntity.id(), jacksonJsonSerializer.toJson(warBean));
		warHistoryEntityDao.save(historyEntity);
		return true;
	}

	private BotPlayerEntity createBot() {
		Peasant peasant = new Peasant(10);

		BotPlayerEntity botPlayer = new BotPlayerEntity();
		botPlayer.setName(peasant.name + " (" + peasant.count + ")");

		CreatureEntity creatureEntity = new CreatureEntity(botPlayer, peasant);
		botPlayer.addCreature(creatureEntity);
		botPlayerDao.save(botPlayer);

		return botPlayer;
	}
}
