import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LFUCacheTest {
    public static void main(String[] args){
        System.out.println("LFU算法:");
        LFUCache lfu = new LFUCache();
        lfu.putKeyValue(5,5);
        lfu.putKeyValue(3,3);
        lfu.putKeyValue(8,8);
        lfu.getValue(5);
        lfu.putKeyValue(7,7);
        lfu.getValue(7);
        lfu.putKeyValue(6,6);
        lfu.forEach((k,v)->System.out.println(k+":"+v));
    }
}
