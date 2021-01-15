package hwm.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import hwm.dto.WarBean;
import hwm.dto.WarCreatureBean;
import hwm.dto.WarPlayerBean;
import hwm.enums.TeamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class JacksonJsonSerializer {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

	private final DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	ObjectMapper om = Jackson2ObjectMapperBuilder.json()
//			.featuresToDisable(SerializationFeature.WRITE_NULL_MAP_VALUES)
			.featuresToDisable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
			.failOnEmptyBeans(false)
			.build()
			.setDateFormat(df)
			.setSerializationInclusion(JsonInclude.Include.NON_NULL);

	public String toJson(Object object) {
		try {
			return om.writeValueAsString(object);
		} catch (Exception e) {
			LOG.error("Failed to serialize to Json", e);
		}
		return "Failed to serialize to json";
	}

	public Object fromJson(String jsonString, Class resultClass) {
		try {
			return om.readValue(jsonString, resultClass);
		} catch (Exception ex) {
			LOG.error("Failed to deserialize from Json {}", jsonString, ex);
		}
		return null;
	}

	public WarBean restoreWar(String jsonString) {
		try {
			WarBean warBean = om.readValue(jsonString, WarBean.class);
			warBean.redTeam.war = warBean;
			warBean.blueTeam.war = warBean;
			warBean.redTeam.players.forEach(player -> {
				player.war = warBean;
				player.team = TeamType.RED;
				player.creatures.forEach(creature -> {
					creature.player = player;
					populatePlayerForNextCreatures(warBean, player, creature);
				});
			});
			warBean.blueTeam.players.forEach(player -> {
				player.war = warBean;
				player.team = TeamType.BLUE;
				player.creatures.forEach(creature -> {
					creature.player = player;
					populatePlayerForNextCreatures(warBean, player, creature);
				});
			});

			return warBean;
		} catch (Exception ex) {
			LOG.error("Failed to deserialize from Json {}", jsonString, ex);
			throw new RuntimeException(ex);
		}
	}

	public void populatePlayerForNextCreatures(WarBean warBean, WarPlayerBean player, WarCreatureBean creature) {
		if (warBean.nextCreaturesToMove != null) {
			warBean.nextCreaturesToMove.forEach(tmpCreature -> {
				if (tmpCreature.id.equals(creature.id)) {
					tmpCreature.player = player;
				}
			});
		}
	}
}
