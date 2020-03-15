<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="flights">

	<jsp:attribute name="customScript">
        <script>
									$(function() {
										$("#landDate").datetimepicker({
											format : 'yyyy-mm-dd hh:ii'
										});
									});
								</script>
        
        <script>
									$(function() {
										$("#departDate").datetimepicker({
											format : 'yyyy-mm-dd hh:ii'
										});
									});
								</script>
    </jsp:attribute>

	<jsp:body>
    <h2>
        Register a flight!
    </h2>
    <form:form modelAttribute="flight" class="form-horizontal" id="add-flight-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Reference" name="reference" />
            <petclinic:inputField label="Seats" name="seats" />
            <petclinic:inputField label="Price" name="price" />
            <petclinic:inputField label="Published" name="published" />
            <petclinic:selectField label="Status" name="flightStatus" size="3" names="${estados}" />
			<petclinic:selectField label="Plane" name="plane" size="5" names="${planes}" />
			<!--<form:select path="plane" items="${planes}" size="5" itemLabel="reference" itemValue="id" />-->
			<petclinic:selectField label="Lands" name="lands" size="5" names="${landsList}" />
			<petclinic:inputField label="Land Date" name="landDate" />
			<petclinic:selectField label="Departes" name="departes" size="5" names="${departuresList}" />
			<petclinic:inputField label="Depart Date" name="departDate" />
			<!--2020-06-06 14:05-->
            
            <input type="hidden" name="id" value="${estados}" />
            <input type="hidden" name="id" value="${planes}" />
            <input type="hidden" name="name" value="${departuresList}" />
            <input type="hidden" name="name" value="${landsList}" />
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
             <c:choose>
                    <c:when test="${flight['new']}">
                        <button class="btn btn-default" type="submit">Register flight</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update flight</button>
                    </c:otherwise>
            </c:choose>
            </div>

        </div>
    </form:form>
   	</jsp:body>
</petclinic:layout>
