import java.util.*;

public class PGM_42579 {

    class Song {
        int num, play;

        public Song(int num, int play) {
            this.num = num;
            this.play = play;
        }
    }

    public int[] solution(String[] genres, int[] plays) {
        Map<String, Integer> playPerGenre = new HashMap<>();
        Map<String, ArrayList<Song>> songInGenre = new HashMap<>();

        for (int i = 0; i < genres.length; i++) {
            String g = genres[i];

            playPerGenre.put(
                    g,
                    playPerGenre.getOrDefault(g, 0) + plays[i]
            );

            songInGenre
                    .computeIfAbsent(g, k -> new ArrayList<>())
                    .add(new Song(i, plays[i]));
        }

        List<String> genreKey = new ArrayList<>(playPerGenre.keySet());

        genreKey.sort((a, b) ->
                Integer.compare(playPerGenre.get(b), playPerGenre.get(a))
        );

        List<Integer> result = new ArrayList<>();

        for (String gen : genreKey) {
            List<Song> songs = songInGenre.get(gen);

            songs.sort((a, b) -> {
                if (a.play == b.play) {
                    return Integer.compare(a.num, b.num);
                }
                return Integer.compare(b.play, a.play);
            });

            result.add(songs.get(0).num);

            if (songs.size() >= 2) {
                result.add(songs.get(1).num);
            }
        }

        return result.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
