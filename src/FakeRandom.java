
/**
 * Implements Random Interface. Returns an arbitrary value.
 * 
 * @author Brianne Kerr 
 * @version 11/14/2018
 */
public class FakeRandom implements RandomInterface
{
    private int value; 
    
    public FakeRandom(int value) {
        this.value = value;
    }
    
    public void setValue(int value) {
        this.value = value;
     }
    
    public int nextInt(int max) {
        return value;
    }
}
