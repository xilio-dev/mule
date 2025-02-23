package cn.xilio.project.service.impl;

import cn.xilio.project.bo.Article;
import cn.xilio.project.bo.Tag;
import cn.xilio.project.common.ResultEnum;
import cn.xilio.project.common.ex.BizException;
import cn.xilio.project.domain.ArticleListDTO;
import cn.xilio.project.domain.vo.ArticleBriefVO;
import cn.xilio.project.domain.vo.article.TagInfoDTO;
import cn.xilio.project.domain.vo.article.add.SaveArticleDTO;
import cn.xilio.project.domain.vo.article.detail.ArticleDetailVO;
import cn.xilio.project.domain.vo.article.get.GetArticleVO;
import cn.xilio.project.mapper.ArticleMapper;
import cn.xilio.project.service.IArticleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public ArticleDetailVO detail(Long id) {
        ArticleDetailVO articleDetail = articleMapper.selectArticleDetail(id);
        if (articleDetail != null) {
            String tagIdStr = articleDetail.getTagIds();
            if (tagIdStr != null) {
                String[] tagIds = articleDetail.getTagIds().split(",");
                String[] tagNames = articleDetail.getTagNames().split(",");
                List<TagInfoDTO> tags = IntStream.range(0, tagIds.length)
                        .mapToObj(i -> {
                            TagInfoDTO tag = new TagInfoDTO();
                            tag.setId(Long.parseLong(tagIds[i]));
                            tag.setName(tagNames[i]);
                            return tag;
                        })
                        .collect(Collectors.toList());
                articleDetail.setTags(tags);
            }
        }
        return articleDetail;
    }

    @Override
    public void addArticle(SaveArticleDTO article) {

    }

    @Override
    public void updateArticle(SaveArticleDTO article) {

    }

    @Override
    public GetArticleVO getArticleById(Long id) {
        return null;
    }
}
