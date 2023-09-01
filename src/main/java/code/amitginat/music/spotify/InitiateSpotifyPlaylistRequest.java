package code.amitginat.music.spotify;

import code.amitginat.ApiKeys;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class InitiateSpotifyPlaylistRequest {


    private ArrayList<SongInfo> songs = new ArrayList<>();
    private String playlistName;
    private int playlistCount;

    public InitiateSpotifyPlaylistRequest(String playlistId) throws IOException, ParseException, SpotifyWebApiException, InterruptedException {

        String token = SyncClient();

        var spotifyApi = new SpotifyApi.Builder().setAccessToken(token).build();

        var request = spotifyApi.getPlaylistsItems(playlistId).build();
        var playlistREQUEST = spotifyApi.getPlaylist(playlistId).build().execute();
        playlistName = playlistREQUEST.getName();
        playlistCount = playlistREQUEST.getTracks().getTotal();


        getPlaylistsItems_Sync(request, spotifyApi);

        getPlaylistsItems_Async(request);
    }
    public ArrayList<SongInfo> getSongs(){
        return songs;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public int getPlaylistCount() {
        return playlistCount;
    }

    private void getPlaylistsItems_Sync(GetPlaylistsItemsRequest request, SpotifyApi spotifyApi) {
        try {

            final Paging<PlaylistTrack> playlistTrackPaging = request.execute();

            PlaylistTrack[] tracks = playlistTrackPaging.getItems();
            for (PlaylistTrack track : tracks) {

                String thisid = track.getTrack().getId();
                Track thisTrack = spotifyApi.getTrack(thisid).build().execute();

                String artistName = thisTrack.getArtists()[0].getName();
                String songName = thisTrack.getName();
                SongInfo info = new SongInfo(artistName, songName);
                songs.add(info);
            }


        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void getPlaylistsItems_Async(GetPlaylistsItemsRequest request) {
        try {
            final CompletableFuture<Paging<PlaylistTrack>> pagingFuture = request.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final Paging<PlaylistTrack> playlistTrackPaging = pagingFuture.join();

        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public String SyncClient() {
        try {
            SpotifyApi api = SpotifyApi.builder().setClientId(ApiKeys.SPOTIFY_CLIENTID).setClientSecret(ApiKeys.SPOTIFY_CLIENTSECRET).build();
            final ClientCredentialsRequest clientCredentialsRequest = api.clientCredentials()
                    .build();

            final CompletableFuture<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest.executeAsync();

            final ClientCredentials clientCredentials = clientCredentialsFuture.join();
            return clientCredentials.getAccessToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
