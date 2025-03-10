package com.stackoak.stackoak.application.service.article;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.common.data.article.*;
import com.stackoak.stackoak.application.service.category.ICategoryService;
import com.stackoak.stackoak.application.service.collect.ICollectService;
import com.stackoak.stackoak.application.service.column.IColumnService;
import com.stackoak.stackoak.application.service.like.ILikesService;
import com.stackoak.stackoak.application.service.search.ISearchService;
import com.stackoak.stackoak.application.service.tag.ITagService;
import com.stackoak.stackoak.common.data.category.Category;
import com.stackoak.stackoak.common.data.collect.ArticleCollect;
import com.stackoak.stackoak.common.data.collect.Collect;
import com.stackoak.stackoak.common.data.column.ArticleColumn;
import com.stackoak.stackoak.common.data.column.Column;
import com.stackoak.stackoak.common.data.likes.LikeTypeEnum;
import com.stackoak.stackoak.common.data.likes.Likes;
import com.stackoak.stackoak.common.data.tag.Tag;
import com.stackoak.stackoak.common.message.ResultEnum;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.common.data.tag.TagInfoDTO;

import com.stackoak.stackoak.repository.article.ArticleMapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.repository.collect.ArticleCollectMapper;
import com.stackoak.stackoak.repository.column.ArticleColumnMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author stackoak.com
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
    @Autowired
    private ArticleColumnMapper articleColumnMapper;
    @Autowired
    private ILikesService likesService;
    @Autowired
    private ICollectService collectService;
    @Autowired
    private ArticleCollectMapper articleCollectMapper;
    @Autowired
    private ISearchService searchService;

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
                            tag.setId(tagIds[i]);
                            tag.setName(tagNames[i]);
                            return tag;
                        })
                        .collect(Collectors.toList());
                articleDetail.setTags(tags);
            }
        }
        //判断是否是登陆用户
        if (StpKit.USER.isLogin()) {
            UserInteractDTO userInteract = getUserInteract(dto.getId());
            //设置交互信息为
            articleDetail.setUserInteract(userInteract);
        }

        return articleDetail;
    }

    private UserInteractDTO getUserInteract(String aid) {
        return new UserInteractDTO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveArticle(SaveArticleDTO dto) {
        String userId = StpKit.USER.getLoginIdAsString();
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
        Article saveArticle;
        if (!isAdd) {
            saveArticle = getById(dto.getId());
            if (ObjectUtils.isEmpty(saveArticle)) {
                throw new BizException("文章不存在！");
            }
        } else {
            saveArticle = new Article();
        }
        //保存文章到数据库
        saveArticle.setTitle(dto.getTitle());
        saveArticle.setDescription(dto.getDescription());
        saveArticle.setContent(dto.getContent());
        saveArticle.setUserId(userId);
        saveArticle.setCover(dto.getCover());
        saveArticle.setPublishTime(LocalDateTime.now());
        saveArticle.setVisibleStatus(dto.getVisibleStatus());
        if (!StringUtils.hasText(dto.getDescription())) {
            saveArticle.setDescription(dto.getContent().trim().substring(0, 200));
        }
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
            if (ObjectUtils.isEmpty(dto.getAuthorizeStatus())) {
                throw new BizException("当前文章类型是转载，需要填写授权状态！");
            }
            if (dto.getAuthorizeStatus() != 1) {
                throw new BizException("当前文章类型是转载，请确认已获得原文作者授权");
            }
            saveArticle.setOriginalUrl(dto.getOriginalUrl());
        }
        saveArticle.setCreativeType(dto.getCreativeType());
        saveArticle.setCategoryId(categoryId);
        if (isAdd) {
            //新增文章并加上索引
            save(saveArticle);
            //todo searchService.saveIndex(saveArticle);
        } else {
            //更新文章并更新索引
            updateById(saveArticle);
            //todo searchService.saveIndex(saveArticle);

        }
        //文章专栏保存与关联 一篇文章存在于3个专栏
        //如果是更新，合并专栏信息
        if (!isAdd && !columnNames.isEmpty()) {
            List<Column> oldColumns = columnService.getColumnsByArticleId(saveArticle.getId(), userId);
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
        if (!isAdd && !tagNames.isEmpty()) {
            // 更新文章：合并标签信息
            List<Tag> oldTags = tagService.getTagsByArticleId(saveArticle.getId());
            List<String> oldTagNames = oldTags.stream().map(Tag::getName).toList();

            // 需要添加的标签
            List<String> newTagsToAdd = tagNames.stream()
                    .filter(name -> !oldTagNames.contains(name))
                    .toList();

            // 需要移除的标签
            List<String> oldTagsToRemove = oldTagNames.stream()
                    .filter(name -> !tagNames.contains(name))
                    .toList();

            // 添加新的标签并建立关联
            createTagIfNotExist(userId, newTagsToAdd, saveArticle);

            // 移除旧的标签关联
            for (String tagName : oldTagsToRemove) {
                Tag tag = oldTags.stream().filter(t -> t.getName().equals(tagName)).findFirst().orElse(null);
                if (tag != null) {
                    articleColumnMapper.deleteById(new ArticleTag(saveArticle.getId(), tag.getId()));
                }
            }
        } else {
            // 新增文章：如果用户提交的标签名字已经在数据库则建立关联，不存在则创建后再关联
            createTagIfNotExist(userId, tagNames, saveArticle);
        }
        //如果数据库没有专栏则创建专栏
        return saveArticle.getId();
    }

    private void createTagIfNotExist(String userId, List<String> tagNames, Article saveArticle) {
        for (String tagName : tagNames) {
            Tag tag = tagService.getTagByName(tagName);
            if (tag == null) {
                // 如果标签不能存在则创建
                tag = new Tag();
                tag.setUserId(userId);
                tag.setName(tagName);
                tagService.save(tag);
            }
            // 建立文章与专栏的关联
            articleTagService.save(new ArticleTag(saveArticle.getId(), tag.getId()));
        }
    }

    /**
     * 如果专栏不存在则创建再建立关联
     *
     * @param userId      用户
     * @param columnNames 表单提交的专栏名字
     * @param saveArticle 文章
     */
    private void createColumIfNotExist(String userId, List<String> columnNames, Article saveArticle) {
        for (String columnName : columnNames) {
            //todo userid
            Column column = columnService.getColumnByName(columnName, "1");
            if (column == null) {
                // 如果专栏不存在，创建专栏
                column = new Column();
                column.setUserId(userId);
                column.setName(columnName);
                columnService.save(column);
            }
            // 建立文章与专栏的关联
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void digg(ArticleId articleId) {
        diggAndUndigg(articleId, 1);
    }

    /**
     * 点赞或取消点赞
     *
     * @param articleId 文章编号
     * @param op        操作类型：1是点赞、0是取消点赞
     */
    private void diggAndUndigg(ArticleId articleId, Integer op) {
        Article article = getById(articleId.getAid());
        if (ObjectUtils.isEmpty(article)) {
            throw new BizException("文章不存在！");
        }
        Likes likes = new Likes();
        likes.setTargetId(article.getId());
        likes.setUserId("1");//todo 临时用户
        likes.setType(LikeTypeEnum.ARTICLE.getType());
        if (op == 1) {
            //判断是否已经点过赞了，不能重复点赞
            Likes like = likesService.getLike("1", article.getId(), LikeTypeEnum.ARTICLE);
            if (!ObjectUtils.isEmpty(like)) {
                throw new BizException("不能重复点赞！");
            }
            likesService.save(likes);
        } else {
            likesService.removeById(likes);
        }
    }

    @Override
    public void cancelDigg(ArticleId articleId) {
        diggAndUndigg(articleId, 0);
    }

    /**
     * 将文章添加到收藏夹
     * todo 需要优化
     *
     * @param favorRequest 收藏请求
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addToFavor(FavorRequest favorRequest) {
        //检查文章是否存在
        Article article = getById(favorRequest.getAid());
        if (ObjectUtils.isEmpty(article)) {
            throw new BizException("文章不存在！");
        }
        //检查收藏夹是否存在
        Collect collect = collectService.getCollectByUser(favorRequest.getCollectId(), "1");
        if (ObjectUtils.isEmpty(collect)) {
            throw new BizException("收藏夹不存在！");
        }
        //保存文章到收藏夹、添加文章与收藏夹的关联
        ArticleCollect articleCollect = new ArticleCollect(favorRequest.getAid(), favorRequest.getCollectId());
        articleCollectMapper.insert(articleCollect);
    }

    /**
     * 从收藏夹中取消文章收藏
     * todo 需要优化
     *
     * @param favorRequest 取消收藏请求信息
     */
    @Override
    public void fromFavorDel(FavorRequest favorRequest) {
        //检查文章是否存在
        Article article = getById(favorRequest.getAid());
        if (ObjectUtils.isEmpty(article)) {
            throw new BizException("文章不存在！");
        }
        Collect collect = collectService.getCollectByUser(favorRequest.getCollectId(), "1");
        if (ObjectUtils.isEmpty(collect)) {
            throw new BizException("收藏夹不存在！");
        }
        //删除文章与收藏夹的关联
        ArticleCollect articleCollect = new ArticleCollect(favorRequest.getAid(), favorRequest.getCollectId());
        articleCollectMapper.deleteById(articleCollect);
    }

    /**
     * 获取用户公开发表的文章
     *
     * @param request 请求参数
     * @return 文章列表
     */
    @Override
    public IPage<ArticleBriefVO> getPublishArticle(ArticleQueryRequest request) {

        return null;
    }

    @Override
    public void deleteArticle(ArticleId dto) {
        String userId = StpKit.USER.getLoginIdAsString();
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getUserId, userId);
        wrapper.eq(Article::getId, dto.getAid());
        Article article = getOne(wrapper);
        article.setStatus(ArticleStatus.RECYCLE.getCode());
        updateById(article);
        //todo 更新搜索引擎或缓存
    }
}
