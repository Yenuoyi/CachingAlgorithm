import java.util.*;

/**
 * 缓存算法
 * @param <K>
 * @param <V>
 */
public class LFUCache<K, V> extends LinkedHashMap<K, V> {
    private final static int DEFAULTSIZE = 3;
    private int maxSize;
    Map<K,HitRate> map = new HashMap<K, HitRate>();
    public LFUCache(){
        this(DEFAULTSIZE);
    }
    public LFUCache(int maxSize){
        this.maxSize = maxSize;
    }

    /**
     * 内部类实现Comparable，用作存储缓存消息的使用次数以及创建时间
     */
    class HitRate implements Comparable<HitRate>{
        K key;
        int hitCount;
        Long craeteTime;
        public HitRate(K key, int hitCount){
            this.key = key;
            this.hitCount = hitCount;
            this.craeteTime = System.currentTimeMillis();
        }
        /**
         * 重写comparaTo比较方法
         * @param o
         * @return
         */
        @Override
        public int compareTo(HitRate o) {
            if(hitCount==o.hitCount){
                return craeteTime.compareTo(o.craeteTime);
            }
            return hitCount - o.hitCount;
        }
    }

    /**
     * 通过排序算法获取需要淘汰的缓存并返回Key值
     * @param key
     * @return
     */
    public K getMin(K key){
        HitRate hitRate = Collections.min(map.values());
        return hitRate.key;
    }

    /**
     * 获取缓存数据
     * @param key
     * @return
     */
    public Object getValue(K key){
        HitRate hitRate = map.get(key);
        if(hitRate!=null){
            hitRate.hitCount = hitRate.hitCount+1;
        }
        return this.get(key);
    }

    /**
     * 添加新的缓存
     * @param newKey
     * @param value
     */
    public void putKeyValue(K newKey, V value){
        HitRate hitRate = null;
        if(this.size()>=maxSize){
            K oldKey = getMin(newKey);
            this.remove(oldKey);
            //对象重复利用，避免内存溢出
            hitRate = map.get(oldKey);
            hitRate.craeteTime = System.currentTimeMillis();
            hitRate.hitCount = 0;
            hitRate.key = newKey;
            map.remove(oldKey);
        }else{
            hitRate = new HitRate(newKey,0);
        }
        map.put(newKey,hitRate);
        this.put(newKey,value);
    }
}
