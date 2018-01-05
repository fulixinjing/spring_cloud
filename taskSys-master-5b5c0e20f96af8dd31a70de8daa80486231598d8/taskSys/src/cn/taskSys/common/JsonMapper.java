package cn.taskSys.common;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.springframework.core.annotation.AnnotationUtils;

public class JsonMapper {

	private ObjectMapper objectMapper = new ObjectMapper();

	public JsonMapper() {
		// 设置默认日期格式
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
		// 提供其它默认设置
		objectMapper
				.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,
				false);
		objectMapper.setFilters(new SimpleFilterProvider()
				.setFailOnUnknownId(false));

	}

	@JsonFilter("axfilter")
	interface DefaultJsonFilter {
		// 默认的@JsonFilter。以实现按字段过滤
	}

	public String toJsonStr(Object value) throws JsonGenerationException,
			JsonMappingException, IOException {
		return objectMapper.writeValueAsString(value);
	}

	public String toJsonStr(Object value, String[] properties)
			throws JsonGenerationException, JsonMappingException, IOException {
		SerializationConfig cfg = objectMapper.getSerializationConfig();
		cfg.addMixInAnnotations(value.getClass(), DefaultJsonFilter.class);

		SimpleBeanPropertyFilter someFilter = SimpleBeanPropertyFilter
				.filterOutAllExcept(properties);
		SimpleFilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("axfilter", someFilter);

		return objectMapper.writer(filterProvider).writeValueAsString(value);

		// return objectMapper.writer(
		// new SimpleFilterProvider().addFilter(
		// AnnotationUtils.getValue(
		// AnnotationUtils.findAnnotation(
		// value.getClass(), JsonFilter.class))
		// .toString(), SimpleBeanPropertyFilter
		// .filterOutAllExcept(properties)))
		// .writeValueAsString(value);

	}

	public String toJsonStrWithExcludeProperties(Object value,
			String[] properties2Exclude) throws JsonGenerationException,
			JsonMappingException, IOException {

		SerializationConfig cfg = objectMapper.getSerializationConfig();
		cfg.addMixInAnnotations(value.getClass(), DefaultJsonFilter.class);

		SimpleBeanPropertyFilter someFilter = SimpleBeanPropertyFilter
				.serializeAllExcept(properties2Exclude);
		SimpleFilterProvider filterProvider = new SimpleFilterProvider()
				.addFilter("axfilter", someFilter);

		return objectMapper.writer(filterProvider).writeValueAsString(value);

		// return objectMapper.writer(
		// new SimpleFilterProvider().addFilter(
		// AnnotationUtils.getValue(
		// AnnotationUtils.findAnnotation(
		// value.getClass(), JsonFilter.class))
		// .toString(), SimpleBeanPropertyFilter
		// .serializeAllExcept(properties2Exclude)))
		// .writeValueAsString(value);

	}

	public void writeJsonStr(OutputStream out, Object value)
			throws JsonGenerationException, JsonMappingException, IOException {
		objectMapper.writeValue(out, value);
	}

	public void writeJsonStr(OutputStream out, Object value, String[] properties)
			throws JsonGenerationException, JsonMappingException, IOException {

		objectMapper.writer(
				new SimpleFilterProvider().addFilter(
						AnnotationUtils.getValue(
								AnnotationUtils.findAnnotation(
										value.getClass(), JsonFilter.class))
								.toString(), SimpleBeanPropertyFilter
								.filterOutAllExcept(properties))).writeValue(
				out, value);

	}

	public void writeJsonStrWithExcludeProperties(OutputStream out,
			Object value, String[] properties2Exclude)
			throws JsonGenerationException, JsonMappingException, IOException {
		objectMapper.writer(
				new SimpleFilterProvider().addFilter(
						AnnotationUtils.getValue(
								AnnotationUtils.findAnnotation(
										value.getClass(), JsonFilter.class))
								.toString(), SimpleBeanPropertyFilter
								.serializeAllExcept(properties2Exclude)))
				.writeValue(out, value);

	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

}
