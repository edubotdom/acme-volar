<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="runways">

	<h2>Runway Information</h2>


	<table class="table table-striped">
	
		<tr>
			<th>Name</th>
			<td><b><c:out value="${runway.name}" /></b></td>
		</tr>
		<tr>
			<th>Type</th>
			<td><c:out value="${runway.type}" /></td>
		</tr>
		
	</table>

	<sec:authorize access="hasAuthority('airline')">
		<spring:url value="/airports/{airportId}/runways/{runwayId}/edit" var="runwayEditUrl">
			<spring:param name="airportId" value="${runway.airport.id}" />
			<spring:param name="runwayId" value="${runway.id}" />
		</spring:url>

		<button class="btn btn-default" onclick="window.location.href='${fn:escapeXml(runwayEditUrl)}'">Edit</button>
	</sec:authorize>

</petclinic:layout>