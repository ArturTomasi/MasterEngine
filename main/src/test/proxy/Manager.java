package proxy;

import java.util.List;

public interface Manager
{
    public void put( Identity identity );
    
    public void update( Identity identity );
    
    public Identity get( Integer id );
    
    public List<Identity> get();
}
