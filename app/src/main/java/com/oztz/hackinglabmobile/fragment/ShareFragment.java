package com.oztz.hackinglabmobile.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oztz.hackinglabmobile.MainActivity;
import com.oztz.hackinglabmobile.R;
import com.oztz.hackinglabmobile.businessclasses.Media;
import com.oztz.hackinglabmobile.businessclasses.Social;
import com.oztz.hackinglabmobile.database.DbOperator;
import com.oztz.hackinglabmobile.helper.App;
import com.oztz.hackinglabmobile.helper.JsonResult;
import com.oztz.hackinglabmobile.helper.PostMediaTask;
import com.oztz.hackinglabmobile.helper.PostTask;

import java.util.List;

/**
 * Created by Tobi on 20.03.2015.
 */
public class ShareFragment extends Fragment implements JsonResult{

    private static final String ARG_SECTION_NUMBER = "section_number";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_PICK = 2;

    private EditText postEditText;
    private ImageButton cameraButton, galleryButton;
    private Button shareButton;
    private ImageView thumbnail;
    private String mediaUri;
    private String socialPost;
    private boolean imageUploaded = false;
    private String qrCode;

    public static ShareFragment newInstance(int sectionNumber) {
        Log.d("DEBUG", "PlaceholderFragment.newInstance(" + String.valueOf(sectionNumber) + ")");
        ShareFragment fragment = new ShareFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        postEditText = (EditText) view.findViewById(R.id.social_post_editText);
        thumbnail = (ImageView) view.findViewById(R.id.share_img_thumbnail);
        shareButton = (Button) view.findViewById(R.id.share_button_share);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
        cameraButton = (ImageButton) view.findViewById(R.id.share_button_camera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePictureIntent();
            }
        });
        galleryButton = (ImageButton) view.findViewById(R.id.share_button_album);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPictureIntent();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(
                ARG_SECTION_NUMBER));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            mediaUri = getRealPathFromURI(data.getData());
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            thumbnail.setImageBitmap(imageBitmap);
        }
        else if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            mediaUri = getRealPathFromURI(data.getData());
            Uri contentUri = Uri.parse(data.getDataString());
            List<String> parts = contentUri.getPathSegments();
            long id = Long.parseLong(parts.get(parts.size() - 1));

            // get a thumbnail for the image
            Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(
                    getActivity().getContentResolver(),
                    id,
                    MediaStore.Images.Thumbnails.MINI_KIND,
                    null
            );
            thumbnail.setImageBitmap(bitmap);
        }
    }

    private void share(){
        DbOperator operator = new DbOperator(getActivity().getApplicationContext());
        qrCode = operator.getQrCode("author", App.eventId);
        socialPost = postEditText.getText().toString();
        if(mediaUri != null || (socialPost != null && socialPost.length() > 0)) { // Avoid empty posts
            if (mediaUri != null) {
                new PostMediaTask(this).execute(getResources().getString(R.string.rootURL) + "media/upload", mediaUri);
            } else {
                Social s = new Social(socialPost, "pending", null, App.username, App.userId, 0, App.eventId);
                if(qrCode != null) {
                    new PostTask(this).execute(getResources().getString(R.string.rootURL) + "social", new Gson().toJson(s), qrCode);
                } else {
                    new PostTask(this).execute(getResources().getString(R.string.rootURL) + "social", new Gson().toJson(s));
                }
            }
        }
    }

    private String getRealPathFromURI(Uri uri){
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private void TakePictureIntent() {
        PackageManager pm = getActivity().getPackageManager();
        if(!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return;
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(pm) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void SelectPictureIntent() {
        PackageManager pm = getActivity().getPackageManager();
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onTaskCompleted(String JsonString, String requestCode) {
        if(requestCode.equals("POST_MEDIA")){
            imageUploaded = true;
            try {
                Media m = new Gson().fromJson(JsonString, Media.class);
                Social s = new Social(socialPost, "pending", m.link, App.username, App.userId, m.mediaID, App.eventId);

                if(qrCode != null){
                    new PostTask(this).execute(getResources().getString(R.string.rootURL) + "social", new Gson().toJson(s), qrCode);
                } else {
                    new PostTask(this).execute(getResources().getString(R.string.rootURL) + "social", new Gson().toJson(s));
                }


            } catch (Exception e){
                Log.d("DEBUG", e.getMessage());
            }
        } else if(requestCode.equals("POST")){
            if(JsonString == null){
                Toast.makeText(App.getContext(), getResources().getString(R.string.error_share_failed), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(App.getContext(), getResources().getString(R.string.share_success), Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance(1))
                        .commit();
            }

        }
    }
}
