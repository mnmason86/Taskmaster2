package com.mnmason86.taskmaster;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
//import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

public class TaskAmplifyApplication extends Application {
    public static final String Tag = "TaskApplication";

    @Override
    public void onCreate(){
        super.onCreate();
        try{
            Amplify.addPlugin(new AWSApiPlugin());
//            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
        } catch(AmplifyException ae){
            Log.e(Tag, "Error initializing Amplify: " + ae.getMessage(), ae);
        }
    }
}
