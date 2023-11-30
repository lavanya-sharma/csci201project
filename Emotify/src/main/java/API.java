

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/Api")
public class api extends HttpServlet {
	
    private static String clientId = "c993bc96218846729531c4817aa41b26"; // Replace with your client ID
    private static String clientSecret = "222d2a5f0c074a1e828b4eee8c3a8ea5"; // Replace with your client secret

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String prompt = request.getParameter("prompt");
        try {
    		Queue<String> idQueue;
            String accessToken = getClientCredentialsAccessToken();
            idQueue = searchSongs(accessToken, prompt);
            String outString = ParseToJson(idQueue);
            PrintWriter out = response.getWriter();
            out.print(outString);
            out.flush();
            out.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
    public static String getClientCredentialsAccessToken() throws Exception {
        String encodedCredentials = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
        URL url = new URL("https://accounts.spotify.com/api/token");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        String data = "grant_type=client_credentials";

        try (OutputStream os = connection.getOutputStream()) {
            os.write(data.getBytes());
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        }

        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        return jsonObject.get("access_token").getAsString();
    }
    
    public static Queue<String> searchSongs(String accessToken, String prompt) throws Exception {
    	//CHAT GPT API
    	
    	 // Replace with your actual API key
        String apiKey = "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";
        
        String engineeredPrompt = " Given the description of this person's mood or request, search through Spotify's song database to give me a list of 5 songs that this person would want to most likely listen to and align with emotionally. Format in a comma-separated list with no quotation marks. Example: 'songName1 - songArtist1, songName2 - songArtist2, songName3 - songArtist3, songName4 - songArtist4, songName5 - songArtist5'. DON'T GENERATE EXTRA TEXT AND STRICTLY ADHERE TO THE STRUCTURE OF THE OUTPUT AS OUTLINED.";

        String jsonPayload = String.format("{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}", prompt + engineeredPrompt);

        // Setup the HTTP Client and Request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(BodyPublishers.ofString(jsonPayload))
                .build();

        // Send the request and get the response
        
        String content ="";
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            // Navigate through the object to get the 'content' field
            content = jsonObject.getAsJsonArray("choices")
                                       .get(0).getAsJsonObject()
                                       .getAsJsonObject("message")
                                       .get("content").getAsString();

            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        ArrayList<Map<String,String>> songList = parseSongs(content);
	     /*   
        for(int i = 0; i<songList.size();i++) {
        	for(int j=0; j<songList.get(i).size();j++) {
        		System.out.println(songList.get(i).get(j));
        	}
        }
    	*/
        Queue<String> resultQueue = new LinkedList<>();
        for (Map<String, String> songMap : songList) {
            // Assuming each map has only one entry: song name (key) and artist name (value)
            Map.Entry<String, String> entry = songMap.entrySet().iterator().next();
            String songName = entry.getKey();
            String artistName = entry.getValue();

            String sContent = "remaster%2520track%3A" + songName + "%2520artist%3A" + artistName;
            String encodedKeyword = java.net.URLEncoder.encode(sContent, StandardCharsets.UTF_8.toString());
            URL url = new URL("https://api.spotify.com/v1/search?q=" + encodedKeyword + "&type=track&limit=1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            com.google.gson.JsonArray tracks = jsonObject.getAsJsonObject("tracks").getAsJsonArray("items");

            if (tracks.size() > 0) {
                JsonObject track = tracks.get(0).getAsJsonObject();
                String trackName = track.get("name").getAsString();
                String ID = track.get("id").getAsString();

                resultQueue.add(ID);
            }
        }
        return resultQueue;
    }
    
    private static ArrayList<Map<String, String>> parseSongs(String input) {
        // Split the input string into song-artist pairs
        String[] pairs = input.split(", ");

        ArrayList<Map<String, String>> songs = new ArrayList<>();
        for (String pair : pairs) {
            // Split each pair into song and artist
            String[] songInfo = pair.split(" - ");
            
            // Check if splitting was successful (we expect 2 elements)
            if (songInfo.length == 2) {
                Map<String, String> songMap = new HashMap<>();
                songMap.put(songInfo[0], songInfo[1]); // Key: Song Name, Value: Artist Name
                songs.add(songMap);
            }
        }
        
        return songs;
    }
    
    public static String ParseToJson( Queue<String> idQueue) {

        // Convert the Queue to a JSON array
    	com.google.gson.JsonArray songIDs = new com.google.gson.JsonArray();

        while (!idQueue.isEmpty()) {
            // Add each String element to the JSON array
            songIDs.add(idQueue.remove());
        }
        
        com.google.gson.JsonObject object = new com.google.gson.JsonObject();
        object.add( "songids", songIDs);

        Gson gsonObject = new Gson();
        String outString = gsonObject.toJson(object);
        return outString;
    }
 
}
