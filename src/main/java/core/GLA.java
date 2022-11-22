//importing code
package core;
import genius.SongSearch;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class GLA {
    //initialize HttpManager and SongSearch
    private HttpManager httpManager = new HttpManager();
    public SongSearch search(String query) throws IOException {
        return new SongSearch(this, query);
    }
    public HttpManager getHttpManager() {
        return this.httpManager;
    }
    public static void main(String[] args) throws Exception {
        //when game is false, the player is done playing and the game ends
        boolean game = true;
        //initialize points to 0
        int points = 0;
        //game repeats each round with while loop
        while (game){
            //initialize scanner and GLA
            Scanner s = new Scanner(System.in);
            GLA gla = new GLA();
            //get player singer input (no validation needed)
            System.out.println("Let's play guess the hit!");
            System.out.println("Choose your Singer:");
            String singer = s.nextLine();
            //get player song input
            System.out.println("What do you think their #1 hit is?");
            String hit = s.nextLine();

            //replace apostrophe (genius uses different type of apostrophe)
            hit = hit.replace("'","’");

            //found is if the loop should keep running
            boolean found = false;
            //won is if the player has the correct guess
            boolean won = false;
            int i = 0;
            //loop to check if hits match guess
            while (found == false) {
                String tempHit = "";
                //get #hits artists has (if less than 10)
                int length = gla.search(singer).getHits().size();
                //check that # of that hit exists before trying to access
                if (i<length) {
                    tempHit = gla.search(singer).getHits().get(i).getTitle();
                } else {
                    //if singer doesn't have 10 hits, end the loop
                    found = true;
                }
                //checks if the player guess is the same as the artist's hit
                //\u200B is the lowercase bug thing

                if (tempHit.equalsIgnoreCase(hit) || tempHit.equalsIgnoreCase("\u200B" + hit)) {
                    //if the player is correct, end the loop and store that they were correct
                    found = true;
                    won = true;
                }
                //if you're at the 10th hit, stop checking and end the loop
                if (i == 9) {
                    found = true;
                }
                //if they were incorrect on this guess, print that it was not this # hit
                if (found == false) {
                    System.out.println("It's not hit #" + (i + 1) + "!");
                }
                i++;
            }

            //if they won, award points and print that they got it right
            if (won) {
                System.out.println("'" + hit + "'" + " was " + singer + "'s #" + i + " hit. You get " + (11 - i) + " points!");
                points+=(11-i);
            } else {
                //if won was never true, they got it wrong and get no points
                System.out.println("That's not a Top 10 hit! No points for you.");
            }

            System.out.println();
            //print out the entire top 10 list for the player to see what the hits actually were and give them options
            //for playing guess the lyric
            System.out.println("Here's their Top 10 list:");
            for (int j = 0; j < 10; j++) {
                int length = gla.search(singer).getHits().size();
                //print out the top 10 hits assuming they have 10 hits; if they have <10 it will only print that many
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
                System.out.println("Thanks for playing! You had " + points + " points!");
                game=false;
            } else {
                System.out.println();
            }



            //Code for Finish the Lyric Game if selected by player
            if (playAgain==2){
                System.out.println("Let's play Finish the Lyric! Each correct guess is 5 points. Enter the number of the hit you'd like to play.");

                //validate the hit is a number from their list
                int selection = getNum();

                //get length of hits again so that user can't check a hit greater than the number they have
                int lengthHits = gla.search(singer).getHits().size();

                //validate that the hit number picked exists
                while (selection <= 0||selection > lengthHits) {
                    System.out.println("Try Again:");
                    selection = getNum();
                }

                //start is where you should start each line cut
                int start = 0;
                //end is where each line should be cut
                int end;
                //running is false when the lyrics are over
                boolean running = true;

                //get song lyrics
                String lyrics = gla.search(singer).getHits().get(selection-1).fetchLyrics();
                System.out.println();

                //while loop to keep playing until the game is over
                while (running){
                    //starting at new line and cutting
                    end = lyrics.indexOf("\n");
                    String line = lyrics.substring(start,end);

                    //check to make sure line is not a header or an empty line
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
                        //get user guess (no validation needed)
                        String guess = s.nextLine();
                        //if guess is correct, award points
                        if (guess.equalsIgnoreCase(blank)){
                            points=points+5;
                            System.out.println("Correct, 5 points! (" + points + " points)");
                        } else {
                            //if guess is wrong, they lose points
                            points=points-2;
                            System.out.println("You flopped and lose 2 points, it was: " + blank + " (" + points + " points)");
                        }
                        //this line runs if the line is just a header, and then just print the line
                    } else {
                        System.out.print(line);
                    }
                    //resetting for next line and check if lyrics are over; if over, end loop
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
                    System.out.println("Thanks for playing! You had " + points + " points!");
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

