package cn.xilio.project.service.impl;

import cn.xilio.project.bo.Article;
import cn.xilio.project.bo.ArticleTag;
import cn.xilio.project.bo.Category;
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
import cn.xilio.project.mapper.ArticleTagMapper;
import cn.xilio.project.mapper.TagMapper;
import cn.xilio.project.service.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
    private ITagService tagService;
    @Autowired
    private IColumnService columnService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IArticleTagService articleTagService;

    @Override
    public IPage<ArticleBriefVO> listByCategory(ArticleListDTO articleListDTO) {
        Page<ArticleBriefVO> page = Page.of(articleListDTO.getCurrent(), articleListDTO.getSize());
        IPage<ArticleBriefVO> articleBriefVOPage = null;
        if (articleListDTO.getShowType() == 1) {
            return articleBriefVOPage;
        } else if (articleListDTO.getShowType() == 2) {
            return articleBriefVOPage;
        } else if (articleListDTO.getShowType() == 3) {
            articleBriefVOPage = selectByCategoryAndRecent(page, articleListDTO);
            // 处理标签数据
            for (ArticleBriefVO article : articleBriefVOPage.getRecords()) {
                if (article.getTagIds() != null && article.getTagNames() != null) {
                    String[] tagIds = article.getTagIds().split(",");
                    String[] tagNames = article.getTagNames().split(",");
                    List<Tag> tags = IntStream.range(0, tagIds.length)
                            .mapToObj(i -> {
                                Tag tag = new Tag();
                                tag.setId(tagIds[i]);
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
    public ArticleDetailVO detail(String id) {
        ArticleDetailVO articleDetail = baseMapper.selectArticleDetail(id);
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
    @Transactional(rollbackFor = Exception.class)
    public void addArticle(SaveArticleDTO dto) {
        String categoryId = dto.getCategoryId();
        List<String> tagNames = dto.getTagNames();
        List<String> columnNames = dto.getColumnNames();
        //检查分类领域是否存在
        Category category = categoryService.getById(categoryId);
        if (ObjectUtils.isEmpty(category)) {
            throw new BizException("分类领域不存在！");
        }
        //如果数据库没有标签则创建标签
        List<Tag> tagList = new ArrayList<>(5);
        List<Tag> allTagList = new ArrayList<>(5);
        tagNames.forEach(tagName -> {
            Tag tag = tagService.getByName(tagName);
            if (ObjectUtils.isEmpty(tag)) {
                tag = new Tag();
                tag.setName(tagName);
                tagList.add(tag);
            }
            allTagList.add(tag);
        });
        //保存文章到数据库
        Article newArticle = new Article();
        newArticle.setTitle(dto.getTitle());
        newArticle.setDescription(dto.getDescription());
        newArticle.setContent(dto.getContent());
        newArticle.setUserId("1");//todo user
        newArticle.setCover(dto.getCover());
        //todo test
        if (!StringUtils.hasLength(dto.getCover())){
            newArticle.setCover("https://picsum.photos/1920/1080?random=1");
        }
        newArticle.setVisibleStatus(dto.getVisibleStatus());
        if (dto.getVisibleStatus() == 4) {
            if (!StringUtils.hasLength(dto.getVisitPassword())) {
                throw new BizException("文章可见状态为密码访问，必须输入密码！");
            }
            newArticle.setVisitPassword(dto.getVisitPassword());
        }
        newArticle.setPublishStatus(dto.getPublishStatus());
        if (dto.getCreativeType() == 2) {
            if (!StringUtils.hasLength(dto.getOriginalUrl())) {
                throw new BizException("当前文章类型是转载，需要填写原文链接！");
            }
            newArticle.setOriginalUrl(dto.getOriginalUrl());
        }
        newArticle.setCreativeType(dto.getCreativeType());
        newArticle.setCategoryId(categoryId);
        save(newArticle);
        //批量创建标签
        tagService.saveBatch(tagList);
        //创建标签与文章的关联
        List<ArticleTag> articleTagsList = new ArrayList<>(5);
        allTagList.forEach(tag -> {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(newArticle.getId());
            articleTag.setTagId(tag.getId());
            articleTagsList.add(articleTag);
        });
        //建立标签与文章的关联
        articleTagService.saveBatch(articleTagsList);
        //如果数据库没有专栏则创建专栏
    }

    @Override
    public void updateArticle(SaveArticleDTO article) {

    }

    @Override
    public GetArticleVO getArticleById(String id) {
        return null;
    }

    @Override
    public IPage<ArticleBriefVO> selectByCategoryAndRecent(Page<ArticleBriefVO> page, ArticleListDTO articleListDTO) {
        return baseMapper.selectByCategoryAndRecent(page, articleListDTO);
    }
}
