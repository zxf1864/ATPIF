package com.afei.atpif;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afei.atpif.CustomListView.ATPIFListModel;
import com.afei.atpif.CustomWidget.CircleImage;
import com.afei.atpif.CustomWidget.SelectPicPopupWindow;
import com.afei.atpif.CustomWidget.Utils;
import com.afei.atpif.Model.TrainListData;
import com.afei.atpif.Util.DatabaseAdapter;
import com.afei.atpif.Util.FileUtil;

import java.io.File;

public class AddDevActivity extends AppCompatActivity
        implements View.OnClickListener{

    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";
    private String urlpath = "";


    private static final int REQUESTCODE_PICK = 0;
    private static final int REQUESTCODE_TAKE = 1;
    private static final int REQUESTCODE_CUTTING = 2;
    private Context mContext;
    private CircleImage avatarImg;
    private SelectPicPopupWindow menuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dev);


        Toolbar toolbar = (Toolbar) findViewById(R.id.child_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        //使能app bar的导航功能
        ab.setDisplayHomeAsUpEnabled(true);

        avatarImg = (CircleImage) findViewById(R.id.imageView);

        initView();

        setOnListener();

    }

    protected void initView() {
        Fragment fragment = null;
        mContext = AddDevActivity.this;

        if(urlpath.equals("")){
            return;
        }
        Bitmap bitmap= BitmapFactory.decodeFile(urlpath);
        avatarImg.setImageBitmap(bitmap);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                //打开相机
                case R.id.takePhotoBtn:
                    Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                    startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                    break;
                //打开相册
                case R.id.pickPhotoBtn:
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, REQUESTCODE_PICK);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_PICK:
                try {
                    startPhotoZoom(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            case REQUESTCODE_TAKE:
                File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }


    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {


            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            urlpath = FileUtil.saveFile(mContext, "Train_" + TrainListData.getInstance().productList.size() + "_temphead.jpg", photo);



            avatarImg.setImageDrawable(drawable);
        }
    }


    protected void setOnListener() {
        avatarImg.setOnClickListener(this);
        Button BT_OK = (Button)findViewById(R.id.button_ok);
        BT_OK .setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView:
                menuWindow = new SelectPicPopupWindow(mContext, itemsOnClick);
                menuWindow.showAtLocation(this.findViewById(R.id.add_dev_layout),
                        Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.button_ok:
                EditText productName = (EditText) findViewById(R.id.editText_name);
                EditText productIP = (EditText) findViewById(R.id.editText_IP);
                EditText productPort = (EditText) findViewById(R.id.editText_Port);

                ATPIFListModel product = new ATPIFListModel();
                product.atpifid = TrainListData.getInstance().productList.size();
                product.atpifName = productName.getText().toString();
                product.atpifIP = productIP.getText().toString();
                product.clientNum = productPort.getText().toString();
                product.atpif_photo_path = urlpath;

                DatabaseAdapter dbadapter = DatabaseAdapter.getInstance(getApplicationContext());
                dbadapter.create(product);

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("product", product);
                setResult(RESULT_OK,i);
                finish();
                break;
        }
    }
}
