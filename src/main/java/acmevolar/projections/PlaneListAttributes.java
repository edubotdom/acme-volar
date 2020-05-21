
package acmevolar.projections;

import java.util.Date;

public interface PlaneListAttributes {

	String getId();
	String getReference();
	String getMaxSeats();
	String getDescription();
	String getManufacter();
	String getModel();
	String getNumberOfKm();
	String getMaxDistance();
	Date getLastMaintenance();
}
