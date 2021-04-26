package com.sino.thirdParty.util;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Properties;

/**
 * 雪花Id生成器
 * @author xiaozj
 */
@Component
public class SnowFlakeIdGenerator implements IdentifierGenerator, Configurable {

    @Resource
    private SnowFlakeIdWorker idWorker;

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        idWorker = new SnowFlakeIdWorker();
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return idWorker.nextId();
    }

    @Override
    public boolean supportsJdbcBatchInserts() {
        return false;
    }
}
