<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% System.out.println("jsp start"); %>

<%-- <%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.domain.Product" %>
<%@ page import="com.model2.mvc.service.domain.User" %>
<%@ page import="com.model2.mvc.common.*" %>

<%@ page import="java.util.List"  %>

<%@ page import="com.model2.mvc.service.domain.Product" %>
<%@ page import="com.model2.mvc.common.Search" %>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>

<%
   List<Product> list = (List<Product>)request.getAttribute("list");
   Page resultPage = (Page)request.getAttribute("resultPage");
   
   Search search = (Search)request.getAttribute("search");
   System.out.println(search);
   
   String searchCondition = CommonUtil.null2str(search.getSearchCondition());
   String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
   
   System.out.println("searchCondition : " + searchCondition);
   System.out.println("searchKeyword : " + searchKeyword);
%> --%>


<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">

<!-- 자바 스크립트 부분
function fncGetProductList(currentPage){
   document.getElementById("currentPage").value = currentPage;
   document.detailForm.submit();
}
-->

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=${param.menu}" method="post">


<table width="100%" height="37" border="0" cellpadding="0"   cellspacing="0">
   <tr>
      <td width="15" height="37">
         <img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
      </td>
      <td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
         <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
               <td width="93%" class="ct_ttl01">
          
      <%--     <%    
          if(request.getParameter("menu") != null && request.getParameter("menu").equals("manage")){   
       %>
       상품 관리
       <% }else if(request.getParameter("menu") == null || request.getParameter("menu").equals("null") || request.getParameter("menu").equals("search")) {
       %>
       상품 목록조회
       <% } %> --%>
       
       <c:if test = "${not empty param.menu and param.menu eq 'manage' }" >
          상품 관리
       </c:if>
       <c:if test = "${empty param.menu or param.menu eq 'null' or param.menu eq 'search' }">
          상품 목록조회
       </c:if>
       
               </td>
            </tr>
         </table>
      </td>
      <td width="12" height="37">
         <img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
      </td>
   </tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
   <tr>
      
      <td align="right">
      
         <%-- <%
      if(search.getSearchCondition() != null) {
      %> --%>
         <select name="searchCondition" class="ct_input_g" style="width:80px">
            <option value ="0" ${ ! empty search.searchCondition && search.searchCondition == 0 ? "selected" : ""  }>상품번호</option>
            <option value ="1" ${ ! empty search.searchCondition && search.searchCondition == 1 ? "selected" : "" }>상품명</option>
            <option value ="2" ${ ! empty search.searchCondition && search.searchCondition == 2 ? "selected" : "" } >상품가격</option>    
         <%--    <%}else{%>
                  <select name="searchCondition" class="ct_input_g" style="width:80px">
                  <option value = "0" >상품번호</option>
                  <option value = "1" >상품명</option>
                  <option value = "2" >상품가격</option>
                  
                  <% } %> --%>
             
         </select>
         
         <input type="text" name="searchKeyword" 
         value="${! empty search.searchKeyword ? search.searchKeyword : "" }" 
         class="ct_input_g" style="width:200px; height:19px" />
      </td>
      
      <td align="right" width="70">
         <table border="0" cellspacing="0" cellpadding="0">
            <tr>
               <td width="17" height="23">
                  <img src="/images/ct_btnbg01.gif" width="17" height="23">
               </td>
               <td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
                  <a href="javascript:fncGetProductList(1);">검색</a>
               </td>
               <td width="14" height="23">
                  <img src="/images/ct_btnbg03.gif" width="14" height="23">
               </td>
            </tr>
         </table>
      </td>
   </tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
   <tr>
      <td colspan="11" >전체 ${resultPage.totalCount } 건수, 현재 ${ resultPage.currentPage } 페이지</td>
  
   </tr>
   <tr>
      <td class="ct_list_b" width="100">No</td>
      <td class="ct_line02"></td>
      <td class="ct_list_b" width="150">상품명</td>
      <td class="ct_line02"></td>
      <td class="ct_list_b" width="150">가격</td>
      <td class="ct_line02"></td>
      <td class="ct_list_b">등록일</td>   
      <td class="ct_line02"></td>
      <td class="ct_list_b">현재상태</td>   
   </tr>
   <tr>
      <td colspan="11" bgcolor="808285" height="1"></td>
   </tr>
      
  <%--     <%   
      for(int i=0; i<list.size(); i++) {
          Product vo = list.get(i);
   %> --%>
      
    <c:set var = "i" value = "0" />  
    <c:forEach var = "product" items = "${ list }" >
       <c:set var = "i" value = "${i +1 }" />
   <tr class="ct_list_pop">
      <td align="center">${ i }</td>
      <td></td>
           <td align="left">
            
            <c:if test = "${not empty param.menu and param.menu eq 'manage' }">
           		 <a href="/updateProductView.do?prodNo=${product.prodNo }&menu=${param.menu}">${product.prodName}</a>
            </c:if>
         
       <c:if test = "${not empty param.menu and param.menu eq 'search' }">
           		 <a href="/getProduct.do?prodNo=${product.prodNo }&menu=${param.menu}">${product.prodName}</a>
            </c:if>
   
            
            <%-- <c:if test = "${empty param.menu or param.menu eq 'null' or param.menu eq 'search' }" >
            	 <a href="/getProduct.do?prodNo=${product.prodNo }">${product.prodName}2</a>
            </c:if> --%>
            <%-- <c:if test = "${empty param.menu eq '' or param.menu eq 'search'}">
            	 <a href="/getProduct.do?prodNo=${product.prodNo }">${product.prodName}3</a>
            </c:if> --%>
            
         <%--  <%    
          if(request.getParameter("menu") != null && request.getParameter("menu").equals("manage")){   
       %>
       <a href="/updateProductView.do?prodNo=<%=vo.getProdNo()%>"><%= vo.getProdName() %></a>
           <% System.out.println("name : " + vo.getProdName()); %>
       
       <% }else if(request.getParameter("menu") == null ||request.getParameter("menu").equals("null") ||  request.getParameter("menu").equals("search")) {
       %>
       
       <a href="/getProduct.do?prodNo=<%=vo.getProdNo()%>"><%= vo.getProdName() %></a>
       
       <% System.out.println(request.getParameter("menu") + "skdkld" + vo.getProdName());  %>
       
       <% } else if(request.getParameter("menu").equals("") || request.getParameter("menu").equals("search")) {%>
       <a href="/getProduct.do?prodNo=<%=vo.getProdNo()%>"><%= vo.getProdName() %></a>
        --%>
       
       
<%-- <% }else{%>
      <% System.out.println(request.getParameter("menu") + "skdkld" + vo.getProdName());  %>
      <% } %> --%>
</td>
      <td></td>
       <%-- <td align="left">${product.prodName}</td> --%>
      <%-- <td align="left"><%=vo.getPrice() %></td> --%>
      
      
      <td align="left">${product.price}</td>
      <td></td>
    <%--   <td align="left"><%=vo.getRegDate() %></td> --%>
      <td align="left">${product.regDate} </td> 
      <td></td>
      <td align="left">
         배송중

      </td>   
   </tr>
   <tr>
      <td colspan="11" bgcolor="D6D7D6" height="1"></td>
   </tr>   
   <%--  <%} %> --%>
   </c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
   <tr>
      <td align="center">
     
         
         <!--  <div style="text-align: center; margin-top: 10px;"> -->
                
<%--                 이전 페이지로 이동하는 링크 버튼  &lt 이거는 < 이 부등호 / &gt는 > 이 부등호
                
                맨 앞 페이지로 이동
                <% if(resultPage.getCurrentPage() > 1) { %>
                   <a href="javascript:fncGetProductList(1)"> &lt;&lt; </a>
            
             맨 앞 페이지 없을 때 (고정)
            <%}else{ %>
                  <span></span>
            <% } %>
            
             앞 페이지로 이동
                <% if(resultPage.getCurrentPage() > 1) { %>
                   <a href="javascript:fncGetProductList(<%= resultPage.getCurrentPage() -1 %>)"> 이전 </a>
            <% } %>
            
                페이지 번호 표시 부분
                <% for (int i = resultPage.getBeginUnitPage(); i <= resultPage.getEndUnitPage(); i++) { %>
                    <a href="javascript:fncGetProductList(<%= i %>)"><%= i %></a>
                <% } %>

             뒤 페이지로 이동
                <% if(resultPage.getCurrentPage() < resultPage.getMaxPage()) { %>
                   <a href="javascript:fncGetProductList(<%= resultPage.getCurrentPage() +1 %>)"> 이후 </a>
            <% } %>

                다음 페이지로 이동하는 링크 버튼
                 <% if(resultPage.getCurrentPage() < resultPage.getEndUnitPage()) { %>
                <a href="javascript:fncGetProductList(<%= resultPage.getMaxPage() %>)"> &gt;&gt; </a>
                
                <%}else{ %>
                  <span></span>
            <% } %>--%>
            </div> 
        </td>
   </tr>
         
         <jsp:include page="../common/pageNavigator.jsp"></jsp:include>
         
       </td>
   </tr>
</table>

<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>