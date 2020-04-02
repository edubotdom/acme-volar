<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<petclinic:layout pageName="book">
	<h2>Books</h2>

	<table id="booksTable" class="table table-striped">
		<thead>
			<tr>
				<th>Quantity</th>
				<th>Price</th>
				<th>Moment</th>
				<th>BookStatusType</th>
				<th>Flight</th>
	
				<sec:authorize access="hasAuthority('airline')">
					<th>Client</th>
					
					<th>Edit Book</th>
				</sec:authorize>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${books}" var="book">
				<tr>
				
					<td><c:out value="${book.quantity}" /></td>
							
					<td><c:out value="${book.price}" /></td>
					
					<td><c:out value="${book.moment}" /></td>
					
					<td><c:out value="${book.bookStatusType}" /></td>
					
					<td><spring:url value="/flights/{flightId}" var="flightUrl">
							<spring:param name="flightId" value="${book.flight.id}" />
						</spring:url> <a href="${fn:escapeXml(flightUrl)}"><c:out value="${book.flight.reference}" /></a></td>
					
					<sec:authorize access="hasAuthority('airline')">	
						<td><c:out value="${book.client}" /></td>
						
						<td><spring:url value="/books/{bookId}/edit" var="bookUrl">
							<spring:param name="bookId" value="${book.id}" />
						</spring:url> <a href="${fn:escapeXml(bookUrl)}"><c:out value="Edit Book" /></a></td>
					</sec:authorize>

				</tr>
			</c:forEach>
		</tbody>
	</table>

	

</petclinic:layout>
