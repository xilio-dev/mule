package com.stackoak.stackoak.application.service.ads;


import com.stackoak.stackoak.common.data.ads.Ads;
import com.stackoak.stackoak.repository.ads.AdsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
@Service
public class AdsServiceImpl extends ServiceImpl<AdsMapper, Ads> implements IAdsService {

}
