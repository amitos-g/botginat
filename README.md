
<h1> Bot Ginat </h1>

<h3> Introduction </h3>

<p>
Bot Ginat is a Discord bot written in Java. <br>
The bot includes music, news, useful commands and more.

It uses:

* JDA: https://github.com/DV8FromTheWorld/JDA for integration with Discord's API <br>
* Lavaplayer: https://github.com/sedmelluq/lavaplayer for playing music
* Spotify's API: https://developer.spotify.com/documentation/web-api/ for getting song info to search in YouTube with Lavaplayer
* Jsoup: https://jsoup.org/ for scraping HTML data (ynet news command)
</p>

<h3> Music </h3>

<p>
Bot Ginat can play music from both YouTube and Spotify. <br>
It can also play playlists from both platforms! <br>
To play a song, simply type `ginat play [song name or link / playlist link]`. the bot will recognize it's type.
</p>

<h3>Features</h3>
* **Help:** Bot Ginat can display a list of all of its commands and how to use them.

* **News updates:** Bot Ginat can send news updates from Ynet every minute. To enable this feature, simply type `ginat ynet`. To disable it, type `ginat ynet` again.

* **Clearing Messages:** Bot ginat can clear messages in the channel you want.

* **Time and date:** Bot Ginat can tell you the current time and date, as well as the time until a specified time or date.
    
* **Fun commands:** Bot Ginat has a number of fun commands, such as riddles, dad jokes, and Chuck Norris jokes.

<h3>Installation</h3>

To install Bot Ginat, first make sure you have:
* A JDA Token
* A Spotify Client & Secret ID
* An Api Ninjas Key

Then, follow these steps:

1. Clone the repository:

```
git clone https://github.com/amitos-g/botginat
```

2. Install the dependencies with maven:

```
lavaplayer, JDA, jsoup, spotify-web-api-java, json.
```

3. Create an ApiKeys.java file inside the code.amitginat package and add the following lines:

```
public static final String SPOTIFY_CLIENT_ID=[your-client-id];
public static final String SPOTIFY_CLIENT_SECRET=[your-client-secret];
public static final String JDA_TOKEN=[your-bot-token];
public static final String API_NINJAS_KEY=[your-api-ninjas-key];
```

4. Run the bot.


<h3>Examples</h3>

Here are some examples of how to use Bot Ginat:

**Showing the help menu:**

```
ginat help
```

**Playing a song from YouTube:**

```
ginat play https://www.youtube.com/watch?v=dQw4w9WgXcQ
```

**Playing a song from Spotify:**

```
ginat play https://open.spotify.com/track/4cOdK2wGLETKBW3PvgPWqT?si=5eee516f20344855
```
**Activating/Stopping Ynet news updates:**

```
ginat ynet
```

**Getting the time:**

```
ginat time
```

**Getting the time until a specified date:**

```
ginat days-until DD:MM:YY
```

**Getting a riddle:**

```
ginat riddle
```

**Getting a dad joke:**

```
ginat dad-joke
```

**Getting a Chuck Norris joke:**

```
ginat chuck-norris
```

