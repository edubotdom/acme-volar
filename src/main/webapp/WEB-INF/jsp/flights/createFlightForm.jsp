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
										$("#landDate").datepicker({
											dateFormat : 'yy-mm-dd'
										});
									});
								</script>
        
        <script>
									$(function() {
										$("#departDate").datepicker({
											dateFormat : 'yy-mm-dd'
										});
									});
								</script>
    </jsp:attribute>

	<jsp:body>
    <h2>
        Register a flight!
    </h2>
    <form:form modelAttribute="flight" class="form-horizontal"
			id="add-flight-form">
        <div class="form-group has-feedback">

        
        
            <petclinic:inputField label="Reference" name="reference" />
            <petclinic:inputField label="Seats" name="seats" />
            <petclinic:inputField label="Price" name="price" />
            <!--<petclinic:inputField label="Published" name="published" />-->
            <div class="control-group">
            	<petclinic:selectField label="Status" name="flightStatus"
						names="${estados}" size="3" />
            </div>
                    
        	<div class="control-group">
            	<petclinic:selectField label="Visibility" name="published" 
						names="${opciones_publicao}" size="3" />
       		 </div>
       		 
			<petclinic:selectField label="Plane" name="plane" size="5"
					names="${planes}" />
			<petclinic:selectField label="Lands" name="lands" size="5"
					names="${landsList}" />

			<petclinic:inputField label="Land Date" name="landDate" />
			<petclinic:selectField label="Departes" name="departes" size="5"
					names="${departuresList}" />
			<petclinic:inputField label="Depart Date" name="departDate" />
			<!--2020-06-06 14:05 airline1-->

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
