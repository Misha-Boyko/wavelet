import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> searchPool = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) 
            return "Add a query to the end of the URL!";
        if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    searchPool.add(parameters[1]);
                    return String.format("Added String %s to search contents pool", parameters[1]);
                }
        } if(url.getPath().contains("/search")){
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                ArrayList<String> result = new ArrayList<>(); 
                for (String string : searchPool) {
                    System.out.println("paramters[1] = " + parameters[1]);
                    System.out.println("string in for loop = " + string);
                    if (parameters[1].equals(string)) {
                        result.add(string);
                    }
                }
                return "The following search results match the substring" + result.toString();
            }
        } 
            return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
