package fun.sherman.tlmall.controller.backend;

import fun.sherman.tlmall.common.Const;
import fun.sherman.tlmall.common.ServerResponse;
import fun.sherman.tlmall.domain.User;
import fun.sherman.tlmall.service.ICategoryService;
import fun.sherman.tlmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author sherman
 */
@Controller
@RequestMapping("/manage/category")
@ResponseBody
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加分类
     *
     * @param session      http session
     * @param categoryName 新分类名称
     * @param parentId     parent id
     * @return 添加是否成功信息
     */
    @RequestMapping(value = "add_category.do", method = RequestMethod.GET)
    public ServerResponse addCategory(HttpSession session,
                                      @RequestParam("category_name") String categoryName,
                                      @RequestParam(value = "parent_id", defaultValue = "0") Integer parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByMsg("用户未登录，请先登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.buildErrorByMsg("非管理员，权限不足");
        }
    }

    /**
     * 修改分类名称
     *
     * @param session      http session
     * @param categoryId   category id
     * @param categoryName 修改分类后的名称
     * @return 修改分类是否成功信息
     */
    @RequestMapping(value = "set_category_name.do", method = RequestMethod.GET)
    public ServerResponse setCategoryName(HttpSession session,
                                          @RequestParam("category_id") Integer categoryId,
                                          @RequestParam("category_name") String categoryName) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByMsg("用户未登录，请先登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            // 更新categoryName
            return iCategoryService.setCategoryName(categoryId, categoryName);
        } else {
            return ServerResponse.buildErrorByMsg("非管理员，权限不足");
        }
    }

    /**
     * 查询子节点的category信息，不递归，保持平级
     *
     * @param session    http session
     * @param categoryId 待查询子节点的category id
     * @return 查询结果信息
     */
    @RequestMapping(value = "get_child_category.do", method = RequestMethod.GET)
    ServerResponse getChildParallelCategory(HttpSession session,
                                            @RequestParam(value = "category_id", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByMsg("用户未登录，请先登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.getChildParallelCategory(categoryId);
        } else {
            return ServerResponse.buildErrorByMsg("非管理员，权限不足");
        }
    }

    /**
     * 递归查询子节点的category信息
     *
     * @param session    http session
     * @param categoryId 待查询节点的id
     * @return 所有子节点的id信息
     */
    @RequestMapping(value = "get_all_category.do", method = RequestMethod.GET)
    ServerResponse getAllChildCategory(HttpSession session,
                                       @RequestParam(value = "category_id", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByMsg("用户未登录，请先登录");
        }
        // 校验是否是管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            // 递归查询所有子分类
            return iCategoryService.getAllChildCategory(categoryId);
        } else {
            return ServerResponse.buildErrorByMsg("非管理员，权限不足");
        }
    }
}