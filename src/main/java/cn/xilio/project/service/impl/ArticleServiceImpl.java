package cn.xilio.project.service.impl;

import cn.xilio.project.bo.Article;
import cn.xilio.project.mapper.ArticleMapper;
import cn.xilio.project.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author xilio.cn
 * @since 2025-02-15
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
