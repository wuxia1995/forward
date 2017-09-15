import com.ntech.util.PictureShow;

import java.text.SimpleDateFormat;
import java.util.Date;

public class javaTest {
    public static void main(String[] args) {
        System.out.println(PictureShow.getInstance().getBase64Picture("http://192.168.10.208:3333/uploads//20170905/15045745561408343.jpeg"));
    }
}
