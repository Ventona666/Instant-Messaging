package utils;

import java.util.Date;
import java.util.Random;

public abstract class IdGenerator {

    private static long firstPassGenerator(){
        Date date = new Date();
        long dateInMilliseconds = date.getTime();

        Random random = new Random();
        int nb = random.nextInt(10000);

        return dateInMilliseconds + nb;
    }

    public static long idGenerator(String ...elements){
        long id = firstPassGenerator();
        for(String element : elements){
            id += element.hashCode();
        }
        return id;
    }
}
