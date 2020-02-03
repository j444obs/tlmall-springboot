package fun.sherman.tlmall.controller.backend;

import com.github.pagehelper.PageInfo;
import fun.sherman.tlmall.common.Const;
import fun.sherman.tlmall.common.ResponseCode;
import fun.sherman.tlmall.common.ServerResponse;
import fun.sherman.tlmall.domain.User;
import fun.sherman.tlmall.service.IOrderService;
import fun.sherman.tlmall.service.IUserService;
import fun.sherman.tlmall.vo.OrderVo;
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
@RequestMapping("/manage/order/")
@ResponseBody
public class OrderManageController {
    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    public ServerResponse<PageInfo> manageOrderList(HttpSession session,
                                                    @RequestParam(value = "page_num", defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.manageOrderList(pageNum, pageSize);
        } else {
            return ServerResponse.buildErrorByMsg("无管理员权限操作");
        }
    }

    @RequestMapping(value = "details.do", method = RequestMethod.POST)
    public ServerResponse<OrderVo> manageOrderDetails(HttpSession session, @RequestParam("order_no") Long orderNo) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.manageOrderDetails(orderNo);
        } else {
            return ServerResponse.buildErrorByMsg("无管理员权限操作");
        }
    }

    @RequestMapping(value = "search.do", method = RequestMethod.POST)
    public ServerResponse<PageInfo> manageOrderSearch(HttpSession session, @RequestParam("order_no") Long orderNo,
                                                      @RequestParam(value = "page_num", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "page_size", defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.mangeOrderSearch(orderNo, pageNum, pageSize);
        } else {
            return ServerResponse.buildErrorByMsg("无管理员权限操作");
        }
    }

    @RequestMapping(value = "send_goods.do", method = RequestMethod.POST)
    public ServerResponse manageSendGoods(HttpSession session, @RequestParam("order_no") Long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.manageSendGoods(orderNo);
        } else {
            return ServerResponse.buildErrorByMsg("无管理员权限操作");
        }
    }
}
