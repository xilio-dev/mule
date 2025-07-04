package com.stackoak.stackoak.application.service.article;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stackoak.stackoak.application.actors.alg.ContentHeatCalculator;
import com.stackoak.stackoak.application.actors.security.SecureManager;
import com.stackoak.stackoak.application.actors.security.StpKit;

import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
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
import com.stackoak.stackoak.common.data.likes.Like;
import com.stackoak.stackoak.common.data.tag.Tag;
import com.stackoak.stackoak.common.message.ResultEnum;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.repository.article.ArticleMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.repository.collect.ArticleCollectMapper;
import com.stackoak.stackoak.repository.column.ArticleColumnMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    @Autowired
    private ContentHeatCalculator heatCalculator;
    @Autowired
    private SecureManager secureManager;

    @Override
    public Page<ArticleBriefVO> listByCategory(ArticleListDTO articleListDTO) {
        Page<ArticleBriefVO> page = Page.of(articleListDTO.current(), articleListDTO.size());
        Page<ArticleBriefVO> articleBriefVOPage = null;
        if (articleListDTO.showType() == 1) {
            return articleBriefVOPage;
        } else if (articleListDTO.showType() == 2) {
            return articleBriefVOPage;
        } else if (articleListDTO.showType() == 3) {
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
        if (!ObjectUtils.isEmpty(articleDetail)) {
            //校验文章是否是受保护状态
            boolean protect = Objects.equals(articleDetail.getArticleInfo().getVisibleStatus(), 4);
            //检查访问者是否是作者本人
            boolean isAuthor = false;
            if (StpKit.USER.isLogin()) {
                String userId = StpKit.USER.getLoginIdAsString();
                isAuthor = Objects.equals(articleDetail.getUserInfo().getUserId(), userId);
            }
            //如果是受保护且不是作者本人的文章的时候
            if (protect && !isAuthor) {
                String visitPassword = articleDetail.getArticleInfo().getVisitPassword();
                //如果是受保护需要看是否携带了密码,密码为空返回没有访问权限，通知客户端输入密码
                BizException.noEmpty(dto.getPass(), ResultEnum.NO_VISIT_PERMISSION);
                //如果携带了密码验证密码是否正确则返回数据
                boolean success = secureManager.decrypt(visitPassword).equals(dto.getPass());
                //验证密码错误，返回错误码
                BizException.exprNull(!success, ResultEnum.PWD_ERROR);
            }
            String tagIdStr = articleDetail.getTagIds();
            if (StringUtils.hasText(tagIdStr)) {
                String[] tagIds = tagIdStr.split(",");
                String[] tagNames = articleDetail.getTagNames().split(",");
                List<Tag> tags = IntStream.range(0, tagIds.length)
                        .mapToObj(i -> {
                            Tag tag = new Tag();
                            tag.setId(tagIds[i]);
                            tag.setName(tagNames[i]);
                            return tag;
                        })
                        .collect(Collectors.toList());
                articleDetail.setTags(tags);
            }

            String columnIdStr = articleDetail.getColumnIds();
            if (StringUtils.hasText(columnIdStr)) {
                String[] columnIds = columnIdStr.split(",");
                String[] columnNames = articleDetail.getColumnNames().split(",");
                List<Column> columns = IntStream.range(0, columnIds.length)
                        .mapToObj(i -> {
                            Column column = new Column();
                            column.setId(columnIds[i]);
                            column.setName(columnNames[i]);
                            return column;
                        })
                        .collect(Collectors.toList());
                articleDetail.setColumns(columns);
            }
            UserInteractDTO defaultUserInteract = new UserInteractDTO();
            //判断是否是登陆用户
            if (StpKit.USER.isLogin()) {
                //获取当前访问者与文章的交互关系
                String visitUserId = StpKit.USER.getLoginIdAsString();
                String authorId = articleDetail.getUserInfo().getUserId();
                defaultUserInteract = baseMapper.selectUserInteract(visitUserId, authorId, dto.getId());
                //设置交互信息为
                articleDetail.setUserInteract(defaultUserInteract);
            }
            return articleDetail;
        }
        throw new BizException("文章不存在或已删除！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveArticle(SaveArticleDTO dto) {
        String userId = StpKit.USER.getLoginIdAsString();
        boolean isAdd = !StringUtils.hasLength(dto.getId());
        String categoryId = dto.getCategoryId();
        List<String> tagNames = dto.getTagNames();
        List<String> columnNames = dto.getColumnNames();
        //检查标题是否合法 可以在接口层校验
//        if (StringTools.hasSpecialChars(dto.getTitle())) {
//            throw new BizException("标题不能包含特殊字符！");
//        }
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
            String content = dto.getContent().trim();
            saveArticle.setDescription(content.length() > 200 ? content.substring(0, 200).replace(" ", "") : content.replace(" ", ""));
        }
        if (dto.getVisibleStatus() == 4) {
            if (!StringUtils.hasLength(dto.getVisitPassword())) {
                throw new BizException("文章可见状态为密码访问，必须输入密码！");
            }
            saveArticle.setVisitPassword(secureManager.encrypt(dto.getVisitPassword()));
        }
        saveArticle.setStatus(1);//todo 临时测试
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
                oldColumns.stream().filter(c -> c.getName().equals(columnName)).findFirst().ifPresent(column ->
                {
                    LambdaQueryWrapper<ArticleColumn> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(ArticleColumn::getArticleId, saveArticle.getId())
                            .eq(ArticleColumn::getColumnId, column.getId());
                    articleColumnMapper.delete(wrapper);
                });
            }
        }
        // 新增文章：如果用户提交的专栏名字已经在数据库则建立关联，不存在则创建后再关联
        if (isAdd) {
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
            // 移除旧的专栏关联
            for (String tagName : oldTagsToRemove) {
                oldTags.stream().filter(t -> t.getName().equals(tagName)).findFirst().ifPresent(tag -> {
                    LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(ArticleTag::getArticleId, saveArticle.getId())
                            .eq(ArticleTag::getTagId, tag.getId());
                    articleTagService.remove(wrapper);
                });
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
            Column column = columnService.getColumnByName(columnName, StpKit.USER.getLoginIdAsString());
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
    public Page<ArticleBriefVO> selectByCategoryAndRecent(Page<ArticleBriefVO> page, ArticleListDTO articleListDTO) {
        return baseMapper.selectByCategoryAndRecent(page, articleListDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void digg(String articleId) {
        diggAndUndigg(articleId, 1);
    }

    /**
     * 点赞或取消点赞
     *
     * @param articleId 文章编号
     * @param op        操作类型：1是点赞、0是取消点赞
     */
    private void diggAndUndigg(String articleId, Integer op) {
        String currentUser = StpKit.USER.getLoginIdAsString();
        Article article = getById(articleId);
        if (ObjectUtils.isEmpty(article)) {
            throw new BizException("文章不存在！");
        }
        if (op == 1) {
            //判断是否已经点过赞了，不能重复点赞
            boolean isLiked = likesService.isLiked(currentUser, article.getId(), LikeTypeEnum.ARTICLE);
            if (isLiked) {
                throw new BizException("不能重复点赞！");
            }
            Like like = new Like();
            like.setTargetId(article.getId());
            like.setUserId(currentUser);
            like.setType(LikeTypeEnum.ARTICLE.getType());
            likesService.save(like);
        } else {
            //取消点赞
            LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Like::getTargetId, article.getId())
                    .eq(Like::getUserId, currentUser)
                    .eq(Like::getType, LikeTypeEnum.ARTICLE.getType());
            likesService.remove(wrapper);
        }
    }

    @Override
    public void cancelDigg(String articleId) {
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
        Article article = getById(favorRequest.aid());
        if (ObjectUtils.isEmpty(article)) {
            throw new BizException("文章不存在！");
        }
        //检查收藏夹是否存在
        Collect collect = collectService.getCollectByUser(favorRequest.collectId(), StpKit.USER.getLoginIdAsString());
        if (ObjectUtils.isEmpty(collect)) {
            throw new BizException("收藏夹不存在！");
        }
        //保存文章到收藏夹、添加文章与收藏夹的关联
        ArticleCollect articleCollect = new ArticleCollect(favorRequest.aid(), favorRequest.collectId());
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
        Article article = getById(favorRequest.aid());
        if (ObjectUtils.isEmpty(article)) {
            throw new BizException("文章不存在！");
        }
        Collect collect = collectService.getCollectByUser(favorRequest.collectId(), StpKit.USER.getLoginIdAsString());
        if (ObjectUtils.isEmpty(collect)) {
            throw new BizException("收藏夹不存在！");
        }
        //删除文章与收藏夹的关联
        LambdaQueryWrapper<ArticleCollect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleCollect::getArticleId, favorRequest.aid()).eq(ArticleCollect::getCollectId, favorRequest.collectId());
        articleCollectMapper.delete(wrapper);
    }

    /**
     * 获取用户公开发表的文章
     *
     * @param request 请求参数
     * @return 文章列表
     */
    @Override
    public Page<ArticleBriefVO> getPublishArticle(ArticleQueryRequest request) {

        return null;
    }

    @Override
    public void deleteArticle(ArticleId dto) {
        String userId = StpKit.USER.getLoginIdAsString();
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getUserId, userId);
        wrapper.eq(Article::getId, dto.aid());
        Article article = getOne(wrapper);
        article.setStatus(ArticleStatus.RECYCLE.getCode());
        updateById(article);
        //todo 更新搜索引擎或缓存
    }

    @Override
    public Page authorRankList(AuthorRankListQuery request) {

        return page(Page.of(request.getCurrent(), request.getSize()));

    }

    @Override
    public Page userRecentArticle(UserRecentArticleQuery request) {
        //todo 需要完善，根据用户文章的可见状态进行展示，如果当前用户已经登陆，
        // 如果是粉丝则返回，没有登陆或非粉丝不返回文章，私人可见不返回
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Article::getId, Article::getTitle, Article::getDescription)
                .eq(Article::getUserId, request.getUserId())
                .orderByDesc(Article::getPublishTime);
        return page(Page.of(request.getCurrent(), request.getSize()), wrapper);
    }

    @Override
    public Page<Article> listByUser(ListByUserQuery request) {
        String key = request.getKey();
        Integer status = request.getStatus();
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 如果 status == -1，排除草稿箱（5）和回收站（6）
        if (status == -1) {
            wrapper.notIn(Article::getStatus, 5, 6);
        } else {
            // 否则，根据传入的 status 进行查询
            wrapper.eq(Article::getStatus, status);
        }
        wrapper.eq(Article::getUserId, StpKit.USER.getLoginIdAsString());
        wrapper.like(StringUtils.hasText(key), Article::getTitle, key);
        wrapper.orderByDesc(Article::getCreateTime);
        return page(Page.of(request.getCurrent(), request.getSize()), wrapper);
    }

    @Override
    public Page<Article> listByUserAndCategory(ListByUserAndCategoryQuery request) {
        String cid = request.cid();
        PageQuery pageQuery = request.pageQuery();
        Page<Article> articles = baseMapper.selectByCategory(Page.of(pageQuery.getCurrent(), pageQuery.getSize()), cid);
        return articles;
    }

    @Override
    public Page<Article> listByUserAndColumn(ListByUserAndCategoryQuery query) {
        PageQuery pageQuery = query.pageQuery();
        return baseMapper.selectByColumn(Page.of(pageQuery.getCurrent(), pageQuery.getSize()), query.cid());
    }

    @Override
    public Page<ArticleBriefVO> followList(PageQuery query) {
        String userId = StpKit.USER.getLoginIdAsString();
        return baseMapper.selectFollowList(Page.of(query.getCurrent(), query.getSize()), userId);
    }

    @Override
    public Page<ArticleBriefVO> authorArticleList(CommonPageQuery dto) {
        String userId = StpKit.USER.getLoginIdAsString();
        return baseMapper.selectAuthorArticleList(Page.of(dto.getCurrent(), dto.getSize()), userId, dto.getKeyword());

    }

    @Override
    public Page<ArticleBriefVO> getArticleListByColumn(CommonPageQuery query) {
        return baseMapper.selectArticleListByColumnId(Page.of(query.getCurrent(), query.getSize()), query.getId());
    }

    @Override
    public Page<ArticleBriefVO> findAuthorHotArticleList(CommonPageQuery query) {
        Page<ArticleBriefVO> page = new Page<>(query.getCurrent(), query.getSize());

        // 从ContentHeatCalculator获取配置参数
        int likeWeight = heatCalculator.getLikeWeight();
        int viewWeight = heatCalculator.getViewWeight();
        int collectWeight = heatCalculator.getCollectWeight();
        int commentWeight = heatCalculator.getCommentWeight();
        double gravity = heatCalculator.getGravity();

        // 调用Mapper查询
        return baseMapper.selectAuthorHotArticleList(
                page, query.getId(), likeWeight, viewWeight, collectWeight, commentWeight, gravity
        );
    }

    @Override
    public Page<ArticleBriefVO> articleComprehensiveRank(PageQuery pageQuery) {
        long current = pageQuery.getCurrent();
        long size = pageQuery.getSize();

        // 计算总页数，确保不超过50条记录
        long totalPages = (50 + size - 1) / size; // 向上取整
        if (current > totalPages) {
            // 如果请求页超过最大页数，返回空页
            return new Page<>(current, size, 0);
        }

        // 调整分页参数，确保总记录数不超过50条
        Page<ArticleBriefVO> page = new Page<>(current, size, 50);
        int likeWeight = heatCalculator.getLikeWeight();
        int viewWeight = heatCalculator.getViewWeight();
        int collectWeight = heatCalculator.getCollectWeight();
        int commentWeight = heatCalculator.getCommentWeight();
        double gravity = heatCalculator.getGravity();
        return baseMapper.findComprehensiveRank(page, likeWeight, viewWeight, collectWeight, commentWeight, gravity);

    }

    @Override
    public SingleArticleStatisticsVo singleArticleStatistics(CommonPageQuery pageQuery, Long start, Long end) {
        Page<Page> page = Page.of(pageQuery.getCurrent(), pageQuery.getSize());
        List<ArticleData> pa = baseMapper.selectSingleArticleStatistics(pageQuery.getId(), start, end);
        SingleArticleStatisticsVo vo = new SingleArticleStatisticsVo();
        List<String> dates = pa.stream()
                .map(ArticleData::getSDate)
                .map(date -> date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                .toList();
        //设置数据列表
        vo.setList(pa);
        //设置日期列表
        vo.setDateList(dates);
        // 设置 chartData
        ChartData chartData = new ChartData();
        chartData.setView(pa.stream().map(ArticleData::getViewCount).toList());
        chartData.setComment(pa.stream().map(ArticleData::getCommentCount).toList());
        chartData.setCollect(pa.stream().map(ArticleData::getCollectCount).toList());
        chartData.setLike(pa.stream().map(ArticleData::getLikeCount).toList());
        vo.setChartData(chartData);
        return vo;
    }
}
