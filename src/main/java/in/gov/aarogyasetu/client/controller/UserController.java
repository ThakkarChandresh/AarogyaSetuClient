package in.gov.aarogyasetu.client.controller;

import in.gov.aarogyasetu.client.util.UtilityMethods;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;


public class UserController
{

    public static void register(BufferedReader userInput) throws IOException
    {

        JSONObject request = new JSONObject();

        request.put("RequestCode", "5");

        JSONObject userDetails = new JSONObject();

        System.out.print("\nEnter Your Full Name: ");

        userDetails.put("fullName", userInput.readLine());

        System.out.print("\nEnter Your Contact Number: ");

        userDetails.put("contactNumber", userInput.readLine());

        System.out.print("\nIn Which City You Live?: ");

        userDetails.put("city", userInput.readLine());

        System.out.print("\nWhich Symptoms You Are Having?: ");

        userDetails.put("symptoms", userInput.readLine());

        System.out.print("\nWhich Medicines You Take?: ");

        userDetails.put("medicines", userInput.readLine());

        userDetails.put("status", "positive");

        System.out.print("\nPlease Choose A Password For Your Profile: ");

        userDetails.put("password", userInput.readLine());

        request.put("RequestBody", new JSONObject().put("userDetails", userDetails));

        JSONObject response = UtilityMethods.makeRequest(request);

        if (response.getInt("StatusCode") == 200)
        {
            System.out.println(response.getJSONObject("ResponseBody").get("message"));
        }
        else if (response.getInt("StatusCode") == 400)
        {
            System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));
        }
        else
        {
            System.out.println("\nSome internal error occurred!");
        }
    }

    public static void login(BufferedReader userInput) throws IOException
    {

        JSONObject request = new JSONObject();

        request.put("RequestCode", "3");

        JSONObject credentials = new JSONObject();

        System.out.print("\nEnter Your UserID: ");

        credentials.put("userID", userInput.readLine());

        System.out.print("\nEnter Your Password: ");

        credentials.put("password", userInput.readLine());

        request.put("RequestBody", new JSONObject().put("userCredentials", credentials));

        JSONObject response = UtilityMethods.makeRequest(request);

        if (response.getInt("StatusCode") == 200)
        {
            System.out.println("\nLogged in successfully!");

            FacilityController.setToken(response.getJSONObject("ResponseBody").getString("access-token"));

            FacilityController.showMenu(userInput);

        }
        else if (response.getInt("StatusCode") == 400)
        {
            System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));
        }
        else if (response.getInt("StatusCode") == 401)
        {
            System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));
        }
        else
        {
            System.out.println("\nSome internal error occurred!");
        }
    }

}
