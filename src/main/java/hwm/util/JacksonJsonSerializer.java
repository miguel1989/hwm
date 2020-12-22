package hwm.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
			.build().setDateFormat(df);

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
		} catch (Exception e) {
			LOG.error("Failed to deserialize from Json {}", jsonString, e);
		}
		return null;
	}
}
