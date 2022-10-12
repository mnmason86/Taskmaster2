package com.mnmason86.taskmaster.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.mnmason86.taskmaster.R;

public class ImageActivity extends AppCompatActivity {

    public static final String TAG = "ImageActivity";
    ActivityResultLauncher<Intent> activityResultLauncher;
    private String s3ImageKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        activityResultLauncher = getImagePickingActivityResultLauncher();

        createAddImageButton();
        createSaveButton();
    }

    private void createSaveButton(){
        findViewById(R.id.imageActivitySaveButton).setOnClickListener(v -> {
            saveTeamImage(s3ImageKey);
        });
    }

    private void saveTeamImage(String s3key){
        Team teamToSave = Team.builder()
                .name("New Team")
                .productImageS3Key(s3key)
                .build();

        Amplify.API.mutate(
                ModelMutation.create(teamToSave),
                success -> Log.i(TAG, "Successfully created new team with s3ImageKey: " + s3key),
                failure -> Log.i(TAG, "Failed to create a new team with message: " + failure.getMessage())
        );
    }

    private void createAddImageButton(){
        findViewById(R.id.imageActivityAddButton).setOnClickListener(button -> {
            launchImageSelectionIntent();
        });
    }

    private void launchImageSelectionIntent(){
        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageFilePickingIntent.setType("*/*");
        imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"});
        activityResultLauncher.launch(imageFilePickingIntent);
    }
}