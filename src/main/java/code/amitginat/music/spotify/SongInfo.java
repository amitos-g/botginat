package code.amitginat.music.spotify;

public class SongInfo{
    private String author;
    private String title;

    public SongInfo(String author, String title) {
        this.author = author;
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", title, author);
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
