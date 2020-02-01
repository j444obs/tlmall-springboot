package fun.sherman.tlmall.controller.backend;

import fun.sherman.tlmall.common.Const;
import fun.sherman.tlmall.common.ServerResponse;
import fun.sherman.tlmall.domain.User;
import fun.sherman.tlmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author sherman
 */
@Controller
@RequestMapping("/manage/user")
@ResponseBody
public class UserManageController {
    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            User user = response.getData();
            if (user.getRole() == Const.Role.ROLE_ADMIN) {
                session.setAttribute(Const.CURRENT_USER, user);
            } else {
                return ServerResponse.buildErrorByMsg("非管理员，无法登陆");
            }
        }
        return response;
    }
}
