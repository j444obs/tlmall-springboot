package fun.sherman.tlmall.controller.portal;

import fun.sherman.tlmall.common.Const;
import fun.sherman.tlmall.common.ResponseCode;
import fun.sherman.tlmall.common.ServerResponse;
import fun.sherman.tlmall.domain.User;
import fun.sherman.tlmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 门户User Controller层
 *
 * @author sherman
 */
@Controller
@RequestMapping(value = "/user")
@ResponseBody
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @param session  http session
     * @return 登录结果封装
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 用户登出
     *
     * @param session http session
     * @return 退出成功封装
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    public ServerResponse<User> logout(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            session.removeAttribute(Const.CURRENT_USER);
            return ServerResponse.buildSuccessByMsg("用户退出成功");
        } else {
            return ServerResponse.buildErrorByMsg("用户未登录，请先登录");
        }
    }

    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    public ServerResponse<String> register(@RequestBody User user) {
        return iUserService.register(user);
    }

    /**
     * 根据type进行校验str，用于输入框边输入边校验
     *
     * @param str  待校验字符
     * @param type 校验类型：username、email、phone
     * @return 返回是否校验成功
     */
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    /**
     * 获取用户信息
     *
     * @param session http session
     * @return 用户登录信息
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServerResponse.buildSuccessByData(user);
        }
        return ServerResponse.buildErrorByMsg("用户未登录");
    }

    /**
     * 忘记密码-通过用户名寻找问题
     */
    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    public ServerResponse<String> forgetGetQuestionByUsername(@RequestParam("username") String username) {
        return iUserService.selectQuestionByUsername(username);
    }

    /**
     * 忘记密码-校验问题答案是否正确
     */
    @RequestMapping(value = "forget_check_question.do", method = RequestMethod.POST)
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    /**
     * 忘记密码-重置密码
     */
    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.POST)
    public ServerResponse<String> forgetResetPassword(String username, String newPassword, String forgetToken) {
        return iUserService.forgetResetPassword(username, newPassword, forgetToken);
    }

    /**
     * 登录状态-修改密码
     */
    @RequestMapping(value = "reset_password_when_login.do", method = RequestMethod.POST)
    public ServerResponse<String> resetPasswordWhenLogin(HttpSession session, String oldPassword, String newPassword) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByMsg("用户未登录");
        }
        return iUserService.resetPasswordWhenLogin(oldPassword, newPassword, user);
    }

    /**
     * 登录状态修改用户信息
     */
    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    public ServerResponse<User> updateInformation(HttpSession session, @RequestBody User user) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.buildErrorByMsg("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if (response.isSuccess()) {
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 获取用户的所有信息
     */
    @RequestMapping(value = "get_information.do", method = RequestMethod.POST)
    public ServerResponse<User> getInformation(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildErrorByCode(ResponseCode.NEED_LOGIN.getCode(), "用户需要先登录");
        }
        return iUserService.getInformation(user.getId());
    }
}
