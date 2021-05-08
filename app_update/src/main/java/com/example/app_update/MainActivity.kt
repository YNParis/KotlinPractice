package com.example.app_update;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_update.update.AppUpdater;
import com.example.app_update.update.bean.VersionDataBean;
import com.example.app_update.update.net.INetCallback;
import com.example.app_update.update.views.UpdateVersionDialog;
import com.example.app_update.utils.AppUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUpdater.getInstance().getNetManager().get("http://59.110.162.30/app_updater_version.json", new INetCallback() {
                    @Override
                    public void onSuccess(String response) {
                        //TODO 查询成功，解析数据
                        //{
                        //    "title":"4.5.0更新啦！",
                        //    "content":"1. 优化了阅读体验；\n2. 上线了 hyman 的课程；\n3. 修复了一些已知问题。",
                        //    "url":"http://59.110.162.30/v450_imooc_updater.apk",
                        //    "md5":"14480fc08932105d55b9217c6d2fb90b",
                        //    "versionCode":"450"
                        //}
                        //比较版本号
                        //需要更新时，弹框
                        Log.e("net", response);
                        //解析
                        VersionDataBean dataBean = VersionDataBean.parse(response);
                        //比较
                        try {
                            if (AppUtils.getVersionCode(MainActivity.this) >= Long.parseLong(dataBean.getVersionCode())) {
                                Toast.makeText(MainActivity.this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //弹框
                            UpdateVersionDialog.show(MainActivity.this, dataBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "版本信息数据异常", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "查询版本信息失败", Toast.LENGTH_SHORT).show();
                    }
                }, MainActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUpdater.getInstance().getNetManager().cancel(this);
    }
}
