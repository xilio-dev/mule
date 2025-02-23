package cn.xilio.project.domain.vo.article.add;

import cn.xilio.project.domain.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SaveArticleDTO extends BaseDTO {
    private String title;
    private String description;
    private String content;
    private String cover;
    private Integer visibleStatus;
    private Integer publishStatus;
    private Integer creativeType;
    /**
     * visibleStatus密码访问才有效
     */
    private String visitPassword;
    /**
     * creativeType转载才有效
     */
    private String originalUrl;
    private List<String> tagNames;
    private String categoryId;
    private List<String> columnNames;
}
