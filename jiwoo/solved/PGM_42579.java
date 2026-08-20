import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;

class Solution_42579_베스트앨범 {
    
    static class Plays {
        int totalPlays = 0;
        PriorityQueue<int[]> songs;
        
        public Plays(int id, int plays) {
            this.totalPlays += plays;
            songs = new PriorityQueue<>((a, b) -> {
                if(a[1] == b[1]) return a[0] - b[0];
                else return b[1] - a[1];});
            songs.add(new int[] {id, plays});
        }
    }
    
    public int[] solution(String[] genres, int[] plays) {
        
        Map<String, Plays> map = new HashMap<>();
        
        for(int i = 0; i < genres.length; i++) {
            
            if(map.containsKey(genres[i])) {
                Plays cur = map.get(genres[i]);
                cur.totalPlays += plays[i];
                cur.songs.add(new int[] {i, plays[i]});
            } else {
                map.put(genres[i], new Plays(i, plays[i]));
            }
            
        }
        
        PriorityQueue<Plays> pq = new PriorityQueue<>((a, b) -> b.totalPlays - a.totalPlays);
        for(Plays genre : map.values()) {
            pq.add(genre);
        }
        
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()) {
            Plays genre = pq.poll();
            
            int[] song1 = genre.songs.poll();
            result.add(song1[0]);
            
            if(genre.songs.isEmpty()) continue;
            
            int[] song2 = genre.songs.poll();
            result.add(song2[0]);
        }
        
        int[] answer = new int[result.size()];
        for(int i = 0; i < result.size(); i++) {
            answer[i] = result.get(i);
        }
        return answer;
    }
}