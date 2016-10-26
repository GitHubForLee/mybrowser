package com.lee.mybrowser;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etUri;
    private Button btnSearch, btnBack, btnNext;
    private WebView webView;
    private ProgressBar bar;
    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        handleEvents();
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                        setState();
                        break;
                }
                return false;
            }
        });


    }

    private void handleEvents() {
        btnBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    private void initViews() {
        etUri = (EditText) findViewById(R.id.etUri);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        webView = (WebView) findViewById(R.id.webView);
        bar = (ProgressBar) findViewById(R.id.bar);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.e("progress", String.valueOf(newProgress));
                bar.setProgress(newProgress);
                if (newProgress == 100) {
                    bar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:

                if (webView.canGoBack()) {
                    webView.goBack();
                }
                setState();
                break;
            case R.id.btnNext:
                if (webView.canGoForward())
                    webView.goForward();
                setState();


                break;
            case R.id.btnSearch:
                bar.setVisibility(View.VISIBLE);
                String sUri = etUri.getText().toString();
                if (URLUtil.isNetworkUrl(sUri)) {
                    webView.loadUrl(sUri);
//                    webView.setWebViewClient(new WebViewClient() {
//                        @Override
//                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                            view.loadUrl(url);
//                            return super.shouldOverrideUrlLoading(view, url);
//                        }
//                    });
                } else {
                    Toast.makeText(MainActivity.this, "对不起，你输入的网址有误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.e("??","touch");
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_HOVER_ENTER:
//                Log.e("??","1");
////                break;
//            case MotionEvent.ACTION_BUTTON_PRESS:
//                Log.e("??","2");
//            case MotionEvent.ACTION_DOWN:
//                Log.e("??", "3");
//            case MotionEvent.ACTION_MASK:
//                Log.e("??", "4");
//            case MotionEvent.ACTION_OUTSIDE:
//                Log.e("??","5");
//            case MotionEvent.ACTION_POINTER_DOWN:
//                Log.e("??","6");
//            case MotionEvent.ACTION_POINTER_INDEX_SHIFT:
//                Log.e("??","7");
//            case MotionEvent.ACTION_POINTER_UP:
//                Log.e("??", "8");
//
//            case MotionEvent.ACTION_UP:
//                Log.e("??","10");
//                setState();
//                break;
//
//
//
//        }
//        return false;
//
//    }

    public void setState() {
      handler.postDelayed(new Runnable() {
          @Override
          public void run() {
              btnBack.setEnabled(webView.canGoBack() ? true : false);
              btnNext.setEnabled(webView.canGoForward() ? true : false);
          }
      },200);

    }
}
