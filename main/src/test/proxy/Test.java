package proxy;

public class Test {
    
    public static void main(String[] args) {
        
        try
        {
            Manager service = Service.getInstance();

            service.get( 1 );
            service.put(new Identity(1,"teste 1") );
            service.put(new Identity(2,"teste 2") );
            service.put(new Identity(3,"teste 3") );
            service.get( 1 );
            service.get( 1 );
            service.get( 2 );
            
            service.get();
        }
        
        catch ( Exception e )
        {
            e.printStackTrace( System.err );
        }
    }
}
