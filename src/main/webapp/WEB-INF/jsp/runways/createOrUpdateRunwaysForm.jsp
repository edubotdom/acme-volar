<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="runways">

	<jsp:body>
    <h2>
        Register a Runway!
    </h2>
    <form:form modelAttribute="runway" class="form-horizontal" id="add-runway-form">
    	<input type="hidden" name="id" value="${runway.id}"/>
        <div class="form-group has-feedback">
            <petclinic:inputField label="Name" name="name" />
            <petclinic:selectField label="Type" name="type" size="2" names="${runwayTypes}" />
            
            
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
             <c:choose>
                    <c:when test="${runway['new']}">
                        <button class="btn btn-default" type="submit">Register Runway</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update runway</button>
                    </c:otherwise>
            </c:choose>
            </div>

        </div>
    </form:form>
   	</jsp:body>
</petclinic:layout>
