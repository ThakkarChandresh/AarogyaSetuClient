package in.gov.aarogyasetu.client.controller;

import in.gov.aarogyasetu.client.util.BaseMethods;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;


public class MainMenuController
{

    public void start()
    {

        JSONObject request;

        try (BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in)))
        {
            while (!Thread.currentThread().isInterrupted())
            {
                System.out.print("\nSelect Appropriate Choice\n\n1.Check COVID Status\n2.Get Active Cases In My Area\n3.Already Registered? Log In\n4.Exit\n\nEnter Your Choice: ");

                String choice = userInput.readLine();

                switch (choice)
                {
                    case "1" ->
                    {
                        request = new JSONObject();

                        request.put("RequestCode", "0");

                        JSONObject response = BaseMethods.makeRequest(request);

                        if (response.getInt("StatusCode") == 200)
                        {
                            List<String> answers = new ArrayList<>();

                            JSONArray questions = response.getJSONObject("ResponseBody").getJSONArray("questions");

                            for (Object o : questions)
                            {
                                System.out.print("\n" + o + ": ");

                                answers.add(userInput.readLine());
                            }

                            request = new JSONObject();

                            request.put("RequestCode", "1");

                            request.put("RequestBody", new JSONObject().put("answers", answers));

                            response = BaseMethods.makeRequest(request);

                            if (response.getInt("StatusCode") == 200)
                            {
                                boolean result = response.getJSONObject("ResponseBody").getBoolean("result");

                                if (result)
                                {
                                    System.out.println("\nCaution! You're COVID Positive!\nPlease Enter Create A Profile: ");

                                    new UserController().register(userInput);
                                }
                                else
                                {
                                    System.out.println("\nDon't Worry! You're COVID Negative!");
                                }
                            }
                            else if (response.getInt("StatusCode") == 400)
                            {
                                System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));
                            }
                            else
                            {
                                System.out.println("Some internal error occurred!");
                            }
                        }
                        else if (response.getInt("StatusCode") == 400)
                        {
                            System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));
                        }
                        else
                        {
                            System.out.println("Some internal error occurred!");
                        }
                    }

                    case "2" ->
                    {
                        request = new JSONObject();

                        request.put("RequestCode", "2");

                        System.out.print("\nEnter your are name: ");

                        String area = userInput.readLine();

                        request.put("RequestBody", new JSONObject().put("area", area));

                        JSONObject response = BaseMethods.makeRequest(request);

                        if (response.getInt("StatusCode") == 200)
                        {
                            System.out.println("\nActive cases in your area are: " + response.getJSONObject("ResponseBody").getInt("activeCases"));
                        }
                        else if (response.getInt("StatusCode") == 400)
                        {
                            System.out.println("\n" + response.getJSONObject("ResponseBody").getString("message"));
                        }
                        else
                        {
                            System.out.println("Some internal error occurred!");
                        }

                    }

                    case "3" -> new UserController().login(userInput);

                    case "4" ->
                    {
                        System.out.println("\n* * * * * * * * * * Thank You * * * * * * * * * *");

                        Thread.currentThread().interrupt();
                    }

                    default -> System.out.println("Invalid Choice!");
                }
            }
        }
        catch (ConnectException exception)
        {
            System.out.println("\n404! Server Is Unreachable");
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }

}
