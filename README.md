
<h1> Bot Ginat </h1>

<h3> Introduction </h3>

<p>
Bot Ginat is a versatile Discord bot written in Java. <br>
It has a wide range of features, including music playback, news updates, and fun commands.

It uses the following libraries:

* JDA: https://github.com/DV8FromTheWorld/JDA for integration with Discord's API <br>
* Lavaplayer: https://github.com/sedmelluq/lavaplayer for playing music
* Spotify's API: https://developer.spotify.com/documentation/web-api/ for getting song info to search in YouTube with Lavaplayer
* Jsoup: https://jsoup.org/ for scraping HTML data (ynet news command)
</p>

<h3> Music </h3>

<p>
Bot Ginat can play music from both YouTube and Spotify. <br>
It can also play playlists from both platforms! <br>
To play a song, simply type `ginat play [song name or link]`. the bot will recognize it's type.
</p>

<h3> Bot Ginat also has a number of other music-related features, such as: </h3>

* **`ginat stop`:** Stops the current song
* **`ginat resume`:** Resumes the current song
* **`ginat skip`:** Skips the current song
* **`ginat now-playing`:** Shows the song that is currently playing
* **`ginat whats-next`:** Shows the songs in the queue
* **`ginat clear-music`:** Clears the songs in the queue


<h3>Other Features</h3>

In addition to its music features, Bot Ginat also has a number of other features, such as:

* **News updates:** Bot Ginat can send news updates from Ynet every minute. To enable this feature, simply type `ginat ynet`. To disable it, type `ginat ynet` again.
* **Time and date:** Bot Ginat can tell you the current time and date, as well as the time until a specified time or date.
* **Help:** Bot Ginat can display a list of all of its commands and how to use them.
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

2. Install the dependencies:

```
mvn install
```

3. Create an ApiKeys file in the project directory and add the following values:

```
SPOTIFY_CLIENT_ID=[your-client-id]
SPOTIFY_CLIENT_SECRET=[your-client-secret]
JDA_TOKEN=[your-bot-token]
API_NINJAS_KEY=[your-api-ninjas-key]
```

4. Run the bot:

```
mvn exec:java
```


<h3>Examples</h3>

Here are some examples of how to use Bot Ginat:

**Playing a song from YouTube:**

```
ginat play https://www.youtube.com/watch?v=dQw4w9WgXcQ
```

**Playing a song from Spotify:**

```
ginat play https://open.spotify.com/track/4cOdK2wGLETKBW3PvgPWqT?si=5eee516f20344855
```

**Playing a playlist from YouTube:**

```
ginat play [youtube playlist link]
```

**Playing a playlist from Spotify:**

```
ginat play [spotify playlist link]
```

**Activating Ynet news updates:**

```
ginat ynet
```

**Getting the time:**

```
ginat time
```

**Showing the help menu:**

```
ginat help
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

