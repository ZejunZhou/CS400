// --== CS400 File Header Information ==--
// Name: Zejun Zhou
// Email: zzhou443@wisc.edu
// Team: <your team name: two letters>
// TA: <name of your team's ta>
// Lecturer: <name of your lecturer>
// Notes to Grader: <optional extra notes>

interface LocationDataInterface {

  public String getLocation();

  public String getDestination();

  public String getWeight();

}


public class LocationData implements LocationDataInterface {
  private String location;
  private String destination;
  private String weight;

  public LocationData(String location, String destination, String weight) {
    this.location = location;
    this.destination = destination;
    this.weight = weight;
  }

  public String getLocation() {
    return this.location;
  }

  public String getDestination() {
    return this.destination;
  }

  public String getWeight() {
    return this.weight;
  }

  public void setLocation (String newLocation) {
    this.location = newLocation;
  }

  public void setDestination (String newDestination) {
    this.destination = newDestination;
  }



}
