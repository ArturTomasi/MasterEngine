package proxy;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

public class CacheControl<T>
    implements
        InvocationHandler 
{
    private final T target;

    private final Map map = new HashMap();

    public CacheControl( T target ) 
    {
        this.target = target;
    }

    @Override
    public Object invoke( Object proxy, Method method, Object[] args) throws Throwable 
    {
        Object result = null;
        
        if ( args != null )
        {
            for ( Object a : args )
            {
                if ( a.getClass().isAnnotationPresent( CacheObject.class ) )
                {
                    for ( Method m : a.getClass().getMethods() )
                    {
                        if ( m.isAnnotationPresent( CacheKey.class ) )
                        {
                            map.put( m.invoke( a ), a ); break;
                        }
                    }
                }
            }
        }
        
        if ( method.getReturnType().isAnnotationPresent( CacheObject.class ) && args != null )
        {
            result = map.get( args[0] );
            
            if ( result == null )
            {
                result = method.invoke( target, args );
                
                if ( result != null )
                {
                    for ( Method m : result.getClass().getMethods() )
                    {
                        if ( m.isAnnotationPresent( CacheKey.class ) )
                        {
                            map.put( m.invoke( result ), result );
                        }
                    }
                }
            }
        }
        
        else
        {
            result = method.invoke(target, args);
        }
            
        return result;
    }

}
