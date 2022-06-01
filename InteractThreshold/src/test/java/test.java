import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
    }
}
