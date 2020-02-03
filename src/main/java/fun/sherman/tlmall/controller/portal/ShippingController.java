package fun.sherman.tlmall.controller.portal;

import fun.sherman.tlmall.common.Const;
import fun.sherman.tlmall.common.ResponseCode;
import fun.sherman.tlmall.common.ServerResponse;
import fun.sherman.tlmall.domain.Shipping;
import fun.sherman.tlmall.domain.User;
import fun.sherman.tlmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author sherman
 */
@Controller
@RequestMapping("/shipping")
@ResponseBody
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    public ServerResponse add(HttpSession session, @RequestBody Shipping shipping) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.add(user.getId(), shipping);
    }

    @RequestMapping(value = "delete.do", method = RequestMethod.POST)
    public ServerResponse delete(HttpSession session, @RequestParam("shipping_id") Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        // 注意防止横向越权，必须传递userId参数
        return iShippingService.deleteByUserIdAndShippingId(user.getId(), shippingId);
    }

    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    public ServerResponse update(HttpSession session, @RequestBody Shipping shipping) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.updateShippingSelective(user.getId(), shipping);
    }

    @RequestMapping(value = "select.do", method = RequestMethod.POST)
    public ServerResponse<Shipping> select(HttpSession session, @RequestParam("shipping_id") Integer shippingId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        // 注意防止横向越权
        return iShippingService.selectByShippingIdAndUserId(user.getId(), shippingId);
    }

    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "page_num", defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.listAllShippingInfo(user.getId(), pageNum, pageSize);
    }
}
