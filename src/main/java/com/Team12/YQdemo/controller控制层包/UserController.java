package com.Team12.YQdemo.controller控制层包;

import com.Team12.YQdemo.domain实体类包.User;
import com.Team12.YQdemo.domain实体类包.uClass;
import com.Team12.YQdemo.service业务逻辑接口包.UserService;
import com.Team12.YQdemo.utils存放工具类.UserResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @PostMapping("/login")
    public UserResult<User> loginController(@RequestParam String uname, @RequestParam String password){
        User user = userService.loginService(uname, password);
        if(user!=null){
            if(user.getBaned() == true){
                return UserResult.error("3","该账号已被封禁！");
            }
            else{
                return UserResult.success(user,"登录成功！");
            }
        }else{
            return UserResult.error("1","账号或密码错误！");
        }
    }

    @PostMapping("/register")
    public UserResult<User> registController(@RequestParam String uname ,@RequestParam String nickname ,@RequestParam String password ,@RequestParam String grade ,@RequestParam String umajor ,@RequestParam String uclass){
        User user = userService.registService(uname , nickname , password , grade , umajor , uclass);
        if(user!=null){
            return UserResult.success(user,"注册成功！");
        }else{
            return UserResult.error("2","用户名已存在！");
        }
    }

    @PostMapping("/changePassword")
    public UserResult<User> changePasswordController(@RequestParam long uid , @RequestParam String oldPassword , @RequestParam String newPassword){
        User user = userService.changePasswordService(uid , oldPassword , newPassword);
        if(user!=null){
            return UserResult.success(user,"更改密码成功！");
        }
        else{
            return UserResult.error("4","旧密码不匹配！");
        }
    }

    @PostMapping("/changeInformation")
    public UserResult<User> changeInformationService(@RequestParam long uid , @RequestParam String newYear , @RequestParam String newMajor , @RequestParam String newClass , @RequestParam String newNickname){
        User user = userService.changeInformationService(uid , newYear , newMajor , newClass , newNickname);
        if(user!=null){
            return UserResult.success(user,"修改资料成功！");
        }
        else{
            return UserResult.error("5","资料没有变更！");
        }
    }
    @PostMapping("/users")//后台管理账户列表
    public List<User>  userListService(@RequestParam int page){
        List<User> userList = userService.userListService();
        for(int i=0;i<8;i++){

        }
        return userList;
    }

    @PostMapping("/users/ban")//后台封禁用户
    public UserResult<User>  banUserService(@RequestParam String uname , @RequestParam boolean ban){
        User user = userService.banUserService(uname , ban);
        return UserResult.success(user , "变更账户状态成功！");
    }

    @PostMapping("/classSelect/classUserList")//前台搜索班级班级同学列表,前台多判断在不在这个班级中
    public LinkedHashMap<String , String> classSelectUserList(@RequestParam long uid , @RequestParam String umajor , @RequestParam String grade , @RequestParam String uclass){
        String inOrNot = userService.classUserInOrNotService(umajor , grade, uclass , uid);
        List<uClass> userList = userService.classListService(umajor , grade , uclass);
        LinkedHashMap<String , String> result = new LinkedHashMap<>();
        result.put("inOrNot" , inOrNot);
        result.put("count" , Integer.toString(userList.size()));
        for(int i=0;i<userList.size();i++){
            result.put(  userList.get(i).getSno(), userList.get(i).getRealname());
        }
        return result;//返回该学生在不在班级里,学生个数,学号姓名
    }

    @PostMapping("/classSelect/classUserList/join")//前台加入班级
    public int classSelectJoin(@RequestParam String umajor ,@RequestParam String grade ,@RequestParam String uclass , @RequestParam long uid , @RequestParam String sno , @RequestParam String realname){
        int classUser= userService.classJoinService(umajor , grade , uclass , uid , sno , realname);
        return classUser;
    }
    @PostMapping("/manager/classUserList")//后台班级管理班级同学列表
    public LinkedHashMap<String , String>  managerClassUserList(@RequestParam String umajor , @RequestParam String grade , @RequestParam String uclass){
        List<uClass> userList = userService.classListService(umajor , grade , uclass);
        LinkedHashMap<String , String> result = new LinkedHashMap<>();
        result.put("count" , Integer.toString(userList.size()));
        for(int i=0;i<userList.size();i++){
            result.put(userList.get(i).getSno() , userList.get(i).getRealname());
        }
        return result;//返回学生个数接着是学号姓名
    }
    @PostMapping("manager/classUserList/out")//后台班级管理踢出成员
    public String  managerClassUserOut(@RequestParam String sno){
        User user =userService.classOutService(sno);
        return "提出成员成功！";
    }
}
