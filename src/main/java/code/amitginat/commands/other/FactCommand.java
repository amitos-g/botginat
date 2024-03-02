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

public class FactCommand extends AbstractCommand {

    @Override
    public void run() {
        JSONObject fact = getFact();
        if (fact == null) {
            channel.sendMessage("Try again later").queue();
            return;
        }
        channel.sendMessage(fact.getString("fact")).queue();
    }



    @Override
    public String getType() {
        return "other";
    }
    @Override
    public String name() {
        return "fact";
    }

    @Override
    public String explain() {
        return "עובדה רנדומלית";
    }


    private JSONObject getFact(){
        try{
            URL link = new URL("https://api.api-ninjas.com/v1/facts?limit=1");
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
                fullReply = fullReply.replace("[", "").replace("]", "");
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
