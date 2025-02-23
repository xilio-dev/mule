package cn.xilio.project.service.impl;

import cn.xilio.project.bo.Category;
import cn.xilio.project.mapper.CategoryMapper;
import cn.xilio.project.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xilio.cn
 * @since 2025-02-24
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
