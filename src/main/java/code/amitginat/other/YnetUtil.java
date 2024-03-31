package code.amitginat.other;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;

import code.amitginat.util.GinatUtil;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import org.jsoup.Jsoup;

public class YnetUtil{
    public MessageChannelUnion thisChannel;
    public Thread thisThread;
    public boolean isActive;
    public String lastID;
    public static HashMap<Long, YnetUtil> running = new HashMap<>();


    public YnetUtil(MessageChannelUnion channel){
        thisChannel = channel;
        lastID = "#";
    }

    public static void execute(MessageChannelUnion channel){
        long id = channel.getIdLong();
        if (running.containsKey(id)){
            YnetUtil existing = running.get(id);
            existing.stop();
            running.remove(id);
            return;
        }
        YnetUtil newUtil = new YnetUtil(channel);
        newUtil.activate();
        running.put(id, newUtil);

    }
    public void activate(){
        isActive = true;
        Thread thread = new Thread(() -> {
            var before = Instant.now();
            while (isActive) {
                var current = Instant.now();
                if (Duration.between(before, current).toSeconds() <= 30) {
                    before = current;
                    try {
                        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless"));
                        driver.get("https://www.ynet.co.il/news/category/184");
                        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
                        Thread.sleep(5000);
                        var radio = driver.findElement(By.cssSelector("input.typeOfViewInput[type=\"radio\"][name=\"typeOfView\"][value=\"expanded\"][data-uw-rm-form=\"nfx\"] + div"));
                        radio.click();
                        String html = driver.getPageSource();
                        Document doc = Jsoup.parse(html);
                        Elements accordions = doc.getElementsByClass("AccordionSection");
                        Element first = accordions.first();
                        var id = first.attributes().get("id");
                        if (id.equals(lastID)) {
                            return;
                        }
                        lastID = id;
                        var title = first.getElementsByClass("titleRow").get(0).getElementsByClass("title").get(0).text();
                        var date = first.getElementsByClass("titleRow").get(0).getElementsByClass("date").get(0).text();
                        var body = first.getElementsByClass("itemBody").get(0).text();
                        GinatUtil.sendFullEmbed(thisChannel, "botginat ynet (%s)".formatted(date), title, new MessageEmbed.Field("", body, false));
                        driver.quit();

                    } catch (Exception s) {
                        GinatUtil.sendEmbed(thisChannel, "Error In Ynet Util");
                        stop();
                        s.printStackTrace();

                    }

                }
            }
        });

        thisThread = thread;
        thread.start();
        GinatUtil.sendEmbed(thisChannel, "Activated Ynet Util");

    }
    public void stop(){
        isActive = false;
        thisThread.interrupt();
        thisThread = null;
        GinatUtil.sendEmbed(thisChannel, "Stopped Ynet Util");
    }
}
