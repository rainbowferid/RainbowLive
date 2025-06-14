package org.admin.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.common.entity.po.CategoryInfo;
import org.common.entity.query.CategoryInfoQuery;
import org.common.entity.vo.ResponseVO;
import org.common.result.Result;
import org.common.service.CategoryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController extends ABaseController{
    @Autowired
    CategoryInfoService categoryInfoService;

    /**
     * 加载数据列表
     * @param query
     * @return
     */
    @RequestMapping("/loadDataList")
    public ResponseVO loadDataList(CategoryInfoQuery query){
        query.setOrderBy("sort asc");
        List<CategoryInfo> categoryInfoList = categoryInfoService.findListByParam(query);
        return getSuccessResponseVO(categoryInfoList);
    }

    @RequestMapping("/saveCategory")
    public ResponseVO saveCategory(@NotNull Integer pCategoryId,
                                   Integer categoryId,
                                   @NotEmpty String categoryName,
                                   @NotEmpty  String categoryCode,
                                   String icon,
                                   String background
                                   ) {
        CategoryInfo categoryInfo = new CategoryInfo();
        categoryInfo.setCategoryId(categoryId);
        categoryInfo.setCategoryName(categoryName);
        categoryInfo.setCategoryCode(categoryCode);
        categoryInfo.setpCategoryId(pCategoryId);
        categoryInfo.setIcon(icon);
        categoryInfo.setBackground(background);

        categoryInfoService.saveCategory(categoryInfo);

        return getSuccessResponseVO(null);
    }


}
