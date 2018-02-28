package kh.nobita.hang.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kh.nobita.hang.R;
import kh.nobita.hang.Utils.GamePermissions;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.activity.OneMachine;
import kh.nobita.hang.model.ListPlayers;
import kh.nobita.hang.model.Player;

import static android.app.Activity.RESULT_OK;

public class PlayersAdd extends Fragment implements OnClickListener {

    private String TAG = PlayersAdd.class.getSimpleName();

    private View view;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private ImageView imgCbHelp;
    private CheckBox cbAddLib;
    private EditText etPlayerName;
    private CircleImageView profileImage;
    private TextView tvRemovePlayer;

    //Open Camera
    private static final int REQUESCODE_CAMERA = 20;
    private static final int REQUESCODE_GALERY = REQUESCODE_CAMERA + 1;

    private ProgressDialog progressDialog;
    private String pathFilePicture = "";
    private Player player;
    private int flagSelect = -1;

    public PlayersAdd() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_players_add, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        player = ((OneMachine) getActivity()).getPlayerEdit();
        setHasOptionsMenu(true);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((OneMachine) getActivity()).setSupportActionBar(toolbar);
        ((OneMachine) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((OneMachine) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        imgCbHelp = (ImageView) view.findViewById(R.id.img_cb_help);
        cbAddLib = (CheckBox) view.findViewById(R.id.cb_check_add_to_library);
        cbAddLib.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.player_add_check_box_text));

        etPlayerName = (EditText) view.findViewById(R.id.et_player_name);
        etPlayerName.setHint(LocaleHelper.getLangResources(getActivity()).getString(R.string.player_add_name_player));

        profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        tvRemovePlayer = (TextView) view.findViewById(R.id.tv_remove_player);
        tvRemovePlayer.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.player_edit_remove));

        if (player == null) {
            toolbar.setTitle(LocaleHelper.getLangResources(getActivity()).getString(R.string.player_add_app_bar));
            tvRemovePlayer.setVisibility(View.GONE);
            cbAddLib.setVisibility(View.VISIBLE);
            imgCbHelp.setVisibility(View.VISIBLE);
        } else {
            tvRemovePlayer.setVisibility(View.VISIBLE);
            cbAddLib.setVisibility(View.GONE);
            imgCbHelp.setVisibility(View.GONE);
            pathFilePicture = player.getPathProfile();
            etPlayerName.setText(player.getName());
            toolbar.setTitle(LocaleHelper.getLangResources(getActivity()).getString(R.string.player_edit_app_bar));
            if (GamePermissions.checkPermissionReadExternalStorage(getActivity()) && !pathFilePicture.trim().equals("")) {
                profileImage.setImageBitmap(BitmapFactory.decodeFile(pathFilePicture));
            } else {
                profileImage.setImageResource(R.drawable.ic_name_player);
            }
        }
    }

    // Set Listeners
    private void setListeners() {
        // Set check listener over checkbox for showing and hiding password
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        imgCbHelp.setOnClickListener(this);
        profileImage.setOnClickListener(this);
        tvRemovePlayer.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case GamePermissions.REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (flagSelect == 0) {
                        intentCaptureImageFullHD();
                    } else if (flagSelect == 1) {
                        intentGalery();
                    }
                    if (!pathFilePicture.trim().equals("")) {
                        profileImage.setImageBitmap(BitmapFactory.decodeFile(pathFilePicture));
                    }
                } else {
                    new MaterialDialog.Builder(getActivity())
                            .title(LocaleHelper.getLangResources(getActivity()).getString(R.string.permission_necessary_title))
                            .content(LocaleHelper.getLangResources(getActivity()).getString(R.string.permission_necessary_content))
                            .positiveText(LocaleHelper.getLangResources(getActivity()).getString(R.string.agree))
                            .show();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Resources resources = LocaleHelper.getLangResources(getActivity());
        switch (v.getId()) {
            case R.id.profile_image:
                new MaterialDialog.Builder(getActivity())
                        .title(resources.getString(R.string.player_add_dialog_title_picture))
                        .items(resources.getStringArray(R.array.player_add__picture_chose))
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                flagSelect = which;
                                if (GamePermissions.checkPermissionReadExternalStorage(getActivity())) {
                                    switch (which) {
                                        case 0:
                                            intentCaptureImageFullHD();
                                            break;
                                        case 1:
                                            intentGalery();
                                            break;
                                    }
                                }
                            }
                        })
                        .show();
                break;
            case R.id.img_cb_help:
                new MaterialDialog.Builder(getActivity())
                        .title(resources.getString(R.string.player_library_app_bar))
                        .content(resources.getString(R.string.dialog_content_help))
                        .positiveText(android.R.string.ok)
                        .show();
                break;
            case R.id.tv_remove_player:
                new MaterialDialog.Builder(getActivity())
                        .title(resources.getString(R.string.dialog_delete_player_lib_title))
                        .content(resources.getString(R.string.player_edit_remove))
                        .positiveText(resources.getString(R.string.agree))
                        .negativeText(resources.getString(R.string.disagree))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                if (player.getId() > -1) {
                                    ListPlayers.getInstance(getActivity()).unCheckPlayerInPlayersLib(player);
                                } else {
                                    ListPlayers.getInstance(getActivity()).deletePlayers(player);
                                }
                                getActivity().onBackPressed();
                            }
                        })
                        .show();
                break;
        }
    }

    public void intentGalery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUESCODE_GALERY);
    }

    public void intentCaptureImageFullHD() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUESCODE_CAMERA);
        } catch (Exception e) {
            // Log is: debug, show Text in logcat
            e.printStackTrace();
            Log.v(TAG, "Can't create file to take picture!");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUESCODE_CAMERA:
                    try {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        // Thread Save Photo after capture
                        progressDialog = ProgressDialog.show(getActivity(), "Please wait...", "Save Photo in SD Card ...", true);
                        storeCameraPhotoInSDCard(bitmap, currentDateFormat());
                    } catch (Exception e) {
                        Log.d(TAG, "Failed to load", e);
                    }
                    break;
                case REQUESCODE_GALERY:
                    try {
                        Uri URI = data.getData();
                        String[] FILE = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getActivity().getContentResolver().query(URI, FILE, null, null, null);

                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(FILE[0]);
                        pathFilePicture = cursor.getString(columnIndex);
                        cursor.close();
                        profileImage.setImageBitmap(BitmapFactory.decodeFile(pathFilePicture));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private String currentDateFormat() {
        // get date format to name File Photo
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate) {
        pathFilePicture = getRealPathFromURI(Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "photo_" + currentDate + ".png", currentDate)));
        progressDialog.dismiss();
        profileImage.setImageBitmap(BitmapFactory.decodeFile(pathFilePicture));
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    // Check Validation before login
    private void checkValidation() {
        etPlayerName.setError(null);
        // Get email id and password
        String name = etPlayerName.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;
        Resources resources = LocaleHelper.getLangResources(getActivity());

        if (TextUtils.isEmpty(name)) {
            etPlayerName.setError(resources.getString(R.string.error_field_name));
            focusView = etPlayerName;
            cancel = true;
        }
        if (name.trim().length() > 25) {
            etPlayerName.setError(resources.getString(R.string.error_long_name) + " " + 25);
            focusView = etPlayerName;
            cancel = true;
        }
        if (player != null) {
            if (!name.equals(player.getName()) && ListPlayers.getInstance(getActivity()).isNameExist(name)) {
                etPlayerName.setError(resources.getString(R.string.error_exist_name));
                focusView = etPlayerName;
                cancel = true;
            }
        } else {
            if (ListPlayers.getInstance(getActivity()).isNameExist(name)) {
                etPlayerName.setError(resources.getString(R.string.error_exist_name));
                focusView = etPlayerName;
                cancel = true;
            }
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            if (player == null) {
                if (cbAddLib.isChecked()) {
                    ListPlayers.getInstance(getActivity()).addPlayerToLib(new Player(name, pathFilePicture, true));
                } else {
                    ListPlayers.getInstance(getActivity()).addPlayer(new Player(name, pathFilePicture));
                }
            } else {
                Player playerOld = player;
                player.setName(name);
                player.setPathProfile(pathFilePicture);
                if (player.getId() > -1) {
                    ListPlayers.getInstance(getActivity()).editPlayersLib(player);
                } else {
                    ListPlayers.getInstance(getActivity()).editPlayers(playerOld, player);
                }
            }
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_players_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.players_library_save:
                checkValidation();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}