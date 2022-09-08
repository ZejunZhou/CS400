// --== CS400 File Header Information ==--
// Name: Zejun Zhou
// Email: zzhou443@wisc.edu
// Team: <your team name: two letters>
// TA: <name of your team's ta>
// Lecturer: <name of your lecturer>
// Notes to Grader: <optional extra notes>

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;

interface LocationDataLoaderInterface {

  public List <LocationDataInterface> loadFile(String csvFilePath);

}

public class LocationLoader {

  List<LocationDataInterface> listLocation = new ArrayList<>();

  public List<LocationDataInterface> loadFile(String csvFilePath) throws IOException {
      File file = new File(csvFilePath);
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = "";
      // start to add contents
      while((line = reader.readLine()) != null){
        String[] temp = line.split(","); // split the csv file with "," as delimitor
        listLocation.add(new LocationData(temp[0],temp[1],temp[2])); // add location, destination,edge
      }
      reader.close();
    return listLocation;
  }
  public void writeFile(String csvFilePath, LocationData newData){
    try {
      FileWriter writer = new FileWriter(csvFilePath, true);
      //handle special case
      handleSpecialInnerQuote(newData);
      writer.append(newData.getLocation());
      writer.append(",");
      writer.append(newData.getDestination());
      writer.append(",");
      writer.append(newData.getWeight());
      writer.append("\n");
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    listLocation.add(newData);
  }

  private void handleSpecialInnerQuote (LocationData newData){
    String location = newData.getLocation();
    //update the location
    if(newData.getLocation().contains(",") || newData.getLocation().contains("\"")
            ||newData.getLocation().contains("'")){
      location = location.replace(",",""); //replace the inner quotes with empty string
      newData.setLocation(location); // update the location 
    }

    String destination = newData.getDestination();
    //update the destination
    if(newData.getDestination().contains(",") || newData.getDestination().contains("\"")
            ||newData.getDestination().contains("'")){
      destination = destination.replace(",",""); //replace the inner quotes with empty string
      newData.setDestination(destination); // update the destination
    }
  }




}

