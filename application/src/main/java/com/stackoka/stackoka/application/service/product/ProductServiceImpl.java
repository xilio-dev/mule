package com.stackoka.stackoka.application.service.product;


import com.stackoka.stackoka.common.data.product.Product;
import com.stackoka.stackoka.repository.product.ProductMapper;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
