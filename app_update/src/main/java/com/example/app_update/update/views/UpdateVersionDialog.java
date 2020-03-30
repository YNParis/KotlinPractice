package com.example.app_update.update.views;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.app_update.R;
import com.example.app_update.update.AppUpdater;
import com.example.app_update.update.bean.VersionDataBean;
import com.example.app_update.update.net.IDownloadCallback;
import com.example.app_update.utils.AppUtils;

import java.io.File;

public class UpdateVersionDialog extends DialogFragment {
    private static String KEY = "dialog_key";
    private static String DIALOG_TAG = "update_version_dialog";
    private VersionDataBean dataBean;

    public static void show(FragmentActivity activity, VersionDataBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, bean);
        UpdateVersionDialog dialog = new UpdateVersionDialog();
        dialog.setArguments(bundle);
        dialog.show(activity.getSupportFragmentManager(), DIALOG_TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            dataBean = (VersionDataBean) arguments.getSerializable(KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_update_dialog, container, false);
        bindView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void bindView(View view) {
        if (dataBean == null) return;
        final File targetFile = new File(getActivity().getCacheDir(), "update.apk");
        if (!targetFile.exists()) {
            targetFile.getParentFile().mkdirs();
        }
        view.findViewById(R.id.dialog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setEnabled(false);
                AppUpdater.getInstance().getNetManager().download(dataBean.getUrl(), targetFile, new IDownloadCallback() {
                    @Override
                    public void onSuccess(File apkFile) {
                        v.setEnabled(true);
                        Log.e(DIALOG_TAG, apkFile.getAbsolutePath());
                        //MD5校验
                        String fileMd5 = AppUtils.getFileMd5(apkFile);
                        if (fileMd5 != null && fileMd5.equals(dataBean.getMd5())) {
                            //安装
                            AppUtils.install(getActivity(), apkFile);
                        } else {
                            Toast.makeText(getActivity(), "md5校验失败", Toast.LENGTH_SHORT).show();
                        }
                        dismiss();
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        v.setEnabled(true);
                        Toast.makeText(getActivity(), "下载失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProcess(int process) {
                        ((TextView) v).setText(process + "%");
                        Log.e(DIALOG_TAG, process + "%");
                    }
                }, UpdateVersionDialog.this);
            }
        });
        ((TextView) view.findViewById(R.id.dialog_content)).setText(dataBean.getContent());
        ((TextView) view.findViewById(R.id.dialog_title)).setText(dataBean.getTitle());
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        //TODO 取消下载
        AppUpdater.getInstance().getNetManager().cancel(this);
    }
}
