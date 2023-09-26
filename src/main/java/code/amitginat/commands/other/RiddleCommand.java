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

public class RiddleCommand extends AbstractCommand {
    @Override
    public void run() {
        JSONObject result = getRiddle();
        if(result == null){
            channel.sendMessage("Error").queue();
        }
        else {
            String question = result.getString("question");
            String answer = result.getString("answer");
            channel.sendMessageFormat("**Question**: *%s*%n%n**Answer**: ||*%s*||",
                    question,
                    answer).queue();
        }
    }
    @Override
    public String getType() {
        return "other";
    }
    @Override
    public String prefix() {
        return "riddle";
    }

    @Override
    public String explain() {
        return "חידה";
    }


    private JSONObject getRiddle(){
        try{
            URL link = new URL("https://api.api-ninjas.com/v1/riddles");
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", ApiKeys.API_NINJAS_KEY);
            connection.setRequestProperty("accept", "application/json");

            connection.connect();
            if (connection.getResponseCode() > 299) {
                return null;
            }
            else {
                String fullReply = convertStreamToString(connection.getInputStream());
                String jsonReply = fullReply.replace("[", "").replace("]", "");
                return new JSONObject(jsonReply);
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
