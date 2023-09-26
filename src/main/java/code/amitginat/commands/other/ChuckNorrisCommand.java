package code.amitginat.commands.other;

import code.amitginat.ApiKeys;
import code.amitginat.commands.AbstractCommand;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChuckNorrisCommand extends AbstractCommand {
    @Override
    public void run() {
        String result = getJoke();
        if(result.equals("Error")){
            channel.sendMessage("Chuck.").queue();
        }
        else {
            channel.sendMessage(result).queue();
        }
    }
    @Override
    public String getType() {
        return "other";
    }
    @Override
    public String prefix() {
        return "chuck-norris";
    }

    @Override
    public String explain() {
        return "צאק נוריס אחי";
    }


    private String getJoke(){
        try{
            URL link = new URL("https://api.api-ninjas.com/v1/chucknorris?");
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", ApiKeys.API_NINJAS_KEY);
            connection.setRequestProperty("accept", "application/json");

            connection.connect();
            if (connection.getResponseCode() > 299) {
                return "Error";
            }
            else {
                String jsonReply = convertStreamToString(connection.getInputStream());
                JSONObject jsonObject = new JSONObject(jsonReply);
                return jsonObject.getString("joke");
            }
        }
        catch (Throwable e){
            return "Error";
        }
    }
    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
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
