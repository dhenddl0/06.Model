package com.model2.mvc.web.product;

import java.sql.Date;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;


@Controller
public class ProductController {
	
	@	Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

   public ProductController() {
      System.out.println(this.getClass());
   }
   
   @Value("#{commonProperties['pageUnit']}")
   //@Value("#{commonProperties['pageUnit'] ?: 3}")
   int pageUnit;
   
   @Value("#{commonProperties['pageSize']}")
   //@Value("#{commonProperties['pageSize'] ?: 2}")
   int pageSize;

   
   @RequestMapping("/addProductView.do")
   public String addProductView() throws Exception{
	   
	   System.out.println("/addProductView.do");
	   
	   return "forward:/product/addProductView.jsp";
   }
   
   @RequestMapping("/addProduct.do")
   public String addProduct(@ModelAttribute("product") Product product, Model model, ServletRequest request) throws Exception {
	   
	   System.out.println("/addProduct.do");
	   
	   String[] date = request.getParameter("manuDate").split("-");
	   
	   product.setManuDate(date[0] + date[1] + date[2]);
	   
	   productService.addProduct(product);
	   model.addAttribute("product", product);
	   
	   return "forward:/product/addProduct.jsp";
   }
   
   @RequestMapping("/getProduct.do")
   public String getProduct(@RequestParam("prodNo") int prodNo, 
	   									@RequestParam(value = "menu", required = false) String menu,
		   										Model model, ServletRequest request,
		   										HttpServletResponse response
		   										) throws Exception {
	   
	   System.out.println("/getProduct.do");
	   
	   Product product = productService.getProduct(prodNo);
	  
	   model.addAttribute("product", product);
	   model.addAttribute("menu", menu);
	   
	   //request.setAttribute("menu", request.getParameter("menu"));
	   
	   String history = null;
       Cookie[] cookies = ((HttpServletRequest) request).getCookies();
       if (cookies != null && cookies.length > 0) {
           for (int i = 0; i < cookies.length; i++) {
               Cookie cookie = cookies[i];
               if (cookie.getName().equals("history")) {
                   history = cookie.getValue();
               }
           }
       }
       
       // 현재 열람한 상품 번호를 기존 열람 기록에 추가
       if (history == null) {
           history = Integer.toString(prodNo);
       } else {
           history += "/" + prodNo;
       }
       
       // 쿠키에 열람 기록을 저장
       Cookie historyCookie = new Cookie("history", history);
       response.addCookie(historyCookie);

	 
	   return "forward:/product/getProduct.jsp";
   }


   @RequestMapping("/updateProductView.do")
   public String updateProductView(@RequestParam("prodNo") int prodNo, Model model, @RequestParam("menu") String menu) throws Exception{
	   
	   System.out.println("/updateProductView.do");
	   
	   Product product = productService.getProduct(prodNo);
	   
	   model.addAttribute("product", product);
	   model.addAttribute("menu", menu);
	   
	   return "forward:/product/updateProductView.jsp";
	   
   }
   
   @RequestMapping("/updateProduct.do")
   public String updateProduct(@ModelAttribute("prodNo") Product product , Model model, ServletRequest request,
		  /* HttpSession session*/ @RequestParam("menu") String menu
		   ) throws Exception{
	   
	   System.out.println("/updateProduct.do");
	   System.out.println("menu = " +menu);
	   
	  // Product product = productService.getProduct(prodNo);
	   
	   
	   product.setRegDate(Date.valueOf(request.getParameter("regDate2")));
	  
	   productService.updateProduct(product);   
	   
	   
	   model.addAttribute("product", product);
	   model.addAttribute("menu", menu);
	   
		/*
		 * int sessionNo = ((Product)session.getAttribute("product")).getProdNo();
		 * if(sessionNo == product.getProdNo()) { session.setAttribute("product",
		 * product); }
		 */
	   
	   return "forward:/product/getProduct.jsp";
   }
   
   @RequestMapping("/listProduct.do")
   public String listProduct(@ModelAttribute("search") Search search, Model model, ServletRequest request,
		   @RequestParam("menu") String menu
		   ) throws Exception{
	   
	   //request.getAttribute("menu");

	   
	   System.out.println("/listProduct.do");
	   
	   if(search.getCurrentPage() == 0) {
		   search.setCurrentPage(1);
	   }
	   search.setPageSize(pageSize);
	   
	   Map<String, Object> map = productService.getProductList(search);
	   
	   Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
	   
	   System.out.println(resultPage);
	     
	   
	   model.addAttribute("list", map.get("list"));
	   model.addAttribute("resultPage", resultPage);
	   model.addAttribute("search", search);
	   model.addAttribute("menu", menu);
	   
	   return "forward:/product/listProduct.jsp";
   }
   
}