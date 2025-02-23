package cn.xilio.project.controller;

import cn.xilio.project.bo.Column;
import cn.xilio.project.common.Result;
import cn.xilio.project.service.IColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xilio.cn
 * @since 2025-02-23
 */
@RestController
@RequestMapping("/column")
public class ColumnController {
    @Autowired
    private IColumnService columnService;

    @GetMapping("list")
    public Result list() {
        List<Column> list = columnService.list();
        return Result.success(list);
    }

}
