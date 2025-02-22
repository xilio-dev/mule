package cn.xilio.project;

import cn.xilio.project.domain.ArticleListDTO;
import cn.xilio.project.domain.vo.ArticleBriefVO;
import cn.xilio.project.service.IArticleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class SetupTests {
    @Autowired
    private IArticleService articleService;

    @Test
    void contextLoads() throws Exception {
        ArticleListDTO dto = new ArticleListDTO();
        dto.setCategoryId(0L);
       dto.setCurrent(0L);
       dto.setSize(10L);
        IPage<ArticleBriefVO> articleBriefVOIPage = articleService.listByCategory(dto);
        System.out.println(articleBriefVOIPage);
    }

}
