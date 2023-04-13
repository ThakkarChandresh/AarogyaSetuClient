package in.gov.aarogyasetu.client.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class UtilityMethods
{

    public static JSONObject makeRequest(JSONObject request) throws IOException
    {

        Socket socket = new Socket("localhost", 9090);

        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        writer.println(request);

        JSONObject response = new JSONObject(reader.readLine());

        socket.close();

        reader.close();

        writer.close();

        return response;

    }

}
