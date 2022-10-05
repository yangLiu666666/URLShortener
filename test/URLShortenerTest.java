import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * This is test for the URL shortener.
 */
public class URLShortenerTest {
    private URLShortener test1;
    private HashMap<String, String> map;

    @Before
    public void setup() {
        map = new HashMap<>();
        test1 = new URLShortener();
    }

    @Test
    public void formatURL() {
        String url = "https://www.google.com";
        String shortUrl = test1.shortenURL(url);
        assertEquals("www.google.com", test1.retrieveURL(shortUrl));
    }

    @Test
    public void shortenURL() {
        String[] longUrls = new String[]{"www.google.com", "www.google.com",
                "http://www.youtube.com", "www.youtube.com", "www.amazon.com",
                "www.applause.com", "https://www.applause.com/"};
        for (String url : longUrls) {
            String shortUrl = test1.shortenURL(url);
            map.put(shortUrl, test1.formatURL(url));
        }

        assertEquals(4, map.size());
        for (String key : map.keySet()) {
            assertEquals(map.get(key), test1.retrieveURL(key));
        }
    }

    @Test
    public void retrieveURL() {
        String longUrl = "https://www.google.com";
        longUrl = test1.formatURL(longUrl);
        String shortUrl = test1.shortenURL(longUrl);
        map.put(shortUrl, longUrl);
        assertEquals(1, map.size());
        assertEquals(longUrl, test1.retrieveURL(shortUrl));
    }

    @Test
    public void basicAdmin() {
        String longUrl1 = "www.google.com";
        String longUrl2 = "http://www.youtube.com";
        String longUrl3 = "https://leetcode.com/problemset";

        String short1 = test1.shortenURL(longUrl1);
        String short2 = test1.shortenURL(longUrl2);
        String short3 = test1.shortenURL(longUrl3);

        test1.retrieveURL(short1);
        test1.retrieveURL(short3);
        test1.retrieveURL(short3);

        assertEquals(3, test1.basicAdmin().get(0).callCount);
        assertEquals(2, test1.basicAdmin().get(1).callCount);
        assertEquals(1, test1.basicAdmin().get(2).callCount);
    }
}