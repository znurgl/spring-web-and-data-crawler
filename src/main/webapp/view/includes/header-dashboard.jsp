
<%--@elvariable id="user" type="net.brandbrother.domain.User"--%>
<%--@elvariable id="user" type="net.brandbrother.domain.Campaign"--%>
<%--@elvariable id="user" type="net.brandbrother.domain.Keyword"--%>
<%--@elvariable id="user" type="net.brandbrother.domain.Data"--%>
<a href="/dashboard">Dashboard</a> 
BrandBrother Kampányok: 
<select>
	<c:forEach var="campaign" items="${campaigns}">
          <option>${campaign.name}</option>
      </c:forEach>  
</select> 
<a href="/campaign/new">Kampány létrehozása</a>
 - ${user.login} - 
<a href="/auth/logout">Kijelentkezés</a>

