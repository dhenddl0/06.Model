<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
   <tr>
      <td align="center">
      <input type = "hidden" id="currentPage" name="currentPage" value =""/>
	
<%-- 	<c:if test="${ resultPage.currentPage <= resultPage.pageUnit }">
			◀ 이전
	</c:if>
	<c:if test="${ resultPage.currentPage > resultPage.pageUnit }">
			<a href="javascript:fncGetUserList('${ resultPage.currentPage-1}')">◀ 이전</a>
	</c:if>
	
	<c:forEach var="i"  begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}" step="1">
		<a href="javascript:fncGetUserList('${ i }');">${ i }</a>
	</c:forEach>
	
	<c:if test="${ resultPage.endUnitPage >= resultPage.maxPage }">
			이후 ▶
	</c:if>
	<c:if test="${ resultPage.endUnitPage < resultPage.maxPage }">
			<a href="javascript:fncGetUserList('${resultPage.endUnitPage+1}')">이후 ▶</a>
	</c:if> --%>
	
	
	<c:if test="${resultPage.currentPage > 1 }">
		<a href="javascript:fncGetUserList(1)"> &lt;&lt; </a>
		<a href="javascript:fncGetUserList(${resultPage.currentPage-1})"> 이전 </a>
	</c:if>
	
	<c:forEach var = "i" begin="${resultPage.beginUnitPage}" end="${resultPage.endUnitPage}">
		<a href="javascript:fncGetUserList(${i })">${i }</a>
	</c:forEach>
	
	<c:if test = "${resultPage.currentPage < resultPage.maxPage}">
		<a href="javascript:fncGetUserList(${resultPage.currentPage +1 })"> 이후 </a>
	</c:if>
	
	<c:if test = "${resultPage.currentPage < resultPage.endUnitPage}">
		 <a href="javascript:fncGetUserList(${resultPage.maxPage })"> &gt;&gt; </a>
	</c:if>
