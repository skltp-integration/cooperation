package se.skltp.cooperation.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapping configuration
 *
 * @author Peter Merikan
 */
@Configuration
public class MappingConfiguration {

	@Bean
	public DozerBeanMapper mapper() {

		List mappingFiles = new ArrayList<>();
		mappingFiles.add("dozerBeanMapping.xml");

		return new DozerBeanMapper(mappingFiles);
	}
}
