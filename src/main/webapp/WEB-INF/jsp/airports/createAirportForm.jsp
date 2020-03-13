<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="airports">
	<jsp:body>
    <h2>
        Airport
    </h2>
    <form:form modelAttribute="airport" class="form-horizontal" id="add-airport-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Name" name="name" />
            <petclinic:inputField label="Max Number Of Planes" name="maxNumberOfPlanes" />
            <petclinic:inputField label="Max Number Of Clients" name="maxNumberOfClients" />
            <petclinic:inputField label="Latitude" name="latitude" />
            <petclinic:inputField label="Longitude" name="longitude" />
            <petclinic:inputField label="Code" name="code" />
            <petclinic:inputField label="City" name="city" />
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                        <!--<button class="btn btn-default" type="submit">Persist airport</button>-->
             <c:choose>
                    <c:when test="${airport['new']}">
                        <button class="btn btn-default" type="submit">Register airport</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update airport</button>
                    </c:otherwise>
            </c:choose>
            </div>

        </div>
    </form:form>
   	</jsp:body>
</petclinic:layout>