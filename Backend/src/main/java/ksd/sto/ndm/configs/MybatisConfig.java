package ksd.sto.ndm.configs;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan("ksd.sto.ndm.dao")
public class MybatisConfig {

    /**
     * SqlSessionFactory 빈 생성
     * 
     * 
     * @param dataSource 데이터베이스 연결을 위한 DataSource 객체
     * @return SqlSessionFactoryBean 객체
     * @throws Exception 예외 처리
     */
    @Bean
    SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource); // 데이터 소스 설정
        // MyBatis 설정 파일 위치 지정
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("ksd.sto.ndm.dao");
        /*
         * ibatis 세부 설정
         */
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setCallSettersOnNulls(true);
        sqlSessionFactoryBean.setConfiguration(configuration);
        
//        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return sqlSessionFactoryBean.getObject(); // SqlSessionFactory 반환
    }

    /**
     * SqlSessionTemplate 빈 생성
     * 
     * 
     * @param sqlSessionFactory SqlSessionFactory 객체
     * @return SqlSessionTemplate 인스턴스
     */
    @Bean
    SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory); // SqlSessionTemplate 반환
    }
}