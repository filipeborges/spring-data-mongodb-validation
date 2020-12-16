package br.com.filipeborges.springdatamongodbvalidation;

import br.com.filipeborges.springdatamongodbvalidation.entities.Data;
import br.com.filipeborges.springdatamongodbvalidation.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class SpringDataMongodbValidationApplication implements CommandLineRunner {

	@Autowired
	private DataRepository dataRepository;

	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener() {
		return new ValidatingMongoEventListener(validator());
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataMongodbValidationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dataRepository
				.findAll()
				.doOnNext(System.out::println)
				.doOnError(Throwable::printStackTrace)
				.subscribe();

		dataRepository
				.save(buildInvalidDataEntity())
				.doOnNext(System.out::println)
				.doOnError(Throwable::printStackTrace)
				.subscribe();
	}

	private Data buildInvalidDataEntity() {
		Data data = new Data();
		data.setValue1("");
		data.setValue2("Value2");
		return data;
	}

}
