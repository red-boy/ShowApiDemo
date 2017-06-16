package com.example.hw.showapidemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.show.api.ShowApiRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 易源数据接口调用练习
 * id：40468
 * 此app的密钥为：8434a08ac8be4817b0e88e250adab546
 */
public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private ImageView mImageView, mImageView2;
    protected Handler mHandler = new Handler();

    private TextView mTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text1);
        mImageView = (ImageView) findViewById(R.id.imageview);
        mImageView2 = (ImageView) findViewById(R.id.imageview2);
        mTextView2 = (TextView) findViewById(R.id.textQQ);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String appid = "40468";
                        String secret = "8434a08ac8be4817b0e88e250adab546";

                        final String res = new ShowApiRequest("http://route.showapi.com/119-42", appid, secret)
                                .addTextPara("data", "0616")
                                .post();


                        try {
                            /**接口测试：对返回数据进行解析*/
                            JSONObject jsonObject = new JSONObject(res);
                            JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body");
                            JSONArray list = showapi_res_body.getJSONArray("list");
                            JSONObject jsonObject1 = list.getJSONObject(29);
                            final String title = jsonObject1.optString("title");
                            final String img = jsonObject1.optString("img");

                            mHandler.post(new Thread() {
                                public void run() {
                                    mTextView.setText(title);
                                    Glide.with(MainActivity.this).load(img).thumbnail(0.1f).placeholder(R.drawable.placeholder_loading).into(mImageView);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });


        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String appid = "40483";
                        String secret = "b676909f7c3a49bda86797f11acb9e4e";

                        final String res = new ShowApiRequest("http://route.showapi.com/213-4", appid, secret)
                                .addTextPara("topid", "5")
                                .post();


                        try {
                            JSONObject jsonObject = new JSONObject(res);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("showapi_res_body").getJSONObject("pagebean");
                            JSONArray list = jsonObject1.getJSONArray("songlist");
                            JSONObject jsonObject2 = list.getJSONObject(0);
                            final String imgURL = jsonObject2.optString("albumpic_small");
                            final String songName = jsonObject2.optString("songname");

                            mHandler.post(new Thread() {
                                @Override
                                public void run() {
                                    mTextView2.setText(songName);
                                    Glide.with(MainActivity.this).load(imgURL).placeholder(R.drawable.placeholder_loading).into(mImageView2);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }).start();

            }
        });
    }
}
