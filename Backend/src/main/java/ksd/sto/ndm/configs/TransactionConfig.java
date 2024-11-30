package ksd.sto.ndm.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class TransactionConfig {

    private static final int TX_TIMEOUT = 30;

    @Bean
    PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Primary
    @Bean
    TransactionInterceptor txAdvice(PlatformTransactionManager transactionManager) {

        TransactionInterceptor txAdvice = new TransactionInterceptor();
        /*
         * 모든 예외처리에 대해 롤백 적용
         */
        List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
        rollbackRules.add(new RollbackRuleAttribute(Exception.class));

        DefaultTransactionAttribute readOnlyAttribute = new DefaultTransactionAttribute(
                TransactionDefinition.PROPAGATION_SUPPORTS);
        readOnlyAttribute.setReadOnly(true);
        readOnlyAttribute.setTimeout(TX_TIMEOUT);

        RuleBasedTransactionAttribute writeAttribute = new RuleBasedTransactionAttribute(
                TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        writeAttribute.setTimeout(TX_TIMEOUT);
        /*
         * 트랜잭션 세션 새로 생성
         */
        RuleBasedTransactionAttribute newWriteAttribute = new RuleBasedTransactionAttribute(
                TransactionDefinition.PROPAGATION_REQUIRES_NEW, rollbackRules);
        newWriteAttribute.setTimeout(TX_TIMEOUT);

        String readOnlyTransactionAttributeDefinition = readOnlyAttribute.toString();
        String writeTransactionAttributeDefinition = writeAttribute.toString();
        String newWriteTransactionAttributeDefinition = newWriteAttribute.toString();

        Properties txAttributes = new Properties();
        Arrays
            .stream(new String[]{"create*", "insert*", "ins*", "update*", "modify*", "upd*"})
            .forEach(methodName -> {
                txAttributes.setProperty(methodName, writeTransactionAttributeDefinition);
            });

        Arrays.stream(new String[]{"partial*", "divideTr*"}).forEach(methodName -> {
            txAttributes.setProperty(methodName, newWriteTransactionAttributeDefinition);
        });

        txAttributes.setProperty("*", readOnlyTransactionAttributeDefinition);
        
        txAdvice.setTransactionAttributes(txAttributes);
        txAdvice.setTransactionManager(transactionManager);

        return txAdvice;

    }

    /**
     * execution(접근제어자 리턴타입 패키지경로.클래스이름.메서드명(파라미터))
     * 표현식의 각 부분:
        * : 리턴 타입에 관계없이 모든 메서드 매칭.
        *.. : 루트부터 시작해 모든 하위 패키지를 포함.
        service.. : service 패키지 및 하위 패키지를 매칭.
        *Impl : 클래스 이름이 Impl로 끝나는 모든 클래스 매칭.
        .*(..) : 해당 클래스의 모든 메서드 매칭.
          - .*   : 모든 메서드 이름.
          - (..) : 매개변수 타입 및 개수에 상관없이 모든 메서드.
        예) serivce폴더 내 Impl로 끝나는 것에 대해서만 적용 => * com.example.service.*Impl.*(..)
        
     * @param txAdvice
     * @return AspectJExpressionPointcutAdvisor 
     */
    @Bean
    AspectJExpressionPointcutAdvisor transactionAdvisor(TransactionInterceptor txAdvice) {

        String expression = "execution(* *..service..*Impl.*(..)) && !@annotation(org.springframework.transaction.annotation.Transactional)";

        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(expression);
        advisor.setAdvice(txAdvice);

        return advisor;
    }
}
