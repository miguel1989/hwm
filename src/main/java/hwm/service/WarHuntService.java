package hwm.service;

import hwm.creatures.Peasant;
import hwm.dao.BotPlayerDao;
import hwm.dao.PlayerEntityDao;
import hwm.dao.WarEntityDao;
import hwm.dao.WarHistoryEntityDao;
import hwm.domain.*;
import hwm.dto.BoardBean;
import hwm.dto.WarBean;
import hwm.dto.WarPlayerBean;
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
		warEntity.addHistory(initialJson);
		warBean.redTeam.players.forEach(it -> warEntity.addRedTeamPlayer(it.id));
		warBean.blueTeam.players.forEach(it -> warEntity.addBlueTeamPlayer(it.id));
		warEntityDao.save(warEntity);

		return warEntity.id().toString();
	}

	public void start(String warId) {
		WarEntity warEntity = warEntityDao.findById(UUID.fromString(warId)).get();

		WarHistoryEntity lastHistoryEntry = warHistoryEntityDao.findTopByWarEntityOrderByCreatedAtDesc(warEntity);

		WarBean warBean = jacksonJsonSerializer.restoreWar(lastHistoryEntry.getJson());
		warEntity.start();
		warBean.findNextCreaturesToMove();
		warEntity.addHistory(jacksonJsonSerializer.toJson(warBean));
		warEntityDao.save(warEntity);
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
