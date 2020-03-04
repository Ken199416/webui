package auto.test.zdebug;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class test {

    @Test
    public void t1(){

//        System.out.println(fun(7));
//        System.out.println(arrayquchong(new int [] {1,5,8,6,3,1,7,5,6,1,2,5} ,1));
        int [] a = {1,5,8,6,3,1,7,5,6,1,2,5};
        List<Object> list = new ArrayList<Object>(Arrays.asList(a));
        for ( Object i: list
             ) {
            System.out.println(i.toString());
        }


    }

    public static int go(int index){
        int currentNum = 1;
        int one = 1;
        int two = 0;
        if (index == 1){
            return 0;
        }
        else if (index == 2){
            return 1;
        }
        else {
        for (int i = 0; i < index -2 ; i++) {
                currentNum = one + two;
                two = one;
                one = currentNum;
            }
        }
        return currentNum;
    }

    public static int fun(int index){
        int currentNum;
        if (index == 1) {
            return 0;
        }
        if (index == 2){
            return 1;
        }
        else {
            currentNum =  fun(index-1)+fun(index-2);
            return currentNum;
        }
    }


    public static int arrayquchong(int [] arrays , int i){
        int len = 0;
        List<Object> list = Arrays.asList(arrays);
        for (int j = 0; j < arrays.length; j++) {
            if (list.remove(i+"") ){
                len++;
            }
        }
        return len;
    }
}
