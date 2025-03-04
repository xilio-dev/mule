package com.stackoak.stackoak.application.config;

import com.baidu.fsg.uid.UidGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class CustomIdGenerator implements IdentifierGenerator {
    @Resource
    private UidGenerator uidGenerator;

    @Override
    public Number nextId(Object entity) {
        return uidGenerator.getUID();
    }
    @Override
    public String nextUUID(Object entity) {
      return uidGenerator.getUID()+"";

    }
}
