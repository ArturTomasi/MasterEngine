package proxy;

@CacheObject()
public class Identity {
    String name;
    Integer i;
    
    public Identity( Integer i, String name) {
        this.name = name;
        this.i = i;
    }
    
    @Override
    public String toString() {
        return name;
    }

    @CacheKey
    public Integer getId() {
        return i;
    }
}
