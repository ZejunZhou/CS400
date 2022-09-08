// --== CS400 File Header Information ==--
// Name: Tony Chen, Tong Xia, Zejun Zhou
// Email: zchen873@wisc.edu
// Team: DG
// TA: Brianna
// Lecturer: Florian
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.platform.console.ConsoleLauncher;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class MapTests {


  public static void main(String[] args) {
    String className = MethodHandles.lookup().lookupClass().getName();
    String classPath = System.getProperty("java.class.path").replace(" ", "\\ ");
    String[] arguments = new String[] {
      "-cp",
      classPath,
      "--include-classname=.*",
      "--select-class=" + className };
    ConsoleLauncher.main(arguments);
  }


    // Data Wrangler Code Tests
    @Test
    /**
     * author : Zejun Zhou
     * role : data wrangler
     * This tests the functionality of loader in header
     */
    public void DataWrangler_Test1() throws IOException {
        LocationLoader test = new LocationLoader();
        List<LocationDataInterface> list = test.loadFile("./location.csv");

        //First word check
        assertEquals("Location", list.get(0).getLocation());
    }

    @Test
    /**
     * author : Zejun Zhou
     * role : data wrangler
     * This tests the functionality of loader in first rows
     */
    public void DataWrangler_Test2() throws IOException {
        LocationLoader test = new LocationLoader();
        List<LocationDataInterface> list = test.loadFile("./location.csv");

        //First word check
        assertEquals("Memorial Library", list.get(1).getLocation());
    }

    @Test
    /**
     * author : Zejun Zhou
     * role : data wrangler
     * This tests the functionality of loader in first rows
     */
    public void DataWrangler_Test3() throws IOException {
        LocationLoader test = new LocationLoader();
        List<LocationDataInterface> list = test.loadFile("./location.csv");

        //First word check
        assertEquals("1", list.get(1).getWeight());
    }


    // Back End Developer Tests
	
    /**
   * Author : Adwait Vaidy
   * Role: BackEnd Developer
   */
  @Test
  public void BackEnd_Test1(){
        BackEnd back = new BackEnd();
        try{
                back.insertNode(null);
        }
        catch(NullPointerException e){
                System.out.println(e.getMessage());
        };
        assertEquals(0,back.isEmpty());
  }

    /**
   * Author : Adwait Vaidya
   * Role : BackEnd Developer
   */
  @Test
  public void BackEnd_Test2(){
          BackEnd back = new BackEnd();
        back.insertNode("Madison");
        back.insertNode("Pitt");
        back.insertNode("Toronto");

        assertTrue(back.contains("Pitt"));

  }

    /**
   * Author : Adwait Vaidya
   * Role : BackEnd Developer
   */
  @Test
  public void BackEnd_Test3(){
          BackEnd back = new BackEnd();

        back.insertNode("Madison");
        back.insertNode("Pitt");
        back.insertNode("Toronto");
        assertTrue(back.removeVertex("Madison"));
  }

    // Front End Developer Tests
    /**
     *Author: Tong Xia
     *Role: FrontEnd Developer
     */

    @Test
    public void FrontEnd_TestAddLocation()
    {
        MapFrontEnd test =  new MapFrontEnd();
        test.back = new BackEnd();
        test.addLocation("Apartment");
        assertTrue(test.locationList.contains("Apartment"));
        test.removeLocation("Apartment");

    }

    /**
     *Author: Tong Xia
     *Role: FrontEnd Developer
     */
    @Test

    public void FrontEnd_TestRemoveLocation()
    {
        MapFrontEnd test =  new MapFrontEnd();
        test.back = new BackEnd();
        test.addLocation("Apartment");
        assertTrue(test.locationList.contains("Apartment"));
        test.removeLocation("Apartment");
        assertTrue(!test.locationList.contains("Apartment"));

    }

    /**
     *Author: Tong Xia
     *Role: FrontEnd Developer
     */
    @Test

    public void FrontEnd_TestSearchSamePlaceOrEmpty()
    {
        MapFrontEnd test =  new MapFrontEnd();
        test.back = new BackEnd();
        assertEquals(test.searchDirections(null,null),"lackInfo");
        test.addLocation("Apartment");
        assertEquals(test.searchDirections("Apartment","Apartment"),"samePlace");
        test.removeLocation("Apartment");

    }  
    // Integration Manager Tests

    /**
     * Author: Tony Chen
     * Role: Integrated Manager
     */
    @Test
    public void IntegratingManager_TestBackEnd()
    {
        BackEnd test = new BackEnd();
        //Insert locations and distances
        test.insertNode("CL");
        test.insertNode("NC");
        test.insertNode("EH");
        test.insertNode("MH");

        test.insertEdge("CL","NC",1);
        test.insertEdge("CL","EH",2);
        test.insertEdge("EH","MH",1);
        test.insertEdge("CL","MH",4);
        test.insertEdge("NC","EH",1);
        test.insertEdge("MH","EH",5);

        assertEquals(3, test.getPathCost("CL", "MH"));
        assertEquals(2,test.getPathCost("CL", "EH"));

    }
    /**
     * Author: Tony Chen
     * Role: Integrated Manager
     */
    @Test
    public void IntegratingManager_TestDataWrangler() throws IOException
    {
        LocationLoader test = new LocationLoader();
        List<LocationDataInterface> list = test.loadFile("./location.csv/");

        //Words check
        assertEquals("Memorial Union", list.get(3).getLocation());
        assertEquals("College Library", list.get(3).getDestination());
        assertEquals("College Library", list.get(5).getLocation());
        assertEquals("Helen C. White Hall", list.get(5).getDestination());

        //Weight Check
        assertEquals("3", list.get(3).getWeight());
        assertEquals("1", list.get(2).getWeight());
        assertEquals("1", list.get(5).getWeight());
        assertEquals("2", list.get(6).getWeight());

    }
    /**
     * Author: Tony Chen
     * Role: Integrated Manager
     */
    @Test

    public void IntegratingManager_TestFrontEnd()
    {
        MapFrontEnd test =  new MapFrontEnd();
        test.back = new BackEnd();
        //null check
        assertEquals(test.searchDirections(null,null),"lackInfo");

        //create a graph for backend
        test.addLocation("CL");
        test.addLocation("NC");
        test.addLocation("EH");
        test.addLocation("MH");

        test.back.insertEdge("CL","NC",1);
        test.back.insertEdge("CL","EH",2);
        test.back.insertEdge("EH","MH",1);
        test.back.insertEdge("CL","MH",4);
        test.back.insertEdge("NC","EH",1);
        test.back.insertEdge("MH","EH",5);

        //check the functionality of frontend methods
        assertTrue(test.locationList.contains("CL"));
        assertEquals(test.searchDirections("CL","MH"),"[CL, NC, EH, MH]");

    }

}
