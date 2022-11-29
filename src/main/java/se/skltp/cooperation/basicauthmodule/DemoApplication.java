//////
// 2022-05-17, Henrik Augustsson.
// Nordic Medtest.
//////

package se.skltp.cooperation.basicauthmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * This main-app-launcher exists only to allow the prototype to launch.
 * Necessary bits are: The @EnableScheduling property.
 * The Datasource and Hibernate exclusions shouldn't be needed in a full app.
 * Can otherwise happily be annihilated if unneeded.
 */

/*
@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class
})
 */

// @EnableScheduling
public class DemoApplication {

	/*
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	*/

}
