
import java.util.Scanner;

import core.GLA;
import core.HttpManager;
import genius.SongSearch;

import java.io.IOException;

public class test {

    private HttpManager httpManager = new HttpManager();
/*
    public SongSearch search(String query) throws IOException {
        return new SongSearch(this, query);
    }

 */

    public HttpManager getHttpManager() {
        return this.httpManager;
    }

    public static void main(String[] args) throws Exception {

        System.out.println("HAIIII");

    }
}
