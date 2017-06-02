package Application.Utility;

import java.util.prefs.Preferences;

/**
 * Created by Sohel on 5/29/2017.
 */
public class MyPreference {
    private String prefName="myPref";
    private Preferences preferences;
    private static MyPreference myPreference;
    private static final String IMAGE_FOLDER="IMAGE_FOLDER";
    private static final String BASIC_ACCOUNT ="BASIC_ACCOUNT";

    private MyPreference(){
        preferences = Preferences.userRoot()
                .node(prefName);
    }

    public static MyPreference getInstance(){
        if(myPreference==null){
            myPreference = new MyPreference();
        }

        return myPreference;
    }

    public void setImageFolder(String path){
        preferences.put(IMAGE_FOLDER,path);
    }

    public String getImageFolder(){
        return preferences.get(IMAGE_FOLDER,"");
    }

    public void setBasicAccount(boolean value){
        preferences.putBoolean(BASIC_ACCOUNT,value);
    }

    public boolean getBasicAccount(){
        return preferences.getBoolean(BASIC_ACCOUNT,false);
    }
}
