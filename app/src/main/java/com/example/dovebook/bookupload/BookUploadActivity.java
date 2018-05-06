package com.example.dovebook.bookupload;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dovebook.Information.InfoManager;
import com.example.dovebook.R;
import com.example.dovebook.base.BaseActivity;
import com.example.dovebook.base.model.User;
import com.example.dovebook.book.model.Book;
import com.example.dovebook.main.MainActivity;
import com.example.dovebook.utils.ToastUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.io.File;
import java.util.zip.Inflater;

import butterknife.BindInt;
import butterknife.BindView;

/**
 * Created by 28748 on 2018/4/15.
 */

public class BookUploadActivity extends BaseActivity implements BookUploadContract.UploadView {

    private static final String TAG = "BookUploadActivity";

    public static final int CHOOSE_PHOTO = 2;

    private static final int REQUEST_CAMERA = 3;

    private static final int RESULT_SCAN = 0x0000c0de;

    @BindView(R.id.iv_book_image_upload)
    ImageView bookImage;
    @BindView(R.id.et_book_title_upload)
    EditText bookTitle;
    @BindView(R.id.et_book_author_upload)
    EditText bookAuthor;
    @BindView(R.id.et_book_isbn_upload)
    EditText bookIsbn;
    @BindView(R.id.et_book_publisher_upload)
    EditText bookPublisher;
    @BindView(R.id.et_book_price_upload)
    EditText bookPrice;
    @BindView(R.id.et_book_page_upload)
    EditText bookPage;
    @BindView(R.id.et_book_publishdate_upload)
    EditText publishDate;
    @BindView(R.id.et_book_abstract_upload)
    EditText bookAbstract;
    @BindView(R.id.bt_book_upload)
    Button uploadButton;

    private View.OnClickListener mListener;


    //上传进度窗口
    private ProgressDialog mUploadProgressDialog;

    File file;

    //test
    private String path;

    //进度对话框
    BookUploadPresenter mPresenter;

    @Override
    protected View initContentView() {
        View viewThis = getLayoutInflater().inflate(R.layout.activity_book_upload, null);
        return viewThis;
    }

    @Override
    protected void initOptions() {
        mPresenter = new BookUploadPresenter(this);
        mListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iv_book_image_upload:
                        choosePhotoFromAlbum();
                        break;
                    case R.id.bt_book_upload:
                        getBookInfo();
                        break;
                }
            }
        };

        bookImage.setOnClickListener(mListener);
        //长按来扫描条形码获取图书信息
        bookImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                scanBarCodeCheck();
                return false;
            }
        });

        uploadButton.setOnClickListener(mListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        file = new File(mPresenter.handleImage(data));
                        Log.d(TAG, "onActivityResult: " + mPresenter.handleImage(data));
                        //加载图书图片
                        Glide.with(this).load(file)
                                .into(bookImage);
                        path = "" + data.getData();
                    }
                }
                break;
            case RESULT_SCAN:
                if (resultCode == RESULT_OK) {
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                    if (scanResult != null) {
                        //获取到图书的ISBN
                        String result = scanResult.getContents();
                        bookIsbn.setText(result);
                        mPresenter.querytBookInfo(result);
                    }
                }
                break;
            default:
                break;
        }
    }


    public void openAlbum() {
        Log.d(TAG, "openAlbum: ");
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    private void getBookInfo() {
        Book book = new Book();
        book.setBookImagepath(path)
                .setBookPages(bookPage.getText().toString())
                .setBookPrice(bookPrice.getText().toString())
                .setBookIsbn(bookIsbn.getText().toString())
                .setBookPublisher(bookPublisher.getText().toString())
                .setBookAuthor(bookAuthor.getText().toString())
                .setBookTitle(bookTitle.getText().toString())
                .setBookSummary(bookAbstract.getText().toString())
                .setBookPubdate(publishDate.getText().toString());
        Log.d(TAG, "getBookInfo: " + book.toString());
        mPresenter.uploadBook(book);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                }
                break;
            case REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    scanBarCode();
                } else {
                    ToastUtil.shortToast("拒绝");
                }
                break;
            default:
                break;
        }
    }

    public void choosePhotoFromAlbum() {
        if (ContextCompat.checkSelfPermission(BookUploadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BookUploadActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    /**
     * 扫描条形码
     */
    public void scanBarCodeCheck() {
        if (ContextCompat.checkSelfPermission(BookUploadActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BookUploadActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            scanBarCode();
        }
    }

    /**
     * 扫描条形码
     */
    public void scanBarCode() {
        IntentIntegrator integrator = new IntentIntegrator(BookUploadActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setCaptureActivity(ScanActivity.class);
        integrator.setPrompt("请扫描ISBN条形码");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    public void showUploadProcess() {
        mUploadProgressDialog = ProgressDialog.show(this, "提示", "图书抛出中...", false, true);
    }

    @Override
    public void hideUploadPrecess() {
        mUploadProgressDialog.dismiss();
    }

    @Override
    public void showUploadSuccess() {
        ToastUtil.shortToast("上传成功！");
    }

    @Override
    public void showUploadFailed() {
        ToastUtil.shortToast("请检查你的网络...");
    }

    @Override
    public void showInfoErrors(String message) {
        ToastUtil.shortToast(message);
    }


    //错误的EditText获取焦点
    @Override
    public void getEditFocus(int editCode) {

    }

    @Override
    public void showBookInfo(Book book) {
        bookTitle.setText(book.getBookTitle());
        bookAuthor.setText(book.getBookAuthor());
        bookPublisher.setText(book.getBookPublisher());
        bookPage.setText(""+book.getBookPages());
        bookPrice.setText("" + book.getBookPrice());
        bookAbstract.setText(book.getBookSummary());
        publishDate.setText(book.getBookPubdate());
    }
}

