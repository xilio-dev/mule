package com.stackoak.stackoak.application.controller.admin;


import com.stackoak.stackoak.application.service.column.IColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-23
 */
@RestController
@RequestMapping("/column")
public class AdminColumnApi {
    @Autowired
    private IColumnService columnService;



}
