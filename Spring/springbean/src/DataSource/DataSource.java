package DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jndi.JndiObjectFactoryBean;

public class DataSource {

    @Bean
    public DataSource dataSource(){
        // 创建一个嵌入式的 Hypersonic 数据库
        // 模式（schema）定义在 schema.sql 中
        // 测试数据通过 test-data.sql 加载
        return (DataSource) new EmbeddedDatabaseBuilder()
                .addScript("classpath:schema.sql")
                .addScript("classpath:test-data.sql")
                .build();
    }

    /*@Bean
    public DataSource dataSource(){
        // 通过 JNDI 获取 DataSource
        // 能够让容器决定如何创建这个 DataSource 包括切换为容器管理的连接池
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("jdbc/myDS");
        jndiObjectFactoryBean.setResourceRef(true);
        jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);
        return (DataSource) jndiObjectFactoryBean.getObject();
    }*/

    /*@Bean(destroyMethod = "close")
    public DataSource dataSource(){
        // 在 QA 环境中，可以配置为Commons DBCP 连接池
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:tcp://dbserver/~/test");
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUserName("sa");
        dataSource.setPassWord("password");
        dataSource.setInitialSize(20);
        dataSource.setMaxActive(30);

        return dataSource;
    }*/

}
