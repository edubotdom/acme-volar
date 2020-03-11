<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="airlines">

	<h2>Airline Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<td><b><c:out value="${airline.name}" /></b></td>
		</tr>
		<tr>
			<th>Identification</th>
			<td><c:out value="${airline.identification}" /></td>
		</tr>
		<tr>
			<th>Reference</th>
			<td><c:out value="${airline.reference}" /></td>
		</tr>
		<tr>
			<th>Email</th>
			<td><c:out value="${airline.email}" /></td>
		</tr>
		<tr>
			<th>Country</th>
			<td><c:out value="${airline.country}" /></td>
		</tr>
		<tr>
			<th>Phone</th>
			<td><c:out value="${airline.phone}" /></td>
		</tr>
		<tr>
			<th>Creation Date</th>
			<td><c:out value="${airline.creationDate}" /></td>
		</tr>
	</table>

</petclinic:layout>
