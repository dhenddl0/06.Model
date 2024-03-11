package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;


/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })

@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })

public class ProductServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		//product.setProdNo(1000);
		product.setProdName("혜정");
		product.setProdDetail("윤상");
		product.setManuDate("20240305");
		product.setPrice(Integer.parseInt("100"));
		product.setFileName("fileName");
		
		productService.addProduct(product);
		
	

		//==> console 확인
		System.out.println(product);
		
		//==> API 확인
		//Assert.assertEquals("testProdNo", product.getProdNo());
		Assert.assertEquals("혜정", product.getProdName());
		Assert.assertEquals("윤상", product.getProdDetail());
		Assert.assertEquals("20240305", product.getManuDate());
		Assert.assertEquals(Integer.parseInt("100"), product.getPrice());
		//Assert.assertEquals("fileName", product.getFileName());
	}


// @Test 
 public void testGetProduct() throws Exception {
 
 Product product = new Product(); //==> 필요하다면... // user.setUserId("testUserId"); //
 product.setProdName("testProdName"); // user.setPassword("testPasswd"); //
 product.setProdDetail("testProdDetail"); 
// 		+ "// user.setPhone("111-2222-3333"); //
 product.setManuDate("20240304"); // user.setEmail("test@test.com");
 product.setPrice(2000000);
// product.setFileName("fileName");
 

 
 //==> console 확인 //
 System.out.println(product);
 
 //==> API 확인 Assert.assertEquals("testUserId", user.getUserId());
 Assert.assertEquals("testProdName", product.getProdName());
 Assert.assertEquals("testProdDetail", product.getProdDetail());
 Assert.assertEquals("20240304", product.getManuDate());
 Assert.assertEquals(2000000, product.getPrice());
 //Assert.assertEquals("fileName", product.getFileName());
 
// Assert.assertNotNull(productService.getProduct(Integer.parseInt("user02")));
// Assert.assertNotNull(productService.getProduct(Integer.parseInt("user05")));
 }




	 //@Test 
	 	
	 public void testUpdateProduct() throws Exception{
	  
	Product product = productService.getProduct(10051); 
	 Assert.assertNotNull(product);
	  
	 Assert.assertEquals("혜정", product.getProdName());
	 Assert.assertEquals("윤상", product.getProdDetail());
	 Assert.assertEquals("20240305", product.getManuDate());
	 Assert.assertEquals(100, product.getPrice());
	 //Assert.assertEquals("fileName", product.getFileName());
	  
	  product.setProdName("change"); 
	  product.setPrice(55055);
	  product.setProdDetail("changeDetail"); 
	  product.setManuDate("20220505");
	  
	  productService.updateProduct(product);
	  
	 // product = productService.getProduct(Integer.parseInt("testProdNo")); 
	 // Assert.assertNotNull(product);
	  
	  //==> console 확인 //
	  System.out.println(product);
	  
	  //==> API 확인 
	  Assert.assertEquals("change", product.getProdName());
	  Assert.assertEquals("20220505", product.getManuDate());
	  Assert.assertEquals("change", product.getProdName());
	  Assert.assertEquals("changeDetail", product.getProdDetail()); }

  
 
 //@Test 
 public void testCheckDuplication() throws Exception{
 
	 Product product = new Product();
 //==> 필요하다면... // User user = new User(); // user.setUserId("testUserId"); //
 product.setProdName("혜정"); // user.setPassword("testPasswd"); //
 product.setProdDetail("윤상"); 
 product.setManuDate("20240305"); // user.setEmail("test@test.com"); // //
 product.setPrice(100);
 productService.addProduct(product);
 
 
 //==> console 확인
 System.out.println(productService.checkDuplication(10051));
 //System.out.println(productService.checkDuplication(Integer.parseInt("10040"+System.currentTimeMillis()) ) );
 
 //==> API 확인 Assert.assertFalse( userService.checkDuplication("testUserId")
 //); 
 //Assert.assertTrue(
// productService.checkDuplication(Integer.parseInt("10040"+System.currentTimeMillis()) ) );
 
 }

 
 //==> 주석을 풀고 실행하면.... 
 	
 	//@Test 
 	
 	public void testGetProductListAll() throws Exception{
 
	 Search search = new Search(); search.setCurrentPage(1);
	 search.setPageSize(3); Map<String,Object> map =
	 productService.getProductList(search);
	 
	 List<Object> list = (List<Object>)map.get("list"); 
	 Assert.assertEquals(3,list.size());
	 
	 //==> console 확인 //
	 System.out.println(list);
	 
	 Integer totalCount = (Integer)map.get("totalCount");
	 System.out.println(totalCount);
	 
	 System.out.println("=======================================");
	 
	 search.setCurrentPage(1);
	 search.setPageSize(3);
	 search.setSearchCondition("0");
	 search.setSearchKeyword(""); 
	 map = productService.getProductList(search);
	 
	 list = (List<Object>)map.get("list"); Assert.assertEquals(3, list.size());
	 
	 //==> console 확인 
	 System.out.println(list);
	 
	 totalCount = (Integer)map.get("totalCount"); 
	 System.out.println(totalCount);
	 }
	
 
 
 // @Test 
  
  public void testGetProductListByProdNo() throws Exception{
  
  Search search = new Search(); 
  search.setCurrentPage(1);
  search.setPageSize(3); 
  search.setSearchCondition("0");
  search.setSearchKeyword("10005"); 
  Map<String,Object> map = productService.getProductList(search);
  
  List<Object> list = (List<Object>)map.get("list"); 
  Assert.assertEquals(1, list.size());
  
  //==> console 확인 
  System.out.println(list);
  
  Integer totalCount = (Integer)map.get("totalCount");
  System.out.println(totalCount);
  
  System.out.println("=======================================");
  
  search.setSearchCondition("0");
  search.setSearchKeyword(""+System.currentTimeMillis()); 
  map = productService.getProductList(search);
  
  list = (List<Object>)map.get("list"); 
  Assert.assertEquals(0, list.size());
  
  //==> console 확인 //
  System.out.println(list);
  
  totalCount = (Integer)map.get("totalCount"); 
  System.out.println(totalCount);
  }


  
	 @Test 
	  
	  public void testGetProductListByProdName() throws Exception{
	  
	  Search search = new Search(); search.setCurrentPage(1);
	  search.setPageSize(3); search.setSearchCondition("1");
	  search.setSearchKeyword("양말"); 
	  Map<String,Object> map = productService.getProductList(search);
	  
	  List<Object> list = (List<Object>)map.get("list"); 
	  Assert.assertEquals(3, list.size());
	  
	  //==> console 확인 
	  System.out.println(list);
	  
	  Integer totalCount = (Integer)map.get("totalCount");
	  System.out.println(totalCount);
	  
	  System.out.println("=======================================");
	  
	  search.setSearchCondition("1");
	  search.setSearchKeyword(""+System.currentTimeMillis()); 
	  map = productService.getProductList(search);
	  
	  list = (List<Object>)map.get("list");
	  Assert.assertEquals(0, list.size());
	  
	  //==> console 확인 
	  System.out.println(list);
	  
	  totalCount = (Integer)map.get("totalCount"); 
	  System.out.println(totalCount);
	  } }
	 