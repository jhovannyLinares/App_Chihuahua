package mx.morena.negocio.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.collection.spi.PersistentCollection;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;

public class MapperUtil {

	private static ModelMapper modelMapper = new ModelMapper();

	/**
	 * Model mapper property setting are specified in the following block. Default
	 * property matching strategy is set to Strict see {@link MatchingStrategies}
	 * Custom mappings are added using {@link ModelMapper#addMappings(PropertyMap)}
	 */
	static {
		modelMapper = new ModelMapper();

		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		modelMapper.getConfiguration().setPropertyCondition(new Condition<Object, Object>() {
			public boolean applies(MappingContext<Object, Object> context) {
				return !(context.getSource() instanceof PersistentCollection);
			}
		});

		modelMapper.addConverter(new AbstractConverter<String, String>() {
			protected String convert(String source) {
				return source == null ? null : source.trim();//.toLowerCase();
			}
		});

	}

	/**
	 * Hide from public usage.
	 */
	private MapperUtil() {
	}

	/**
	 * <p>
	 * Note: outClass object must have default constructor with no arguments
	 * </p>
	 *
	 * @param <D>      type of result object.
	 * @param <T>      type of source object to map from.
	 * @param entity   entity that needs to be mapped.
	 * @param outClass class of result object.
	 * @return new object of <code>outClass</code> type.
	 */
	public static <D, T> D map(final T entity, Class<D> outClass) {
		return modelMapper.map(entity, outClass);
	}

	/**
	 * <p>
	 * Note: outClass object must have default constructor with no arguments
	 * </p>
	 *
	 * @param entityList list of entities that needs to be mapped
	 * @param outCLass   class of result list element
	 * @param <D>        type of objects in result list
	 * @param <T>        type of entity in <code>entityList</code>
	 * @return list of mapped object with <code><D></code> type.
	 */
	public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
		List<D> result = new ArrayList<D>();
		for (T entity : entityList) {
			result.add(map(entity, outCLass));
		}
		return result;
	}

	/**
	 * Maps {@code source} to {@code destination}.
	 *
	 * @param source      object to map from
	 * @param destination object to map to
	 */
	public static <S, D> D map(final S source, D destination) {
		modelMapper.map(source, destination);
		return destination;
	}
}