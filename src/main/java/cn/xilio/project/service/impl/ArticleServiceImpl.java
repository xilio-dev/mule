package cn.xilio.project.service.impl;

import cn.xilio.project.bo.Article;
import cn.xilio.project.bo.Tag;
import cn.xilio.project.common.ResultEnum;
import cn.xilio.project.common.ex.BizException;
import cn.xilio.project.domain.ArticleListDTO;
import cn.xilio.project.domain.vo.ArticleBriefVO;
import cn.xilio.project.mapper.ArticleMapper;
import cn.xilio.project.service.IArticleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Autowired
    private ArticleMapper articleMapper;

    @Override

    public IPage<ArticleBriefVO> listByCategory(ArticleListDTO articleListDTO) {
        Page<ArticleBriefVO> page = Page.of(articleListDTO.getCurrent(), articleListDTO.getSize());
        IPage<ArticleBriefVO> articleBriefVOPage = null;
        if (articleListDTO.getShowType() == 1) {
            return articleBriefVOPage;
        } else if (articleListDTO.getShowType() == 2) {
            return articleBriefVOPage;
        } else if (articleListDTO.getShowType() == 3) {
            articleBriefVOPage = articleMapper.selectByCategoryAndRecent(page, articleListDTO);
            // 处理标签数据
            for (ArticleBriefVO article : articleBriefVOPage.getRecords()) {
                if (article.getTagIds() != null && article.getTagNames() != null) {
                    String[] tagIds = article.getTagIds().split(",");
                    String[] tagNames = article.getTagNames().split(",");
                    List<Tag> tags = IntStream.range(0, tagIds.length)
                            .mapToObj(i -> {
                                Tag tag = new Tag();
                                tag.setId(Long.parseLong(tagIds[i]));
                                tag.setName(tagNames[i]);
                                return tag;
                            })
                            .collect(Collectors.toList());
                    article.setTags(tags);
                }
            }
            return articleBriefVOPage;
        } else {
            throw new BizException(ResultEnum.BIZ_ERROR);
        }
    }
}
