import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Objects;

import org.json.*;

public class DB {
    private static final DB db = new DB();
    private final static String response = db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/selectuser");

    public String makeGETRequest(String urlName){
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;
        try {         //THIS CONNECTION HAS POTENTIAL FOR ERRORS
            URL url = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();  //open the connection
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));//get the output of GET request (from above)
            sb = new StringBuilder();
            while ((line = rd.readLine()) != null) //take all the data from website to a big string
            {
                sb.append(line).append('\n'); //add each line to StringBuilder
            }
            conn.disconnect(); //no more lines, disconnect
            return sb.toString(); //return the big string
        } catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public void parseJSON(String jsonString){
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject curObject = array.getJSONObject(i);
                System.out.println("The coach for the " + curObject.getString("Date") + " session is " + curObject.getString("Coach"));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public boolean checkUser(String jsonString, String userName, String password){
        boolean flag = false;
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject curObject = array.getJSONObject(i);
                if(curObject.getString("NAME").equals(userName))
                {
                    if(curObject.getString("PASSWORD").equals(password))
                    {
                        flag = true;
                    }
                }
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return flag;
    }

    public void rememberLastUser(String jsonString, String user, String password){
        try {
            JSONArray array = new JSONArray(jsonString);
            db.makeGETRequest("https://studev.groept.be/api/a21ib2c04/reset_lastRemember");
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject curObject = array.getJSONObject(i);
                if(checkUser(response, user, password))
                    db.makeGETRequest(
                            "https://studev.groept.be/api/a21ib2c04/update_lastRemember/" + getUserID(response, user));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public boolean checkIfRemember(String jsonString){
        boolean flag = false;
        try {
            JSONArray array = new JSONArray(jsonString);
            for(int i =0; i < array.length(); i++)
            {
                JSONObject curObject = array.getJSONObject(i);
                if(curObject.getInt("lastRemember") == 1)
                    flag = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public int getUserID(String jsonString, String name){
        int ID = 0;
        try{
            JSONArray array = new JSONArray(jsonString);
            for(int i = 0; i < array.length(); i++){
                JSONObject curObject = array.getJSONObject(i);
                if(curObject.getString("NAME").equals(name))
                    ID = Integer.parseInt(curObject.getString("ID"));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return ID;
    }

    public String getUserName(String jsonString, int ID){
        String userName = null;
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject curObject = array.getJSONObject(i);
                if(curObject.getInt("ID") == App.ID)
                    userName = curObject.getString("NAME");
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return userName;
    }

    public String getRememberedUserName(String jsonString){
        String remembereduserName = null;
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject curObject = array.getJSONObject(i);
                if(curObject.getInt("lastRemember") == 1)
                    remembereduserName = curObject.getString("NAME");
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return remembereduserName;
    }

    public String getPassword(String jsonString, int ID){
        String password = null;
        try{
            JSONArray array = new JSONArray(jsonString);
            for(int i = 0; i < array.length(); i++)
            {
                JSONObject curObject = array.getJSONObject(i);
                if(curObject.getInt("ID") == App.ID)
                    password = curObject.getString("PASSWORD");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return password;
    }

    public String getRememberedPassword(String jsonString){
        String rememberedpassword = null;
        try{
            JSONArray array = new JSONArray(jsonString);
            for(int i = 0; i < array.length(); i++)
            {
                JSONObject curObject = array.getJSONObject(i);
                if(curObject.getInt("lastRemember") == 1)
                    rememberedpassword = curObject.getString("PASSWORD");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rememberedpassword;
    }

    public String getString(String jsonString, String desiredType){
        String result = null;
        try {
            JSONArray array = new JSONArray(jsonString);
            JSONObject curObject = array.getJSONObject(0);
            //System.out.println("The coach for the " + curObject.getString("Date") + " session is " + curObject.getString("Coach"));
            result = curObject.getString(desiredType);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    public String[] getAllString(String jsonString){
        String[] result = new String[5];
        try {
            JSONArray array = new JSONArray(jsonString);
            JSONObject curObject = array. getJSONObject(0);
            //System.out.println("The coach for the " + curObject.getString("Date") + " session is " + curObject.getString("Coach"));
            result[0] = curObject.getString("enableLock");
            result[1] = curObject.getString("enableLamp");
            result[2] = curObject.getString("enableCurtain");
            result[3] = curObject.getString("lampState");
            result[4] = curObject.getString("curtainState");
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        DB rc = new DB();
        String response = rc.makeGETRequest("https://studev.groept.be/api/a21ib2demo/all" );
        rc.parseJSON(response);
    }
}


