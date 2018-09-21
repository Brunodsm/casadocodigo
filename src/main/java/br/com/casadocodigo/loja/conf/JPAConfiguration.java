package br.com.casadocodigo.loja.conf; 
// classe de configuração da JPA

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement // habilita a transação
public class JPAConfiguration {
	
	@Bean // retorna uma classe gerenciada pelo spring
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
		
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean(); // Cria nosso entity manager factory
		
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(); // adaptador do hibernate 
		
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("root"); // Usuário do banco de dados 
		dataSource.setPassword("root"); // senha do banco de dados
		dataSource.setUrl("jdbc:mysql://localhost:3306/casadocodigo"); // url de conexão 
		dataSource.setDriverClassName("com.mysql.jdbc.Driver"); // classe para fazermos a conexão
		
		factoryBean.setDataSource(dataSource);
		
		 Properties props = new Properties(); // propriedades que temos que configurar do hibernate 
		 props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");//dialeto. Esse dialeto vai ser o que o hibernate vai conversar com o banco de dados.
		 props.setProperty("hibernate.show_sql", "true");// essa configuração do hibernate permite que vemos o próprio sql gerado pelo hibernate
		 props.setProperty("hibernate.hbm2ddl.auto", "update");// essa configuração faz com que toda vez que mudar nossa modelo, o hibernate muda o banco.
		 
		 factoryBean.setJpaProperties(props);// adicionando nossas propriedades 
		 
		 factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models"); // a gente fala onde esta cada entidade para ele scanealas 
		 
		 return factoryBean;
	
	}
		
	
		// depois que habilitamos o transactionManager, temos que criar tambem o método que vai associar o transactionManager com
		//nosso entityManager. Uma vez que estamos utilizando jpa, precisa ser esse JpaTransactionManager.
		// Esse método esta anotado como bean, para que o spring o reconheça como o criador da transação, como o método que cuida
		// da transação.
		@Bean
		public JpaTransactionManager transactionManager(EntityManagerFactory emf) { // entityManager passado como parâmetro
		return new JpaTransactionManager(emf);
			
		}
	
}
