<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="flights">
	<h2>My Flights</h2>

	<table id="flightsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Reference</th>
				<th>Seats</th>
				<th>Price</th>
				<th>Flight Status</th>
				<th>Published</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${flights}" var="flight">
				<tr>
					<td><spring:url value="/flights/{flightId}" var="flightUrl">
							<spring:param name="flightId" value="${flight.id}" />
						</spring:url> <a href="${fn:escapeXml(flightUrl)}"><c:out value="${flight.reference}" /></a></td>
					<td><c:out value="${flight.seats}" /></td>
					<td><c:out value="${flight.price}" /></td>
					<td><c:out value="${flight.flightStatus}" /></td>
					<td><c:out value="${flight.published}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<sec:authorize access="hasAuthority('airline')">
		<button class="btn btn-default" onclick="window.location.href='/flights/new'">New</button>
	</sec:authorize>

</petclinic:layout>
