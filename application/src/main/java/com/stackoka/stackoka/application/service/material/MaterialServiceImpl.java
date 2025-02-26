package com.stackoka.stackoka.application.service.material;


import com.stackoka.stackoka.common.data.material.Material;
import com.stackoka.stackoka.repository.material.MaterialMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-24 17:49:31
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {

}
