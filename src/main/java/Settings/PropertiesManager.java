package Settings;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
    private static final String PROPERTIES_FILE = "app.properties";
    private Properties props = null;
    private static PropertiesManager instance;

    public PropertiesManager(){
        loadProperties(PROPERTIES_FILE);
    }

    public static PropertiesManager getInstance(){
        if(instance == null){
            instance = new PropertiesManager();
        }
        return instance;
    }

    private void loadProperties(String filename){
        props = new Properties();
        InputStream instream = null;
        ClassLoader loader = this.getClass().getClassLoader();
        instream = loader.getResourceAsStream(filename);
        if(instream == null){
            System.err.println("Unable to open property file "+ filename);
            return;
        }

        try{
            props.load(instream);
        } catch (IOException e) {
            System.err.println("Error reading property file "+ filename);
            System.err.println(e.getMessage());
        }

        try{
            instream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String name){
        return props.getProperty(name);
    }

}
