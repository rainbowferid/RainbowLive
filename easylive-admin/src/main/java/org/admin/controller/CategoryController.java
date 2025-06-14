package org.admin.controller;

import org.common.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController extends ABaseController{

    @RequestMapping("/loadDataList")
    public Result loadDataList(){
        return Result.success();
    }


}
