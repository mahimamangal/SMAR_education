package com.example.jayatulsiani.smar;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import com.example.jayatulsiani.concepts.UnityPlayerActivity;
import android.content.Intent;
import android.widget.Toast;

import com.Biology.Brain.UnityPlayerActivity;

public class MultipleSceneActivity extends UnityPlayerActivity {

    public static boolean unityIsRunning = false;
    private int screenOrientation;

    @Override
    protected void onResume()
    {
        if (!unityIsRunning) {
//            screenOrientation=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
//            setRequestedOrientation(screenOrientation);
            Intent intent=getIntent();
            String sub=intent.getStringExtra("sub");

            switch (sub) {
                case "0" :
                    super.mUnityPlayer.UnitySendMessage("SceneManager", "ReceiveJavaMessage","0" );
                    Toast.makeText(this,"brain",Toast.LENGTH_SHORT).show();
                    break;

                case "3" :
                    super.mUnityPlayer.UnitySendMessage("SceneManager", "ReceiveJavaMessage","3");
                    Toast.makeText(this,"ear",Toast.LENGTH_SHORT).show();
                    break;

                case "7" :
                    super.mUnityPlayer.UnitySendMessage("SceneManager", "ReceiveJavaMessage","7");
                    Toast.makeText(this,"earth",Toast.LENGTH_SHORT).show();
                    break;

                case "13" :
                    super.mUnityPlayer.UnitySendMessage("SceneManager", "ReceiveJavaMessage","13");
                    Toast.makeText(this,"nacl",Toast.LENGTH_SHORT).show();
                    break;

                case "14" :
                    super.mUnityPlayer.UnitySendMessage("SceneManager", "ReceiveJavaMessage", "14");
                    Toast.makeText(this,"so",Toast.LENGTH_SHORT).show();
                    break;

                default :
                    super.mUnityPlayer.UnitySendMessage("SceneManager", "PuzzleJavaMessage", sub);
                    Toast.makeText(this,"puzzle",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
        unityIsRunning = true;
        super.onResume();
        super.mUnityPlayer.resume();
    }

}
