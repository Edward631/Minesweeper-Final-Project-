import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreManager {
    private static final String SCORE_FILE = "scores.txt";

    public ScoreManager() {
        File file = new File(SCORE_FILE);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Saves a new time ONLY if it's better (lower)
    public void saveScore(String name, int seconds) {
        List<String> scores = loadScores();
        scores.add(name + ":" + seconds + "s");
        scores.sort((s1, s2)->{ 
            int time1 = Integer.parseInt(s1.split(": ")[1].replace("s", ""));
            int time2 = Integer.parseInt(s2.split(": ")[1].replace("s", ""));
            return Integer.compare(time1, time2);
        });
        writeScores(scores);
    }

    // Read scores from file
    public List<String> loadScores() {
        List<String> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            } 
        }catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    //public int getFastestScore() {
    //    List<String> scores = loadScores();
   //     return scores.isEmpty() ? -1 : scores.get(0);
  //  }

    private void writeScores(List<String> scores) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORE_FILE))) {
            for (String time : scores) {
                writer.println(time);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
