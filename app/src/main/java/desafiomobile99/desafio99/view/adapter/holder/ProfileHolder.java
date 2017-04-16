package desafiomobile99.desafio99.view.adapter.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import desafiomobile99.desafio99.R;
import desafiomobile99.desafio99.model.ProfileModel;

public class ProfileHolder extends RecyclerView.ViewHolder {

    private TextView textProfileName;
    private TextView textProfileAge;
    private TextView textProfileBio;
    private RoundedImageView imageView;
    private Context context;
    private ProfileModel mProfileModel;

    public ProfileHolder(View itemView) {
        super(itemView);
        imageView = (RoundedImageView) itemView.findViewById(R.id.imageProfilePicture);
        textProfileName = (TextView) itemView.findViewById(R.id.textProfileName);
        textProfileAge = (TextView) itemView.findViewById(R.id.textProfileAge);
        textProfileBio = (TextView) itemView.findViewById(R.id.textProfileBio);

    }

    public void setup(Context context,ProfileModel profileModel) {
        mProfileModel = profileModel;
        this.context = context;
        textProfileName.setText(mProfileModel.getName());
        textProfileBio.setText(mProfileModel.getBio());

        setuBirthdayDate();

        if(profileModel.getImage() != null) {
            Picasso.with(context).load(profileModel.getImage()).into(imageView);
        } else {
            imageView.setImageDrawable(null);
        }
    }

    private void setuBirthdayDate() {
        if(mProfileModel.getBirthday() != null) {
            String birthday = parseISO8601BirthdayDate();
            textProfileAge.append("Age: ");
            textProfileAge.append(birthday);
        }
    }

    private String parseISO8601BirthdayDate() {

        String birthdayDate = null;
        try {

            /*
                Since API doesn't return the TimeZone properly, we will consider it as +00:00,
                and in this case we're using it to generate a birthday date, so TimeZone won't matter,
                in other situations we should consider timezone while parsing to prevent misleading
                 dates or time.
             */

            String birthday = mProfileModel.getBirthday().replace("Z", "+00:00");

            try {
                // to get rid of the ":"
                birthday = birthday.substring(0, 22) + birthday.substring(23);
            } catch (IndexOutOfBoundsException e) {
                throw new ParseException("Invalid length", 0);
            }
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).parse(birthday);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            birthdayDate = Integer.toString(Calendar.getInstance().get(Calendar.YEAR) - calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return birthdayDate;
    }

}