package com.example.pushpal.cubical_bot;

import android.bluetooth.BluetoothAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;

import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

public class MainActivity extends AppCompatActivity implements RecognitionListener {

    TextView botStatus, voiceStatus;
    public final String TAG = "Main";
    private Bluetooth bt;
    android.support.v7.widget.AppCompatImageButton cubicle1, cubicle2, cubicle3, cubicle4, cubicle5, cubicle6, cubicle7, cubicle8, cubicle9, cubicle10, cubicle11, cubicle12, cubicle13, cubicle14, admin, hr;
    android.support.v7.widget.AppCompatButton rest, exit;

    /* Named searches allow to quickly reconfigure the decoder */
    private static final String KWS_SEARCH = "wakeup";
    private static final String FORECAST_SEARCH = "forecast";
    private static final String DIGITS_SEARCH = "digits";
    private static final String PHONE_SEARCH = "phones";
    /* Keyword we are looking for to activate menu */
    private static final String KEYPHRASE = "hello cubicle";
    private SpeechRecognizer recognizer;
    private HashMap<String, Integer> captions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cubicles);

        cubicle1 = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.cubicle1);
        cubicle2 = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.cubicle2);
        cubicle3 = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.cubicle3);
        cubicle4 = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.cubicle4);
        cubicle5 = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.cubicle5);
        cubicle6 = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.cubicle6);
        cubicle7 = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.cubicle7);
        cubicle8 = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.cubicle8);
        cubicle9 = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.cubicle9);
        cubicle10 = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.cubicle10);
        cubicle11 = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.cubicle11);
        cubicle12 = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.cubicle12);
        cubicle13 = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.cubicle13);
        cubicle14 = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.cubicle14);
        admin = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.admin);
        hr = (android.support.v7.widget.AppCompatImageButton) findViewById(R.id.hr);
        rest = (android.support.v7.widget.AppCompatButton) findViewById(R.id.rest);
        exit = (android.support.v7.widget.AppCompatButton) findViewById(R.id.exit);
        botStatus = (TextView) findViewById(R.id.botStatus);
        voiceStatus = (TextView) findViewById(R.id.voiceStatus);

        bt = new Bluetooth(this, mHandler);
        connectService();

        captions = new HashMap<String, Integer>();
        captions.put(KWS_SEARCH, R.string.kws_caption);
        captions.put(DIGITS_SEARCH, R.string.digits_caption);
        voiceStatus.setText("Preparing the recognizer");

        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(MainActivity.this);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    ((TextView) findViewById(R.id.caption_text)).setText("Failed to init recognizer " + result);
                } else {
                    switchSearch(KWS_SEARCH);
                }
            }
        }.execute();

        cubicle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                cubicle1.setImageResource(R.drawable.cubicle_green);
                send("-01", "+04");
                botStatus.setText("Going to Mainframe...");
            }
        });

        cubicle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                cubicle2.setImageResource(R.drawable.cubicle_green);
                send("-01", "+03");
                botStatus.setText("Going to JAVA...");
            }
        });

        cubicle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                cubicle3.setImageResource(R.drawable.cubicle_green);
                send("-01", "+02");
                botStatus.setText("Going to BIZSKILL...");
            }
        });

        cubicle4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                cubicle4.setImageResource(R.drawable.cubicle_green);
                send("-01", "+01");
                botStatus.setText("Going to Helpdesk...");
            }
        });

        cubicle5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                cubicle5.setImageResource(R.drawable.cubicle_green);
                send("+00", "+05");
                botStatus.setText("Going to Innovation...");
            }
        });

        cubicle6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                cubicle6.setImageResource(R.drawable.cubicle_green);
                send("+01", "+04");
                botStatus.setText("Going to BIPM...");
            }
        });

        cubicle7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                cubicle7.setImageResource(R.drawable.cubicle_green);
                send("+01", "+03");
                botStatus.setText("Going to...");
            }
        });

        cubicle8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                cubicle8.setImageResource(R.drawable.cubicle_green);
                send("+01", "+02");
                botStatus.setText("Going to BIZSKILL...");
            }
        });

        cubicle9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                cubicle9.setImageResource(R.drawable.cubicle_green);
                send("+01", "+01");
                botStatus.setText("Going to Helpdesk...");
            }
        });

        cubicle10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                cubicle10.setImageResource(R.drawable.cubicle_green);
                send("+01", "-05");
                botStatus.setText("Going to BIPM...");
            }
        });

        cubicle11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                cubicle11.setImageResource(R.drawable.cubicle_green);
                send("+01", "-04");
                botStatus.setText("Going to...");
            }
        });

        cubicle12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                cubicle12.setImageResource(R.drawable.cubicle_green);
                send("+01", "-03");
                botStatus.setText("Going to BIZSKILL...");
            }
        });

        cubicle13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                cubicle13.setImageResource(R.drawable.cubicle_green);
                send("+01", "-02");
                botStatus.setText("Going to Helpdesk...");
            }
        });

        cubicle14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                cubicle14.setImageResource(R.drawable.cubicle_green);
                send("+00", "-06");
                botStatus.setText("Going to BIPM...");
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                admin.setImageResource(R.drawable.cubicle_green);
                send("-01", "-01");
                botStatus.setText("Going to Admin...");
            }
        });

        hr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                hr.setImageResource(R.drawable.cubicle_green);
                send("-02", "-01");
                botStatus.setText("Going to HR...");
            }
        });

        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                send("+00", "+00");
                botStatus.setText("Going to Rest Area...");
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiseColors();
                send("-03", "-01");
                botStatus.setText("Going to Exit...");
            }
        });
    }

    public void send(String xCoordinate, String yCoordinate) {
        bt.sendMessage(xCoordinate + yCoordinate + " ");
    }

    public void initialiseColors() {
        cubicle1.setImageResource(R.drawable.cubicle_blue);
        cubicle2.setImageResource(R.drawable.cubicle_blue);
        cubicle3.setImageResource(R.drawable.cubicle_blue);
        cubicle4.setImageResource(R.drawable.cubicle_blue);
        cubicle5.setImageResource(R.drawable.cubicle_blue);
        cubicle6.setImageResource(R.drawable.cubicle_blue);
        cubicle7.setImageResource(R.drawable.cubicle_blue);
        cubicle8.setImageResource(R.drawable.cubicle_blue);
        cubicle9.setImageResource(R.drawable.cubicle_blue);
        cubicle10.setImageResource(R.drawable.cubicle_blue);
        cubicle11.setImageResource(R.drawable.cubicle_blue);
        cubicle12.setImageResource(R.drawable.cubicle_blue);
        cubicle13.setImageResource(R.drawable.cubicle_blue);
        cubicle14.setImageResource(R.drawable.cubicle_blue);
        admin.setImageResource(R.drawable.cubicle_blue);
        hr.setImageResource(R.drawable.cubicle_blue);
    }

    public void connectService() {
        try {
            //status.setText("Connecting...");
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter.isEnabled()) {
                bt.start();
                bt.connectDevice("HC-05");
                Log.d(TAG, "Btservice started - listening");
                //status.setText("Connected to " + bt.getDeviceName());
            } else {
                Log.w(TAG, "Btservice started - bluetooth is not enabled");
                //status.setText("Bluetooth Not enabled");
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to start bt ", e);
            //status.setText("Unable to connect " + e);
        }
    }


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Bluetooth.MESSAGE_STATE_CHANGE:
                    Log.d(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    break;
                case Bluetooth.MESSAGE_WRITE:
                    Log.d(TAG, "MESSAGE_WRITE ");
                    break;
                case Bluetooth.MESSAGE_READ:
                    Log.d(TAG, "MESSAGE_READ ");
                    break;
                case Bluetooth.MESSAGE_DEVICE_NAME:
                    Log.d(TAG, "MESSAGE_DEVICE_NAME " + msg);
                    break;
                case Bluetooth.MESSAGE_TOAST:
                    Log.d(TAG, "MESSAGE_TOAST " + msg);
                    break;
            }
        }
    };

    private void switchSearch(String searchName) {
        recognizer.stop();

        if (searchName.equals(KWS_SEARCH))
            recognizer.startListening(searchName);
        else
            recognizer.startListening(searchName, 10000);

        String caption = getResources().getString(captions.get(searchName));
        voiceStatus.setText(caption);
    }

    private void setupRecognizer(File assetsDir) throws IOException {

        recognizer = defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
                .setRawLogDir(assetsDir)
                .setKeywordThreshold(1e-45f)
                .setBoolean("-allphone_ci", true)
                .getRecognizer();

        recognizer.addListener(this);
        recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);

        File digitsGrammar = new File(assetsDir, "digits.gram");
        recognizer.addGrammarSearch(DIGITS_SEARCH, digitsGrammar);
        File languageModel = new File(assetsDir, "weather.dmp");
        recognizer.addNgramSearch(FORECAST_SEARCH, languageModel);
        File phoneticModel = new File(assetsDir, "en-phone.dmp");
        recognizer.addAllphoneSearch(PHONE_SEARCH, phoneticModel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recognizer.cancel();
        recognizer.shutdown();
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onEndOfSpeech() {
        if (!recognizer.getSearchName().equals(KWS_SEARCH))
            switchSearch(KWS_SEARCH);
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;

        String text = hypothesis.getHypstr();

        if (text.equals(KEYPHRASE)) {
            switchSearch(DIGITS_SEARCH);
        } else
            voiceStatus.setText(text);
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        voiceStatus.setText("");
        if (hypothesis != null) {
            String text = hypothesis.getHypstr();

            if (text.equalsIgnoreCase("hello cubicle")) {
                voiceStatus.setText("Go to ....?");
            } else {
                voiceStatus.setText("To start say \"Hello Cubicle!\"");
                if (text.equalsIgnoreCase("GOTOADMIN")) {
                    admin.callOnClick();
                } else if (text.equalsIgnoreCase("GOTOBIPM")) {
                    cubicle6.callOnClick();
                } else if (text.equalsIgnoreCase("GOTOBIZSKILL")) {
                    cubicle8.callOnClick();
                } else if (text.equalsIgnoreCase("GOTOEXIT")) {
                    exit.callOnClick();
                } else if (text.equalsIgnoreCase("GOTOHELPDESK")) {
                    cubicle9.callOnClick();
                } else if (text.equalsIgnoreCase("GOTOHR")) {
                    hr.callOnClick();
                } else if (text.equalsIgnoreCase("GOTOINNOVATION")) {
                    cubicle5.callOnClick();
                } else if (text.equalsIgnoreCase("GOTOJAVA")) {
                    cubicle2.callOnClick();
                } else if (text.equalsIgnoreCase("GOTOMAINFRAME")) {
                    cubicle1.callOnClick();
                }
            }
        }
    }

    @Override
    public void onError(Exception e) {
        voiceStatus.setText(e.getMessage());
    }

    @Override
    public void onTimeout() {
        switchSearch(KWS_SEARCH);
    }
}