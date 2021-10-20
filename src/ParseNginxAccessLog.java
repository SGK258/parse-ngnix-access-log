import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// Question : Count the number of requests for each IP address
//Input : Log file as accesslog.txt
//
//Output : example
//        Count of IP address , IP address
//
//        14 172.31.30.96
//        7 172.31.30.95
//        4 172.31.30.93
//        3 172.31.30.92
//        2 172.31.30.97


public class ParseNginxAccessLog {

    private static String regex = "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \\\"(\\S+) (\\S+)\\s*(\\S+)?\\s*\\\" (\\d{3}) (\\S+)";

    public static void main(String[] args) throws IOException {
        String log_content = readAccessLogFile("accesslog.txt");
        printIpAddressCount(log_content);
    }


    public static String readAccessLogFile(String filename) throws IOException {
        Path fileName = Path.of(filename);
        return Files.readString(fileName);
    }


    public static void printIpAddressCount(String content) {

        String[] lines = content.split("\\r?\\n");      //split each line by carriage return and store in  String array

        HashMap<String, Integer> hashMap = new HashMap<>();
        String ip_string;

        for (String str : lines) {
            if (!Objects.equals(str, "")) {             //condition to skip empty lines

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(str);

                if (matcher.find()) {
                    ip_string = matcher.group(1);           // group 1 = IP address
                    if (!hashMap.containsKey(ip_string)) {  //if no key
                        hashMap.put(ip_string, 1);          // add new key and count =1
                    } else {                                //else increment the count for existing value
                        hashMap.put(ip_string, hashMap.get(ip_string) + 1);
                    }

                }

            }
        }

        //to print key value of the hashMap
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + " ==> " + entry.getValue());
        }
    }
}

