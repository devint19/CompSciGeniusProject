package core;

import genius.SongSearch;

import java.io.IOException;
import java.util.Scanner;

public class GLA {

    private HttpManager httpManager = new HttpManager();

    public SongSearch search(String query) throws IOException {
        return new SongSearch(this, query);
    }

    public HttpManager getHttpManager() {
        return this.httpManager;
    }

    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        GLA gla = new GLA();
        System.out.println("Let's play guess the hit!");
        System.out.println("Choose your Singer:");
        String singer = s.nextLine();
        System.out.println("What do you think their #1 hit is?");

        String hit = s.nextLine();

        boolean found = false;
        boolean won=false;
        int i=0;
        while (found==false){
            String tempHit=gla.search(singer).getHits().get(i).getTitle();
            if (tempHit.equalsIgnoreCase(hit)) {
                found = true;
                won=true;
            }
            if (i==9){
                found=true;

            }
            if (found==false) {
                System.out.println("It's not hit #" + (i + 1) + "!");
            }
            i++;
        }
        if (won) {
            System.out.println("'" + hit + "'" + " was " + singer + "'s #" + i + " hit. You get " + (11 - i) + " points!");
        } else {
            System.out.println("That's not a Top Ten hit! No points for you.");
        }
        System.out.println();
        System.out.println("Here's their Top Ten list:");
        for (int j=0; j<10; j++){
            System.out.println("#" + (j+1) + ": " + gla.search(singer).getHits().get(j).getTitle());
        }


        /*
        System.out.println("Searching...");
        long startMs = System.currentTimeMillis();
        System.out.println(gla.search(singer).getHits().get(0).fetchLyrics());
        System.out.println(System.currentTimeMillis() - startMs + "ms");
        System.out.print(gla.search("Taylor Swift").getHits().get(1).getImageUrl());
        */

    }

}
