package cn.stu.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cn.stu.model.User;
import cn.stu.neu.util.ChangeToJSON;
import cn.stu.neu.util.Judge;
import cn.stu.service.UserService;

/**
 * 用户注册、登录、退出
 */
@WebServlet({ "/login.action", "/logout.action","/registUser.action","/forgetPasswordForMobile.action",
	"/loginForMobile.action", "/logoutForMobile.action","/registUserForMobile.action"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 8L;
    public UserServlet() {
        super();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getServletPath();
		boolean isMobile = Judge.isForMobile(url);
		UserService service = new UserService();
		if(url.equals("/login.action") || url.equals("/loginForMobile.action")){
			//用户登录
			String userName = request.getParameter("username");
			String pw = request.getParameter("password");
			if(userName.length()!=0 && pw.length()!=0){
				Map<String,String> map = service.loginUser(userName, pw);
				if(map!=null){
					HttpSession session = request.getSession();
					session.setAttribute("loginUserName", map.get("user_name"));
					session.setAttribute("loginUserId", map.get("user_id"));
					if(Integer.parseInt(map.get("user_kind"))==User.MANAGER){
						session.setAttribute("loginUserKind", "m");
					}else{
						session.setAttribute("loginUserKind", "u");
						session.setAttribute("shoppingcarNum", map.get("shoppingcarNum"));
					}
					if(isMobile){
						Map<String,String> mapLogin = new HashMap<>();
						mapLogin.put("loginUserName", map.get("user_name"));
						mapLogin.put("loginUserId", map.get("user_id"));
						mapLogin.put("sessionId", session.getId());
						mapLogin.put("orderNum", map.get("orderNum"));
						if(Integer.parseInt(map.get("user_kind"))==User.MANAGER){
							ChangeToJSON.parseMap("ok", "m", mapLogin, request, response);
							mapLogin.put("shoppingcarNum", "0");
						}else{
							mapLogin.put("shoppingcarNum", map.get("shoppingcarNum"));
							ChangeToJSON.parseMap("ok", "u", mapLogin, request, response);
						}
						return;
					}
				}else{
					//失败
					if(isMobile){
						ChangeToJSON.parseMap("no", "loginFalse", null, request, response);
						return;
					}
				}
			}
		}else if(url.equals("/logout.action") || url.equals("/logoutForMobile.action")){
			//用户退出
			request.getSession().invalidate();
		}else if(url.equals("/registUser.action") || url.equals("/registUserForMobile.action")){
			//用户注册
			String userName = request.getParameter("username");
			String pw1 = request.getParameter("password1");
			String pw2 = request.getParameter("password2");
			boolean res = false;
			if(userName.length()!=0 && pw1.length()!=0 && pw2.length()!=0){
				if(pw1.equals(pw2)){
					res = service.registUser(userName,pw1);
				}
			}
			System.out.println("registResult:"+res);
			if(isMobile){
				if(res){
					ChangeToJSON.parseMap("ok", "registSuccess", null, request, response);
				}else{
					ChangeToJSON.parseMap("no", "registFalse", null, request, response);
				}
				return;
			}
			if(res){
				request.setAttribute("msg", "注册成功O(∩_∩)O~~");
				request.setAttribute("url", "index.action");
				request.getRequestDispatcher("/prompt.jsp").forward(request, response);
			}else{
				request.setAttribute("msg", "注册失败！/(ㄒoㄒ)/~~");
				request.setAttribute("url", "index.action");
				request.getRequestDispatcher("/prompt.jsp").forward(request, response);
			}
			service.close();
			return;
		}else if(url.equals("/forgetPasswordForMobile.action")){
			String userName = request.getParameter("username");
			String pw = request.getParameter("password");
			boolean res = service.changePassword(userName,pw);
			if(res){
				ChangeToJSON.parseMap("ok", "success", null, request, response);
			}else{
				ChangeToJSON.parseMap("no", "false", null, request, response);
			}
			service.close();
			return;
		}
		service.close();
		response.sendRedirect(request.getContextPath()+"/index.action");
	}
}
