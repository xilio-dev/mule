package cn.xilio.project.config;

import cn.xilio.project.common.ex.BizException;
import com.baidu.fsg.uid.UidGenerator;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
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
        long uid = uidGenerator.getUID();
        return uid;
    }

    @Override
    public String nextUUID(Object entity) {
        long uid = uidGenerator.getUID();
        return uid + "";
    }
}
