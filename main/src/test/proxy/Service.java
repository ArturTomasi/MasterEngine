package proxy;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service
    implements
        Manager
{
    private static Manager instane;

    public static final Manager getInstance() 
    {
        if ( instane == null ) 
        {
            instane = (Manager) Proxy.newProxyInstance(
                                        Service.class.getClassLoader(),
                                        new Class[]{ Manager.class },
                                        new CacheControl<Service>( new Service() ) );
        }

        return instane;
    }

    private Map<Integer, Identity> map = new HashMap();

    private Service() {
    }

    @Override
    public void put( Identity v) 
    {
        map.put( v.getId(), v);
    }

    @Override
    public void update( Identity v) 
    {
        map.put( v.getId(), v);
    }

    @Override
    public Identity get( Integer k )
    {
        System.out.println( k );
        return map.get(k);
    }

    @Override
    public List<Identity> get() 
    {
        return new ArrayList( map.values() );
    }
}
