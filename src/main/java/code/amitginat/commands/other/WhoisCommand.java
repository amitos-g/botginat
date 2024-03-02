package code.amitginat.commands.other;

import code.amitginat.ApiKeys;
import code.amitginat.commands.AbstractCommand;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WhoisCommand extends AbstractCommand {

    @Override
    public void run() {
        String domain = message.replace("ginat whois", "").replace(" ", "");
        JSONObject response = getWhois(domain);
        if(response == null){
            channel.sendMessage("invalid url! usage: ginat whois [url]").queue();
            return;
        }
        StringBuilder result = new StringBuilder();
        result.append("result for domain %s%n------------------%n".formatted(domain));
        String builtResult = buildResult(response, result);
        channel.sendMessage(builtResult).queue();

    }


    public String buildResult(JSONObject response, StringBuilder result){
        try{
            String name = response.getString("registrant_name");
            result.append("Owner Name : %s%n".formatted(name));
        }
        catch (JSONException ignore ){}
        try{
            JSONArray address = (JSONArray) response.get("registrant_address");
            result.append("Owner Address : [");
            for (var val : address ) {
                result.append(" ").append(val.toString()).append(",");
            }
            result.deleteCharAt(result.length() - 1);
            result.append("]\n");
        }
        catch (JSONException  | ClassCastException ignore){}
        try{
            String emails = response.getString("emails");
            result.append("Email/s : %s%n".formatted(emails));
        }
        catch (JSONException ignore ){}
        try{
            String phone = response.getString("phone");
            result.append("Phone: %s%n".formatted(phone));
        }
        catch (JSONException ignore ){

        }


        try{
            JSONArray name_servers = (JSONArray) response.get("name_servers");
            result.append("Name Servers : [");
            for (var val : name_servers ) {
                result.append(" ").append(val.toString()).append(",");
            }
            result.deleteCharAt(result.length() - 1);
            result.append("]\n");
        }
        catch (JSONException  | ClassCastException ignore){}

        try{
            String registrar = response.getString("registrar");
            result.append("Registrar: %s%n".formatted(registrar));
        }
        catch (JSONException ignore ){

        }





        return result.toString();
    }

    @Override
    public String getType() {
        return "other";
    }
    @Override
    public String name() {
        return "whois";
    }

    @Override
    public String explain() {
        return "חפש מידע על אתר";
    }


    private JSONObject getWhois(String domain){
        try{
            URL link = new URL("https://api.api-ninjas.com/v1/whois?domain=%s".formatted(domain));
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", ApiKeys.API_NINJAS_KEY);
            connection.setRequestProperty("accept", "application/json");

            connection.connect();
            if (connection.getResponseCode() > 299) {
                System.out.println(connection.getResponseMessage());
                System.out.println(connection.getResponseCode());
                return null;
            }
            else {
                String fullReply = convertStreamToString(connection.getInputStream());
                return new JSONObject(fullReply);
            }
        }
        catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }
}
