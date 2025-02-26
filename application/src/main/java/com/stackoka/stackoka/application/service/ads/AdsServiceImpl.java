package com.stackoka.stackoka.application.service.ads;


import com.stackoka.stackoka.common.data.ads.Ads;
import com.stackoka.stackoka.repository.ads.AdsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-15
 */
@Service
public class AdsServiceImpl extends ServiceImpl<AdsMapper, Ads> implements IAdsService {

}
