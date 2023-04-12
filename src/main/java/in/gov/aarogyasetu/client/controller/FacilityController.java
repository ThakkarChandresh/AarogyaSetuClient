package in.gov.aarogyasetu.client.controller;

import in.gov.aarogyasetu.client.util.BaseMethods;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;


public class FacilityController
{

    private String token;

    FacilityController(String token)
    {

        this.token = token;

    }

    public void showMenu(BufferedReader userInput) throws IOException
    {

        JSONObject request;

        boolean isLoggedIn = true;

        while (isLoggedIn)
        {
            System.out.print("\nSelect Appropriate Choice\n\n1.View all available COVID hospitals\n2.Book bed\n3.Cured From COVID? Check Out Bed\n4.Logout\n\nEnter Your Choice: ");

            String choice = userInput.readLine();

            switch (choice)
            {
                case "1" ->
                {

                    request = new JSONObject();

                    request.put("RequestCode", "6");

                    System.out.print("\nDo want to filter it by your area? (Yes/No): ");

                    boolean filterByArea = userInput.readLine().equalsIgnoreCase("yes");

                    request.put("RequestBody", new JSONObject().put("filterByArea", filterByArea).put("token", this.token));

                    JSONObject response = BaseMethods.makeRequest(request);

                    if (response.getInt("StatusCode") == 200)
                    {

                        this.token = response.getJSONObject("ResponseBody").getString("access-token");

                        JSONArray hospitals = response.getJSONObject("ResponseBody").getJSONArray("hospitals");

                        if (hospitals.length() > 0)
                        {
                            for (Object hospital : hospitals)
                            {
                                JSONObject jsonObject = new JSONObject(hospital.toString());

                                System.out.println("\n\n* * * * * " + jsonObject.get("hospitalName") + " * * * * *");

                                System.out.println("Hospital Id Is: " + jsonObject.get("hospitalID"));

                                System.out.println("Doctor Name Is: " + jsonObject.get("doctorName"));

                                System.out.println("Hospital Is In Area: " + jsonObject.get("area"));

                                System.out.println("Contact Number Is: " + jsonObject.get("contactNumber"));

                                System.out.println("Available COVID Beds Are: " + jsonObject.get("availableBeds"));
                            }
                        }
                        else
                        {
                            System.out.println("\nSorry! No Hospitals Found!");
                        }
                    }
                    else if (response.getInt("StatusCode") == 400)
                    {
                        System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));
                    }
                    else if (response.getInt("StatusCode") == 401)
                    {
                        System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));

                        isLoggedIn = false;
                    }
                    else
                    {
                        System.out.println("Some internal error occurred!");
                    }
                }
                case "2" ->
                {
                    request = new JSONObject();

                    request.put("RequestCode", "7");

                    System.out.print("\nEnter the Hospital Id Where You Want To Book The Bed: ");

                    String hospitalID = userInput.readLine();

                    request.put("RequestBody", new JSONObject().put("hospitalID", hospitalID).put("token", this.token));

                    JSONObject response = BaseMethods.makeRequest(request);

                    if (response.getInt("StatusCode") == 200)
                    {
                        this.token = response.getJSONObject("ResponseBody").getString("access-token");

                        System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));
                    }
                    else if (response.getInt("StatusCode") == 400)
                    {
                        System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));
                    }
                    else if (response.getInt("StatusCode") == 401)
                    {
                        System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));

                        isLoggedIn = false;
                    }
                    else
                    {
                        System.out.println("Some internal error occurred!");
                    }
                }
                case "3" ->
                {
                    request = new JSONObject();

                    request.put("RequestCode", "8");

                    System.out.print("\nDo you fell better right now? And you are sure to checkout? (Yes/No): ");

                    boolean better = userInput.readLine().equalsIgnoreCase("yes");

                    if (better)
                    {
                        request.put("RequestBody", new JSONObject().put("token", this.token));

                        JSONObject response = BaseMethods.makeRequest(request);

                        if (response.getInt("StatusCode") == 200)
                        {

                            this.token = response.getJSONObject("ResponseBody").getString("access-token");

                            System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));
                        }
                        else if (response.getInt("StatusCode") == 400)
                        {
                            System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));
                        }
                        else if (response.getInt("StatusCode") == 401)
                        {
                            System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));

                            isLoggedIn = false;
                        }
                        else
                        {
                            System.out.println("Some internal error occurred!");
                        }

                    }
                    else
                    {
                        System.out.println("\nPlease Take Care of your self!");
                    }
                }
                case "4" ->
                {
                    request = new JSONObject();

                    request.put("RequestCode", "4");

                    request.put("RequestBody", new JSONObject().put("token", this.token));

                    JSONObject response = BaseMethods.makeRequest(request);

                    System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));

                    isLoggedIn = false;
                }
                default -> System.out.println("Invalid Choice!");
            }
        }
    }


}
