import com.ntech.forward.HttpUploadFile;
import com.ntech.util.PictureShow;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class javaTest {
    public static void main(String[] args) throws IOException {
        Map<String,String> header = new HashMap<String,String>();
        header.put("Method","GET");
        header.put("API","/faces");
        System.out.println("reply: "+HttpUploadFile.getInstance().httpURLConnectionSDK(header,null,null,"no"));
    }
}
