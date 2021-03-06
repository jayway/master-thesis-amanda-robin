package com.example.amandaeliasson.ecodrivning;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapperConfig;

import java.sql.Time;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class FinishFragment extends Fragment {
    public static final String ARG_DRIVE_ID ="ARG_DRIVE_ID";
    public static  final  String ARG_USER_ID = "ARG_USER_ID";
    private DynamoDBMapper dynamoDBMapper;
    private String userId, driveId;
    private State state;


    public FinishFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        dynamoDBMapper = MainActivity.dynamoDBMapper;
        Bundle args = getArguments();
        userId = args.getString(ARG_USER_ID);
        driveId = args.getString(ARG_DRIVE_ID);
        state = (State) args.getSerializable(MainActivity.ARGS_STATE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v =inflater.inflate(R.layout.fragment_finish, container, false);
        RelativeLayout mContainerView = v.findViewById(R.id.finishBackground);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.carmoving22);
        Bitmap blurredBitmap = BlurBuilder.blur(getContext(), originalBitmap);
        mContainerView.setBackground(new BitmapDrawable(getResources(), blurredBitmap));


        CardView card = v.findViewById(R.id.cv);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).openMenuItem(R.id.score);
            }
        });
//        gotoScoreView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((MainActivity)getActivity()).openMenuItem(R.id.score);
//            }
//        });
        TextView textViewEcoscore = v.findViewById(R.id.tv_finish_ecoscore);
        textViewEcoscore.setText("0.89");
//        final OverviewDO overview = null;
//        Timer t = new Timer();
//        while(overview == null){
//           t.schedule(new TimerTask() {
//               @Override
//               public void run() {
//                   new AsyncTask<Void, Void, OverviewDO>(){
//
//                       @Override
//                       protected OverviewDO doInBackground(Void... voids) {
//                           overview = dynamoDBMapper.load(OverviewDO.class, userId, driveId);
//                           return overview;
//                       }
//
//                       @Override
//                       protected void onPostExecute(OverviewDO overviewDO) {
//                           super.onPostExecute(overviewDO);
//                           textViewEcoscore.setText(String.format("%2.0f", overviewDO.getEcoScore()));
//                       }
//                   }.execute();
//               }
//           },0,100);
//        }


        return v;
    }

}
