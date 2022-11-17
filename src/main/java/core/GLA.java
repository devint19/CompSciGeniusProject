package core;

import genius.SongSearch;

import java.io.IOException;
import java.util.LinkedList;
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
        boolean game = true;
        int points = 0;
        while (game){
            Scanner s = new Scanner(System.in);
            GLA gla = new GLA();
            System.out.println("Let's play guess the hit!");
            System.out.println("Choose your Singer:");
            String singer = s.nextLine();
            System.out.println("What do you think their #1 hit is?");
            String hit = s.nextLine();
            boolean found = false;
            boolean won = false;
            int i = 0;
            while (found == false) {

                String tempHit = "";
                String lyrics = gla.search(singer).getHits().get(0).fetchLyrics();

                int length = gla.search(singer).getHits().size();

                if (i<length) {
                    tempHit = gla.search(singer).getHits().get(i).getTitle();
                } else {
                    found = true;
                }
                if (tempHit.equalsIgnoreCase(hit) || tempHit.equalsIgnoreCase("\u200B" + hit)) {
                    found = true;
                    won = true;
                }
                if (i == 9) {
                    found = true;
                }
                if (found == false) {
                    System.out.println("It's not hit #" + (i + 1) + "!");
                }
                i++;
            }
            if (won) {
                System.out.println("'" + hit + "'" + " was " + singer + "'s #" + i + " hit. You get " + (11 - i) + " points!");
                points+=(11-i);
            } else {
                System.out.println("That's not a Top 10 hit! No points for you.");
            }
            System.out.println();
            System.out.println("Here's their Top 10 list:");
            for (int j = 0; j < 10; j++) {
                int length = gla.search(singer).getHits().size();
                if (j<length) {
                    System.out.println("#" + (j + 1) + ": " + gla.search(singer).getHits().get(j).getTitle());
                }
            }

            System.out.println();
            System.out.println("Want to play again? Press 1 to Play Again, 2 to Play 'Guess the Lyric', or 3 to Quit");
            int playAgain = getNum();

            while (playAgain <= 0||playAgain > 3) {
                System.out.println("Try Again:");
                playAgain = getNum();
            }

            if (playAgain==3){
                System.out.println("You got " + points + " points!");
                System.out.println("Thanks for playing!");
                game=false;
            } else {
                System.out.println();
            }



            //FINISH THE LYRIC CODE
            if (playAgain==2){
                System.out.println("Let's play Finish the Lyric! Each correct guess is 5 points. Enter the number of the hit you'd like to play.");


                //validate the house down
                int selection = getNum();
                while (selection <= 0||selection > 10) {
                    System.out.println("Try Again:");
                    selection = getNum();
                }

                int start = 0;
                int end;
                boolean running = true;
                String lyrics = gla.search(singer).getHits().get(selection-1).fetchLyrics();
                System.out.println();
                while (running){
                    //starting at new line and cutting
                    end = lyrics.indexOf("\n");
                    String line = lyrics.substring(start,end);

                    //check to see if the line is a header, then just print it

                    if (line.indexOf("[")==-1 && !line.equals("")) {
                        //split into array of words
                        String[] arrOfLine = line.split(" ");
                        int remove = (int) (Math.random() * (arrOfLine.length));
                        String blank = arrOfLine[remove];
                        while (blank.equalsIgnoreCase("the") || blank.equalsIgnoreCase("and") || blank.equalsIgnoreCase("or") || blank.equalsIgnoreCase("is") || blank.equalsIgnoreCase("I") || blank.equalsIgnoreCase("a")){
                            remove = (int) (Math.random() * (arrOfLine.length));
                            blank = arrOfLine[remove];
                        }
                        arrOfLine[remove] = "_______";
                        blank = blank.replace(",","");
                        blank = blank.replace("(","");
                        blank = blank.replace(")","");
                        blank = blank.replace("-","");
                        blank = blank.replace("?","");

                        //print each word
                        for (String word : arrOfLine) {
                            System.out.print(word);
                            System.out.print(" ");
                        }
                        System.out.println();
                        System.out.print("Finish the lyric: ");
                        String guess = s.nextLine();
                        if (guess.equalsIgnoreCase(blank)){
                            points=points+5;
                            System.out.println("Slay the house down, 5 points! (" + points + " points)");
                        } else {
                            points=points-2;
                            System.out.println("You flopped and lose 2 points, it was: " + blank + " (" + points + " points)");
                        }
                    } else {
                        System.out.print(line);
                    }

                    //resetting for next time and ending
                    lyrics = lyrics.substring(end+1);
                    if (lyrics.indexOf("\n") == -1){
                        running=false;
                    }
                    System.out.println();
                }

                System.out.println("Want to play again? Press 1 to Play Again or 2 to Quit");
                playAgain = getNum();
                while (playAgain < 1 ||playAgain > 2) {
                    System.out.println("Try Again:");
                    playAgain = getNum();
                }
                if (playAgain==2){
                    System.out.println("Thanks for playing!");
                    game=false;
                } else {
                    System.out.println();
                }
            }
        }

        /*
        System.out.println("Searching...");
        long startMs = System.currentTimeMillis();
        System.out.println(gla.search(singer).getHits().get(0).fetchLyrics());
        System.out.println(System.currentTimeMillis() - startMs + "ms");
        System.out.print(gla.search("Taylor Swift").getHits().get(1).getImageUrl());
        */

    }
    public static int getNum() {
        int num = -1;
        try {
            Scanner s = new Scanner(System.in);
            String inputString = s.nextLine();
            num = Integer.parseInt(inputString);
        }
        catch (Exception flop) {
            System.out.println("Not a valid number");
        }
        return num;
    }
}

