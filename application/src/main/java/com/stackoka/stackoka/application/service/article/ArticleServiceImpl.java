package com.stackoka.stackoka.application.service.article;


import com.stackoka.stackoka.application.service.category.ICategoryService;
import com.stackoka.stackoka.application.service.column.IColumnService;
import com.stackoka.stackoka.application.service.tag.ITagService;
import com.stackoka.stackoka.common.data.article.*;
import com.stackoka.stackoka.common.data.category.Category;
import com.stackoka.stackoka.common.data.column.ArticleColumn;
import com.stackoka.stackoka.common.data.column.Column;
import com.stackoka.stackoka.common.data.tag.Tag;
import com.stackoka.stackoka.common.message.ResultEnum;
import com.stackoka.stackoka.application.exception.BizException;
import com.stackoka.stackoka.common.data.tag.TagInfoDTO;

import com.stackoka.stackoka.repository.article.ArticleMapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoka.stackoka.repository.column.ArticleColumnMapper;
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
 * @author stackoka.com
 * @since 2025-02-15
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleDO> implements IArticleService {

    @Autowired
    private ITagService tagService;
    @Autowired
    private IColumnService columnService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IArticleTagService articleTagService;
    @Autowired
    private ArticleColumnMapper articleColumnMapper;

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
    public ArticleDetailVO detail(ArticleDetailDTO dto) {
        ArticleDetailVO articleDetail = baseMapper.selectArticleDetail(dto.getId());
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
    public String saveArticle(SaveArticleDTO dto) {
        String userId = "1";
        boolean isAdd = !StringUtils.hasLength(dto.getId());
        String categoryId = dto.getCategoryId();
        List<String> tagNames = dto.getTagNames();
        List<String> columnNames = dto.getColumnNames();
        //检查分类领域是否存在
        Category category = categoryService.getById(categoryId);
        if (ObjectUtils.isEmpty(category)) {
            throw new BizException("分类领域不存在！");
        }

        //如果文章ID存在，通过文章ID查询是否存在当前用户文章
        ArticleDO saveArticle;
        if (!isAdd) {
            saveArticle = getById(dto.getId());
            if (ObjectUtils.isEmpty(saveArticle)) {
                throw new BizException("文章不存在！");
            }
        } else {
            saveArticle = new ArticleDO();
        }
        //保存文章到数据库
        saveArticle.setTitle(dto.getTitle());
        saveArticle.setDescription(dto.getDescription());
        saveArticle.setContent(dto.getContent());
        saveArticle.setUserId(userId);//todo user
        saveArticle.setCover(dto.getCover());
        //todo test
        if (!StringUtils.hasLength(dto.getCover())) {
            saveArticle.setCover("https://picsum.photos/1920/1080?random=1");
        }
        saveArticle.setVisibleStatus(dto.getVisibleStatus());
        if (dto.getVisibleStatus() == 4) {
            if (!StringUtils.hasLength(dto.getVisitPassword())) {
                throw new BizException("文章可见状态为密码访问，必须输入密码！");
            }
            saveArticle.setVisitPassword(dto.getVisitPassword());
        }
        saveArticle.setPublishStatus(dto.getPublishStatus());
        if (dto.getCreativeType() == 2) {
            if (!StringUtils.hasLength(dto.getOriginalUrl())) {
                throw new BizException("当前文章类型是转载，需要填写原文链接！");
            }
            saveArticle.setOriginalUrl(dto.getOriginalUrl());
        }
        saveArticle.setCreativeType(dto.getCreativeType());
        saveArticle.setCategoryId(categoryId);
        if (isAdd) {
            save(saveArticle);
        } else {
            updateById(saveArticle);
        }
        //文章专栏保存与关联 一篇文章存在于3个专栏
        //如果是更新，合并专栏信息
        if (!isAdd && !columnNames.isEmpty()) {
            List<Column> oldColumns = columnService.getColumnsByArticleId(saveArticle.getId(),userId);
            //旧的专栏名字列表
            List<String> oldColumnNames = oldColumns.stream().map(Column::getName).toList();

            // 需要添加的专栏
            List<String> newColumnsToAdd = columnNames.stream()
                    .filter(name -> !oldColumnNames.contains(name))
                    .toList();

            // 需要移除的专栏
            List<String> oldColumnsToRemove = oldColumnNames.stream()
                    .filter(name -> !columnNames.contains(name))
                    .toList();

            // 添加新的专栏并建立关联
            createColumIfNotExist(userId, newColumnsToAdd, saveArticle);
            // 移除旧的专栏关联
            for (String columnName : oldColumnsToRemove) {
                Column column = oldColumns.stream().filter(c -> c.getName().equals(columnName)).findFirst().orElse(null);
                if (column != null) {
                    articleColumnMapper.deleteById(new ArticleColumn(column.getId(), saveArticle.getId()));
                }
            }
        } else {
            // 新增文章：如果用户提交的专栏名字已经在数据库则建立关联，不存在则创建后再关联
            createColumIfNotExist(userId, columnNames, saveArticle);
        }
        /*--------------------------------------------------------------------------------------*/
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
        //批量创建标签
        tagService.saveBatch(tagList);
        //创建标签与文章的关联
        List<ArticleTag> articleTagsList = new ArrayList<>(5);
        allTagList.forEach(tag -> {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(saveArticle.getId());
            articleTag.setTagId(tag.getId());
            articleTagsList.add(articleTag);
        });
        //建立标签与文章的关联
        articleTagService.saveBatch(articleTagsList);
        //如果数据库没有专栏则创建专栏
        return saveArticle.getId();
    }

    /**
     * 如果专栏不存在则创建再建立关联
     *
     * @param userId      用户
     * @param columnNames 表单提交的专栏名字
     * @param saveArticle 文章
     */
    private void createColumIfNotExist(String userId, List<String> columnNames, ArticleDO saveArticle) {
        for (String columnName : columnNames) {
            //todo userid
            Column column = columnService.getColumnByName(columnName,"1");
            if (column == null) {
                // 如果专栏不存在，创建专栏
                Column c = new Column();
                c.setUserId(userId);
                c.setName(columnName);
                columnService.save(c);
            }
            // 建立文章与专栏的关联
            assert column != null;
            articleColumnMapper.insert(new ArticleColumn(column.getId(), saveArticle.getId()));
        }
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
