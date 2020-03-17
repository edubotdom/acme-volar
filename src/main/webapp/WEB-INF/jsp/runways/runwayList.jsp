<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="runways">
	<h2>Runways</h2>

	<table id="runwayTable" class="table table-striped">
		<thead>
			<tr>
				<th>Name</th>
				<th>Type</th>
				<th>Edit</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${runways}" var="runway">
				<tr>
					<td><c:out value="${runway.name}" /></td>
					<td><c:out value="${runway.runwayType.name}" /></td>
					<td><spring:url value="/airports/{airportId}/runways/{runwayId}/edit" var="runwayEditUrl">
								<spring:param name="runwayId" value="${runway.id}" />
								<spring:param name="airportId" value="${runway.airport.id}" />
						</spring:url> <a href="${fn:escapeXml(runwayEditUrl)}"><c:out value="Edit" /></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
		<spring:url value="/airports/{airportId}/runways/new" var="runwayNewUrl">
			<spring:param name="airportId" value="${airport}" />
		</spring:url>
		<button class="btn btn-default" onclick="window.location.href='${fn:escapeXml(runwayNewUrl)}'">New Runway</button>
	
	
</petclinic:layout>
