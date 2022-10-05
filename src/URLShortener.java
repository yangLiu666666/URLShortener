import java.util.*;

/**
 * This class is URL shortener.
 */
public class URLShortener {
    private final HashMap<String, String> valueMap;
    private final HashMap<String, LinkedListNode> map;
    private final String domain;
    private Random rand;
    private final int keyLength;
    private LinkedListNode head;
    private LinkedListNode tail;
    private final String alphabet;
    private int capacity;

    /**
     * Construct a URLShortener object. It shows the capacity of this URL shortener.
     */
    public URLShortener() {
        capacity = 62 * 62;
        map = new HashMap<>();
        valueMap = new HashMap<>();
        alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        domain = "applau.se/";
        rand = new Random();
        keyLength = 2;
    }

    /**
     * Helper method to generate a key with random
     * @return the key generated with random
     */
    private String generateKey() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyLength; i++) {
            sb.append(alphabet.charAt(rand.nextInt(62)));
        }
        return domain + sb.toString();
    }

    /**
     * According to the input original URL, look for its corresponding short URL, if the short URL exists, return directly.
     * If there is space, the short URL will be generated using generateKey() helper method, and wrap the short URL and other corresponding values into the new node.
     * If there is no space, the least commonly used URL (the URL node in the tail) will be removed first,
     * and then will add a new URL node in the head.
     * @param longUrl the long URL
     * @return the short URL
     */
    private String getKey(String longUrl) {
        LinkedListNode node;
        String key;
        if (valueMap.containsKey(longUrl)) {
            key = valueMap.get(longUrl);
            return key;
        }

        if (map.size() < capacity) {
            key = generateKey();
            /*
            First, it needs to determine whether the randomly generated key has been used.
            If it has been used, it will be randomly generated until it finds the unused key.
             */
            while (map.containsKey(key)) {
                key = generateKey();
            }
            node = new LinkedListNode();
            node.key = key;
        } else {
            node = this.tail;
            key = node.key;
            removeNode(node);
            valueMap.remove(longUrl);
        }
        node.value = longUrl;
        node.callCount = 1;
        Calendar calendar = Calendar.getInstance();
        node.callTime.format(calendar.getTime());
        setHead(node);

        map.put(key, node);
        valueMap.put(longUrl, key);
        return key;
    }

    /**
     * Remove the node
     * @param node will be removed
     */
    private void removeNode(LinkedListNode node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        node.prev = null;
        node.next = null;
    }

    /**
     * Add the node to the head
     * @param node will be added
     */
    private void setHead(LinkedListNode node) {
        node.next = head;
        node.prev = null;
        if (head != null) head.prev = node;
        head = node;

        if (tail == null) tail = head;
    }

    /**
     * Returns the shortened URL
     * @param longUrl A long URL
     * @return the shortened URL
     */
    public String shortenURL(String longUrl) {
        String shortURL;
        longUrl = formatURL(longUrl);
        if (valueMap.containsKey(longUrl)) {
            shortURL = valueMap.get(longUrl);
        } else {
            shortURL = getKey(longUrl);
        }
        return shortURL;
    }

    /**
     * Returns a URL in the same format
     * @param url the original URL
     * @return a URL in the same format
     */
    public String formatURL(String url) {
        if (url.substring(0, 7).equals("http://")) {
            url = url.substring(7);
        }
        if (url.substring(0, 8).equals("https://")) {
            url = url.substring(8);
        }
        if (url.charAt(url.length() - 1) == '/') {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    /**
     * Return a original URL
     * @param shortURL the shortened URL
     * @return a original URL
     */
    public String retrieveURL(String shortURL) {
        if (!map.containsKey(shortURL)) {
            throw new NoSuchElementException("This short URL does not exist. Please input exist URL.");
        }
        LinkedListNode node = map.get(shortURL);
        removeNode(node);
        setHead(node);
        node.callCount++;
        return node.value;
    }

    /**
     * Returns all stored shortened URL nodes and sort by call count
     * @return all stored shortened URL nodes
     */
    public List<LinkedListNode> basicAdmin() {
        //Take nodes to list, sort it, and then print
        List<LinkedListNode> list = new ArrayList<>();
        for (String key : map.keySet()) {
            list.add(map.get(key));
        }

        Collections.sort(list, new Comparator<LinkedListNode>() {
            @Override
            public int compare(LinkedListNode o1, LinkedListNode o2) {
                return o2.callCount - o1.callCount;
            }
        });

        List<LinkedListNode> nodeList = new ArrayList<>();

        for (LinkedListNode node : list) {
            LinkedListNode newNode = new LinkedListNode(node.key, node.value, node.callCount, node.callTime);
            nodeList.add(newNode);
        }
        return nodeList;
    }

    public static void main(String[] args) {
        URLShortener test = new URLShortener();
        String[] urls = { "www.google.com", "http://www.google.com",
                "http://www.youtube.com", "www.youtube.com", "http://www.amazon.com",
                "www.applause.com", "https://www.applause.com/" };

        for (String url : urls) {
            System.out.println("URL:" + url + "\tShortenURL: "
                    + test.shortenURL(url) + "\tRetrieveURL: "
                    + test.retrieveURL(test.shortenURL(url)));
        }

        test.basicAdmin();
        System.out.println(test.basicAdmin());
    }
}
