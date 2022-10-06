import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> s = new ArrayList<String>();
    
    public String handleRequest(URI url) {
        
        System.out.println("Path: " + url.getPath());
        if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                s.add(parameters[1]);
                return String.format("String added! The String List is now: " + s);
            }
        }
        else if (url.getPath().contains("/search")) {
            ArrayList<String> subarr=new ArrayList<String>();
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                String containString = parameters[1];
                for(int x = 0; x < s.size(); x++){
                    if(s.get(x).contains(containString)){
                        subarr.add(s.get(x));
                    }
                }
                return String.format("These words in the list contain " + containString + ": " + subarr);
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
