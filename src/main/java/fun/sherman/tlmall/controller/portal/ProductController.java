package fun.sherman.tlmall.controller.portal;

import com.github.pagehelper.PageInfo;
import fun.sherman.tlmall.common.ServerResponse;
import fun.sherman.tlmall.service.IProductService;
import fun.sherman.tlmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sherman
 */
@RequestMapping("/product/")
@Controller
@ResponseBody
public class ProductController {

    @Autowired
    IProductService iProductService;

    @RequestMapping(value = "detail.do", method = RequestMethod.POST)
    public ServerResponse<ProductDetailVo> detail(@RequestParam("product_id") Integer productId) {
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "category_id", required = false) Integer categoryId,
                                         @RequestParam(value = "page_num", defaultValue = "1") Integer PageNum,
                                         @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize,
                                         @RequestParam(value = "order_by", defaultValue = "") String orderBy) {
        return iProductService.getProductByKeywordAndCategoryId(keyword, categoryId, PageNum, pageSize, orderBy);
    }
}
