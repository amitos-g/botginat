package code.amitginat.music.spotify;

import code.amitginat.ApiKeys;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class InitiateSpotifyTrack {



    private SongInfo thisTrack;

    public InitiateSpotifyTrack(String trackID) throws IOException, ParseException, SpotifyWebApiException, InterruptedException {

        String token = SyncClient();
        var spotifyApi = new SpotifyApi.Builder().setAccessToken(token).build();
        var request = spotifyApi.getTrack(trackID).build();


       getTrack_Sync(request, spotifyApi);
        getPlaylistsItems_Async(request);
    }

    public SongInfo getThisTrack(){
        return thisTrack;
    }

    private void getTrack_Sync(GetTrackRequest request, SpotifyApi spotifyApi) {
        try {

            Track track = request.execute();
            thisTrack = new SongInfo(track.getArtists()[0].getName(), track.getName());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void getPlaylistsItems_Async(GetTrackRequest request) {
        try {
            CompletableFuture<Track> trackCompletableFuture = request.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            var futureJoined = trackCompletableFuture.join();
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
