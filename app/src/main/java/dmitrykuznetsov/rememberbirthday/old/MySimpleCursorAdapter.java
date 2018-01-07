package dmitrykuznetsov.rememberbirthday.old;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.DetailBirthdayActivity;


/**
 * Created by Dmitry Kuznetsov on 09.01.2016.
 */
class MySimpleCursorAdapter extends SimpleCursorAdapter {

    private final Context ctx;
    private LayoutInflater inflater;
    private Cursor cursor;

    public MySimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.ctx = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        inflater = LayoutInflater.from(ctx);
        cursor = getCursor();
        View rowView = inflater.inflate(R.layout.list_contact, parent, false);
        TextView textName = (TextView) rowView.findViewById(R.id.text_name);
        TextView textDate = (TextView) rowView.findViewById(R.id.text_date);
        TextView textAge = (TextView) rowView.findViewById(R.id.text_age);
        ImageView imageUser = (ImageView) rowView.findViewById(R.id.user_image);

        if (cursor != null) {
            cursor.moveToPosition(position);

            String name = cursor.getString(cursor.getColumnIndex(RememberContentProvider.NAME));
            int age = cursor.getInt(cursor.getColumnIndex(RememberContentProvider.AGE_PERSON));
            final int uid = cursor.getInt(cursor.getColumnIndex(RememberContentProvider.UID));
            long milliseconds = cursor.getLong(cursor.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY_IN_SECONDS));
            String path = cursor.getString(cursor.getColumnIndex(RememberContentProvider.PATHIMAGE));
            String date = cursor.getString(cursor.getColumnIndex(RememberContentProvider.DATE_BIRTHDAY));


            if (MyHelperClass.isUserBirthdayToday(milliseconds) == true) {
                //date = ctx.getString(R.string.today);
                age++;
            }

            textName.setText(name);
            textDate.setText(date);
            textAge.setText(MyHelperClass.getAgeSuffix(age, ctx, 0));

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    DetailBirthdayActivity.open(ctx, uid);
//                    Intent intent = new Intent();
//                    intent.setClass(ctx, ActivityDetailPerson.class);
//                    intent.putExtra("position", uid);
//                    ctx.startActivity(intent);
                }
            });

            Bitmap bitmap = MyHelperClass.loadImageFromStorage(path, uid);
            if (bitmap != null)
                imageUser.setImageBitmap(bitmap);

            return rowView;
        } else {
            return super.getView(position, convertView, parent);
        }
    }


}