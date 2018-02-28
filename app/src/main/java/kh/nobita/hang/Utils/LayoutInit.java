package kh.nobita.hang.Utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import de.hdodenhof.circleimageview.CircleImageView;
import kh.nobita.hang.R;
import kh.nobita.hang.model.Player;

public class LayoutInit {

    public static CircleImageView createCircleView(Context context) {
        CircleImageView cv = new CircleImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.gv_players_item), (int) context.getResources().getDimension(R.dimen.gv_players_item));
        params.gravity = Gravity.CENTER;
        cv.setPadding((int) context.getResources().getDimension(R.dimen.detail_padding), 0, (int) context.getResources().getDimension(R.dimen.detail_padding), 0);
        cv.setLayoutParams(params);
        cv.setBorderColor(ContextCompat.getColor(context, R.color.colorBlack));
        cv.setBorderWidth(2);
        return cv;
    }

    public static LinearLayout addPlayerToLN(final Context context, final Player player) {
        LinearLayout ln = createLinearLayoutVertical(context);
        CircleImageView cv = createCircleView(context);
        if (player.getPathProfile().trim().equals("")) {
            cv.setImageResource(R.drawable.ic_name_player);
        } else {
            if (GamePermissions.checkPermissionReadExternalStorage(context)) {
                cv.setImageBitmap(BitmapFactory.decodeFile(player.getPathProfile()));
            } else {
                cv.setImageResource(R.drawable.ic_name_player);
            }
        }
        TextView tv = createTextView(context);
        tv.setText(player.getName());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        cv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new MaterialDialog.Builder(context)
                        .title(player.getRole().getName())
                        .content(player.getRole().getRole())
                        .positiveText(LocaleHelper.getLangResources(context).getString(R.string.agree))
                        .show();
            }
        });
        ln.addView(cv);
        ln.addView(tv);
        return ln;
    }

    public static TextView createTextViewWrap(Context context) {
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(params);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setPadding((int) context.getResources().getDimension(R.dimen.detail_padding), 0, (int) context.getResources().getDimension(R.dimen.detail_padding), 0);
        return tv;
    }

    public static TextView createTextView(Context context) {
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(params);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        tv.setPadding((int) context.getResources().getDimension(R.dimen.detail_padding), 0, (int) context.getResources().getDimension(R.dimen.detail_padding), 0);
        return tv;
    }

    public static LinearLayout createLinearLayoutHorizontal(Context context) {
        LinearLayout ln = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        ln.setOrientation(LinearLayout.HORIZONTAL);
        ln.setPadding(0, (int) context.getResources().getDimension(R.dimen.detail_padding), 0, (int) context.getResources().getDimension(R.dimen.detail_padding));
        ln.setLayoutParams(params);
        return ln;
    }

    public static LinearLayout createLinearLayoutVertical(Context context) {
        LinearLayout ln = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        ln.setOrientation(LinearLayout.VERTICAL);
        ln.setPadding((int) context.getResources().getDimension(R.dimen.detail_padding), 0, (int) context.getResources().getDimension(R.dimen.detail_padding), 0);
        ln.setLayoutParams(params);
        return ln;
    }

    public static HorizontalScrollView createHorizontalScrollView(Context context) {
        HorizontalScrollView hs = new HorizontalScrollView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        hs.setLayoutParams(params);
        return hs;
    }

    public static void showInfo(Context context, String title, String content) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(R.string.agree)
                .show();
    }
}
