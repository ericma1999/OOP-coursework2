public class Pair<K, V> {
    private final K k;
    private final V v;

    public Pair(K k, V v){
        this.k = k;
        this.v = v;
    }

    public K getValue0(){
        return this.k;
    }

    public V getValue1(){
        return this.v;
    }

}
