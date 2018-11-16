import java.util.Random;
/**
 * Implememts RandomInterface. Returns value of nextInt from java.util.Random.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RealRandom implements RandomInterface
{
   Random rand = new Random();
   
   public int nextInt(int max) {
       return rand.nextInt(max);
    }
}