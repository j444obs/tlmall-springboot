package fun.sherman.tlmall.controller.backend;

import fun.sherman.tlmall.common.Const;
import fun.sherman.tlmall.common.ResponseCode;
import fun.sherman.tlmall.common.ServerResponse;
import fun.sherman.tlmall.domain.Product;
import fun.sherman.tlmall.domain.User;
import fun.sherman.tlmall.service.IFileService;
import fun.sherman.tlmall.service.IProductService;
import fun.sherman.tlmall.service.IUserService;
import fun.sherman.tlmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sherman
 */
@Controller
@RequestMapping("/manage/product")
@ResponseBody
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    /**
     * 保存或者更新商品：根据传入的product是否有product_id
     *
     * @param session http session
     * @param product 传入的product对象
     * @return 保存或者更新商品结果信息
     */
    @RequestMapping(value = "save_product.do", method = RequestMethod.POST)
    public ServerResponse saveProduct(HttpSession session, @RequestBody Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), "请先登录管理员账户");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.saveOrUpdateProduct(product);
        } else {
            return ServerResponse.buildErrorByMsg("无权限操作");
        }
    }

    /**
     * 修改商品的status信息
     *
     * @param session   http session
     * @param productId 商品 id
     * @param status    商品 状态码
     * @return 修改商品status是否成功信息
     */
    @RequestMapping(value = "set_product_status.do", method = RequestMethod.POST)
    public ServerResponse<String> setProductStatus(HttpSession session,
                                                   @RequestParam("product_id") Integer productId,
                                                   @RequestParam("status") Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), "请先登录管理员账户");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.setProductStatus(productId, status);
        } else {
            return ServerResponse.buildErrorByMsg("无权限操作");
        }
    }

    /**
     * 获取商品详细信息
     *
     * @param session   http session
     * @param productId 商品id
     * @return 返回商品详情信息
     */
    @RequestMapping(value = "details.do", method = RequestMethod.POST)
    public ServerResponse getProductDetails(HttpSession session,
                                            @RequestParam("product_id") Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), "请先登录管理员账户");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.manageProductDetail(productId);
        } else {
            return ServerResponse.buildErrorByMsg("无权限操作");
        }
    }

    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    public ServerResponse getList(HttpSession session,
                                  @RequestParam(value = "page_num", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "page_size", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), "请先登录管理员账户");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.getProductList(pageNum, pageSize);
        } else {
            return ServerResponse.buildErrorByMsg("无权限操作");
        }
    }

    @RequestMapping(value = "search.do", method = RequestMethod.POST)
    public ServerResponse productSearch(HttpSession session,
                                        @RequestParam(value = "product_name", required = false) String productName,
                                        @RequestParam(value = "product_id", required = false) Integer productId,
                                        @RequestParam(value = "page_num", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "page_size", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), "请先登录管理员账户");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.searchByProductOrId(productName, productId, pageNum, pageSize);
        } else {
            return ServerResponse.buildErrorByMsg("无权限操作");
        }
    }

    @RequestMapping(value = "upload.do", method = RequestMethod.POST)
    public ServerResponse upload(HttpSession session, HttpServletRequest request,
                                 @RequestParam(value = "upload_file", required = false) MultipartFile multipartFile) {
        // 相当于在web目录下（和WEB-INF平级）创建了一个upload文件夹，但是该upload文件夹的创建不应该依赖业务逻辑
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), "请先登录管理员账户");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath(Const.UPLOAD_PATH);
            String targetFilename = iFileService.upload(multipartFile, path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFilename;
            Map<String, String> fileMap = new HashMap<>();
            fileMap.put("uri", targetFilename);
            fileMap.put("url", url);
            return ServerResponse.buildSuccessByData(fileMap);
        } else {
            return ServerResponse.buildErrorByMsg("无权限操作");
        }
    }

    @RequestMapping(value = "richtext_upload.do", method = RequestMethod.POST)
    public Map<String, Object> richtextUpload(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(value = "upload_file", required = false) MultipartFile multipartFile) {
        Map<String, Object> resultMap = new HashMap<>();
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            resultMap.put("success", false);
            resultMap.put("msg", "请登录管理员账号");
            return resultMap;
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath(Const.UPLOAD_PATH);
            String targetFilename = iFileService.upload(multipartFile, path);
            if (StringUtils.isBlank(targetFilename)) {
                resultMap.put("success", false);
                resultMap.put("msg", "上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFilename;
            resultMap.put("success", true);
            resultMap.put("msg", "上传成功");
            resultMap.put("file_path", url);
            response.addHeader("Access-Controller-Allow-Headers", "X-File-Name");
            return resultMap;
        } else {
            resultMap.put("success", false);
            resultMap.put("msg", "无权限操作");
            return resultMap;
        }
    }
}