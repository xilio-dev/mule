package com.stackoak.stackoak.application.service.faq;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.common.data.faq.FaqCategory;
import com.stackoak.stackoak.repository.faq.FaqCategoryMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * FAQ分类表 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-31 00:28:42
 */
@Service
public class FaqCategoryServiceImpl extends ServiceImpl<FaqCategoryMapper, FaqCategory> implements IFaqCategoryService {

}
