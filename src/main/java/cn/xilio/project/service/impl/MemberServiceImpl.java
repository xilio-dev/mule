package cn.xilio.project.service.impl;

import cn.xilio.project.bo.Member;
import cn.xilio.project.mapper.MemberMapper;
import cn.xilio.project.service.IMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xilio.cn
 * @since 2025-02-15
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

}
