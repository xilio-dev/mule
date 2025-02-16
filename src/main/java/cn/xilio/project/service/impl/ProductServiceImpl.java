package cn.xilio.project.service.impl;

import cn.xilio.project.bo.Product;
import cn.xilio.project.mapper.ProductMapper;
import cn.xilio.project.service.IProductService;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
