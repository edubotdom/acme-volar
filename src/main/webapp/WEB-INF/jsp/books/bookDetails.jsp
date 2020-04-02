<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="books">

	<h2>Book Information</h2>


	<table class="table table-striped">

		<tr>
			<th>Quantity</th>
			<td><c:out value="${book.quantity}" /></td>
		</tr>
		<tr>
			<th>Price</th>
			<td><c:out value="${book.price}" /></td>
		</tr>
		<tr>
			<th>Moment</th>
			<td><c:out value="${book.moment}" /></td>
		</tr>
		<tr>
			<th>Status Type</th>
			<td><c:out value="${book.bookStatusType}" /></td>
		</tr>
		<tr>
			<th>Flight</th>
			<td><spring:url value="/flights/{flightId}" var="flightUrl">
					<spring:param name="flightId" value="${book.flight.id}" />
				</spring:url> <a href="${fn:escapeXml(flightUrl)}"><c:out value="${book.flight.reference}" /></a></td>
		</tr>
		<tr>
			<th>Client</th>
			<td><c:out value="${book.client}" /></td>
		</tr>

	</table>
	
	<sec:authorize access="hasAuthority('airline')">
		<spring:url value="/book/{bookId}/edit" var="editUrl">
			<spring:param name="bookId" value="${book.id}" />
		</spring:url>
		<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Book</a>
	</sec:authorize>
	
	
</petclinic:layout>

