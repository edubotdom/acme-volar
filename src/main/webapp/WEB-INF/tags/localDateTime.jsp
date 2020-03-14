<%@ tag body-content="empty" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="date_time" required="true" type="java.time.LocalDateTime" %>
<%@ attribute name="pattern" required="true" type="java.lang.String" %>


<fmt:parseDate value="${date_time}" pattern="yyyy-MM-dd HH:mm" var="parsedDate" type="date_time"/>
<fmt:formatDate value="${parsedDate}" type="date_time" pattern="${pattern}"/>
