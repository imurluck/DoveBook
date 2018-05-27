package com.example.dovebook.widget.popupwindow;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dovebook.R;
import com.example.dovebook.base.BaseFragment;
import com.example.dovebook.images.ImageManager;
import com.example.dovebook.share.wrold.ShareWorldFragment;
import com.example.dovebook.utils.PictureUriUtil;
import com.example.dovebook.utils.WindowUtil;
import com.example.dovebook.widget.recycler.RecyclerAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddSharePopWindow extends PopupWindow {
    private static final String TAG = "AddSharePopWindow";
    public static final int CHOOSE_PHOTO = 0;
    public static final int CAMERA_PHOTO = 1;

    private View mRootView;

    @BindView(R.id.pop_window_add_share_recycler)
    RecyclerView imgRecycler;
    @BindView(R.id.pop_window_add_share_edit)
    EditText contentEt;
    @BindView(R.id.pop_window_add_share_publish)
    TextView publishTv;
    @BindView(R.id.pop_window_add_share_close)
    ImageView closeImg;

    private Activity mActivity;
    private BaseFragment mFragment;

    private Uri mCameraImgUri;
    private File mOutputImgFile;

    private int mWidth;
    private int mHeight;

    private RecyclerAdapter<String> mAdapter;

    private ArrayList<String> mImgList;

    public AddSharePopWindow(BaseFragment fragment) {
        mFragment = fragment;
        mActivity = fragment.getActivity();
        mRootView = mActivity.getLayoutInflater().inflate(R.layout.pop_window_add_share, null);
        setContentView(mRootView);
        ButterKnife.bind(this, mRootView);
        DisplayMetrics metrics = WindowUtil.getWindowDisplayMetrics();
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;
        setWidth(mWidth);
        setHeight(mHeight);
        setFocusable(true);
        setAnimationStyle(R.style.PopupWindowUpAndDownAnimation);
        initData();
        initWidget();
    }

    private void initWidget() {
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (contentEt.getText().toString().length() == 0) {
                    publishTv.setClickable(false);
                    publishTv.setTextColor(mActivity.getResources().getColor(R.color.divider_line));
                } else {
                    publishTv.setTextColor(mActivity.getResources().getColor(R.color.white));
                    publishTv.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        publishTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishMoment();
            }
        });
        publishTv.setClickable(false);
    }

    private void publishMoment() {
        //this.dismiss();
        Map<String, RequestBody> paraMap = new HashMap<>();
        String momentContent = contentEt.getText().toString();
        String momentDetail = "沈阳";
        String type = "[{\"key\":\"Content-Type\",\"value\":\"application/x-www-form-urlencoded\",\"description\":\"\"}]";
        double latitude = 123.34;
        double longitude = 456.321;
        RequestBody cmomentDetaillocBody = RequestBody.create(MediaType.parse(type), momentDetail);
        paraMap.put("cmomentDetailloc", cmomentDetaillocBody);
        RequestBody momentLatitudeBody = RequestBody.create(MediaType.parse(type), String.valueOf(latitude));
        paraMap.put("momentLatitude", momentLatitudeBody);
        RequestBody momentLongitudeBody = RequestBody.create(MediaType.parse(type), String.valueOf(longitude));
        paraMap.put("momentLongitude", momentLongitudeBody);
        RequestBody momentContentBody = RequestBody.create(MediaType.parse(type), momentContent);
        paraMap.put("momentContent", momentContentBody);
        List<MultipartBody.Part> momentPics = new ArrayList<>();
        if (mImgList.size() - 2 > 0) {
            for (int i = 0; i < mImgList.size() - 2; i++) {
                File file = new File(mImgList.get(i));
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                momentPics.add(MultipartBody.Part.createFormData("momentPic", file.getName(),
                                    requestBody));
            }
        }
        ((ShareWorldFragment) mFragment).addOneMomentToServer(paraMap, momentPics);
    }

    private void initData() {
        mImgList = new ArrayList<>();
        mImgList.add(String.valueOf(R.drawable.ic_add_a_camera_grey_70dp));
        mImgList.add(String.valueOf(R.drawable.ic_photo_size_select_actual_grey_70dp));

        mAdapter = new RecyclerAdapter<String>() {
            @Override
            protected int getItemViewType(int position, String s) {
                if (position >= getItemCount() - 2) {
                    return R.layout.add_share_item_photo_resource;
                } else {
                    return R.layout.add_share_item_choose;
                }
            }

            @Override
            protected ViewHolder<String> onCreateViewHolder(View root, int viewType) {
                if (viewType == R.layout.add_share_item_photo_resource) {
                    return new ResourceViewHolder(root);
                } else {
                    return new ChooseViewHolder(root);
                }
            }
        };
        mAdapter.add(mImgList);
        imgRecycler.setAdapter(mAdapter);
    }

     class ResourceViewHolder extends RecyclerAdapter.ViewHolder<String> {

        private ImageView mImg;

        public ResourceViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.add_share_item_img);
        }

        @Override
        protected void onBind(String s) {
            Log.e(TAG, "onBind: position = " + getAdapterPosition()
                + " count = " + totleCount);
            if (getAdapterPosition() == totleCount - 1) {
                mImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e(TAG, "OnBind  onClick: ");
                        ((ShareWorldFragment) mFragment).getPicsFromAblum();
                    }
                });
            }
            if (getAdapterPosition() == totleCount - 2) {
                mImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPicFromCamera();
                    }
                });
            }
            mImg.setImageResource(Integer.valueOf(s));
        }
    }

    private  void getPicFromCamera() {
        File externalFile = mActivity.getExternalFilesDir("Camera");
        mOutputImgFile = new File(externalFile, "camera_output"
                + Math.ceil(Math.random() * 1000)
                + ".jpg");
        while (mOutputImgFile.exists()) {
            mOutputImgFile = new File(externalFile, "camera_output"
                    + Math.ceil(Math.random() * 1000)
                    + ".jpg");
        }

        try {
            mOutputImgFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            mCameraImgUri = FileProvider.getUriForFile(mActivity,
                    "com.exmaple.dovebook.file.provider", mOutputImgFile);
        } else {
            mCameraImgUri = Uri.fromFile(mOutputImgFile);
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraImgUri);
        mFragment.startActivityForResult(intent, CAMERA_PHOTO);
    }

     class ChooseViewHolder extends RecyclerAdapter.ViewHolder<String> {

        private ImageView mImg;
        private RelativeLayout mDeleteLayout;

        public ChooseViewHolder(View itemView) {
            super(itemView);
            mDeleteLayout = (RelativeLayout) itemView.findViewById(R.id.add_share_item_delete);
            mImg = (ImageView) itemView.findViewById(R.id.add_share_item_img);
        }

        @Override
        protected void onBind(String s) {
            mDeleteLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteImg(getAdapterPosition());
                }
            });
            ImageManager.getInstance().loadImage(s, mImg);
        }
    }

    private void deleteImg(int index) {
        mImgList.remove(index);
        mAdapter.remove(index);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: ");
        if (requestCode == CHOOSE_PHOTO && resultCode == Activity.RESULT_OK) {
            List<Uri> uriList = Matisse.obtainResult(data);
            ArrayList pathList = PictureUriUtil.handleImageAfterKitKat(mActivity, uriList);
            addToImgList(pathList);
        }
        if (requestCode == CAMERA_PHOTO && resultCode == Activity.RESULT_OK) {
            String path = mOutputImgFile.getPath();
            mImgList.add(mImgList.size() - 2, path);
            mAdapter.add(mAdapter.getItemCount() - 2, path);
        }
    }

    private void addToImgList(ArrayList<String> imgList) {
        for (int i = 0; i < imgList.size(); i++) {
            if (mImgList.contains(imgList.get(i))) {
                imgList.remove(i);
            }
        }
        mImgList.addAll(mImgList.size() - 2, imgList);
        mAdapter.add(mAdapter.getItemCount() - 2, imgList);
    }

}
