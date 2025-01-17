/**
 * Copyright (c) 2014 Center for eHalsa i samverkan (CeHis).
 * 								<http://cehis.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.cooperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.scheduling.annotation.EnableScheduling;

// cooperation-config-override.properties file is for tomcat production deployment
// should contain
// spring.datasource.url=jdbc:mysql://localhost/cooperation
// spring.datasource.username=root
// spring.datasource.testOnBorrow: true
// spring.datasource.validationQuery: SELECT 1
// spring.datasource.password=

// these properties are left out of the application.yml in the prod profile section
//
// tomcat bin directory should contain a setenv.sh containing
// export CATALINA_OPTS="$CATALINA_OPTS -Dspring.profiles.active=prod"
// export CATALINA_OPTS="$CATALINA_OPTS -Dapp.conf.dir=/Users/jan/conf"
// the cooperation-config-override.properties should be placed in the directory pointed out in the last row above

@SpringBootApplication
@PropertySources({
    @PropertySource(value = "file:${app.conf.dir}/cooperation-config-override.properties", ignoreResourceNotFound = true)
})
@EnableScheduling
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// To produce formatted output in a browser
	// Not sure that we should do this
	// But is a good place to configure Jackson, possible with other information
	@Bean
    public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper;
    }
}
