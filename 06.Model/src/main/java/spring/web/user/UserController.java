package spring.web.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;


@Controller
public class UserController {
	
	@	Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

   public UserController() {
      System.out.println(this.getClass());
   }
   
   @Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

   @RequestMapping("/addUserView.do")
   public String addUserView() throws Exception {
	   
      System.out.println("\n::==>addUserView");
      
      return "redirect:/user/addUserView.jsp";
      }
   
   @RequestMapping("/addUser.do")
   public String addUser(@ModelAttribute("user") User user ) throws Exception {
	   
      System.out.println("\n::==>addUser.do");
      
      return "redirect:/user/loginView.jsp";
      }
   
   @RequestMapping("/getUser.do")
   public String getUser(@RequestParam("userId") String userId, Model model ) throws Exception {
	   
      System.out.println("\n::==>getUser.do");
      
      User user = userService.getUser(userId);
      
      model.addAttribute("user", user);
      
      return "forward:/user/getUser.jsp";
      }
   
   @RequestMapping("/updateUserView.do")
   public String updateUserView(@RequestParam("userId") String userId, Model model ) throws Exception {
	   
      System.out.println("\n::==>updateUserView.do");
      
      User user = userService.getUser(userId);
      
      model.addAttribute("user", user);
      
      return "forward:/user/updateUser.jsp";
      }
   
   @RequestMapping("/updateUser.do")
   public String updateUser(@ModelAttribute("user") User user, Model model, HttpSession session) throws Exception {
	   
      System.out.println("\n::==>updateUser.do");
      
      userService.updateUser(user);
      
      String sessionId = ((User) session.getAttribute("user")).getUserId();
      if(sessionId.equals(user.getUserId())) {
    	  session.setAttribute("user", user);
      }
      
      return "redirect:/getUser.do?userId="+user.getUserId();
      }
   
   		@RequestMapping("/login.do")
   		public String login(@ModelAttribute("user") User user, HttpSession session ) throws Exception {
   			
   			System.out.println("/login.do");
   			
   			User conUser = userService.getUser(user.getUserId());
   			
   			if(user.getPassword().equals(conUser.getPassword())) {
   				session.setAttribute("user", conUser);
   			}
   			
   				return "redirect:/index.jsp";
   		}
   		
   		@RequestMapping("/logout.do")
   		public String logout(HttpSession session) throws Exception{
   			
   			System.out.println("/logout.do");
   			
   			return "redirect:/index.jsp";
   		}
   		
   		@RequestMapping("/checkDuplication.do")
   		public String checkDuplication(@RequestParam("userId") String userId, Model model ) throws Exception{
   			
   			System.out.println("/checkDuplication.do");
   			
   			boolean result = userService.checkDuplication(userId);
   			
   			model.addAttribute("result", new Boolean(result));
   			model.addAttribute("userId", userId);
   			
   			return "forward:/user/checkDuplication.jsp";
   		}
   		
   		@RequestMapping("listUser.do")
   		public String listUser(@ModelAttribute("search") Search search, Model model, HttpServletRequest request, int pageSize, int pageUnit ) throws Exception{

   		System.out.println("/listUser.do");
   		
   		if(search.getCurrentPage() == 0 ) {
   			search.setCurrentPage(1);
   		}
   		search.setPageSize(pageSize);
   		
   		Map<String, Object> map = userService.getUserList(search);
   		
   		Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
   		
   		System.out.println(resultPage);
   		
   		model.addAttribute("list", map.get("list"));
   		model.addAttribute("resultPage", resultPage);
   		model.addAttribute("search", search);
   		
   		return "forward:/user/listUser.jsp";
   		}
}