package com.example.apidemo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.theidentityhub.model.Account;
import com.theidentityhub.model.AccountProvider;
import com.theidentityhub.model.Friend;
import com.theidentityhub.model.Profile;
import com.theidentityhub.model.Role;
import com.theidentityhub.service.IdentityService;

public class MainActivity extends Activity {

     private TextView textView;
     private Button signInOutButton;
     private IdentityService is;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          textView = (TextView) findViewById(R.id.categories_text);
          textView.setMovementMethod(new ScrollingMovementMethod());
          signInOutButton = (Button) findViewById(R.id.signout);

          String BASE_URL = "https://www.theidentityhub.com/[Your URL segment]]";
          String CLIENT_ID = "[Your Application Client Id]";

          is = new IdentityService(CLIENT_ID, BASE_URL);

          signInOutButton.setOnClickListener(new OnClickListener() {
               @Override
               public void onClick(View v) {
                    if (signInOutButton.getText().toString()
                              .compareTo("Sign out") == 0) {

                         is.signOut();
                         textView.setText("");
                         signInOutButton.setText("Sign in");
                    } else {
                         is.tryAuthenticate(MainActivity.this);
                         final Handler h = new Handler();
                         h.postDelayed(new Runnable() {

                              @Override
                              public void run() {
                                   Log.e("test",
                                             "IS AUTH---____--->>"
                                                       + is.isAuthenticated());
                                   if (is.isAuthenticated()) {
                                        h.removeCallbacks(this);
                                        signInOutButton.setText("Sign out");
                                        loadData();
                                   } else {
                                        h.postDelayed(this, 5 * 1000);
                                   }
                              }
                         }, 5 * 1000);
                    }

               }
          });

     }

     private void loadData() {
          try {
               // Check if user is verified
               final boolean isVerified = is.verifyToken();
               Log.e("test", "IS verified-->" + isVerified);
            // Get profile data
               final Profile object = is.getProfile();

               Log.e("test", "PROFILE FROM GET --->>>: " + object);
               final String textProfile = "--PROFILE--\n" + textView.getText()
                         + "DisplayName " + object.getDisplayName() + "\n"
                         + "EmailAddress " + object.getEmailAddress() + "\n"
                         + "GivenName " + object.getGivenName() + "\n"
                         + "IdentityId " + object.getIdentityId() + "\n"
                         + "Picture " + object.getPicture() + "\n" + "Surname "
                         + object.getSurname() + "\n" + "UserName "
                         + object.getUserName() + "\n" + "LargePictures "
                         + object.getLargePictures() + "\n" + "MediumPictures "
                         + object.getMediumPictures() + "\n" + "Is editable "
                         + object.isEditable() + "\n" + "SmallPictures "
                         + object.getSmallPictures() + "\n\n";
               textView.setText(textProfile);
            // Get accounts data
               final ArrayList<AccountProvider> accountProviders = is
                         .getAccounts();
               String textAccounts = textView.getText().toString()
                         + "\n--ACCOUNTS--";
               for (AccountProvider provider : accountProviders) {
                    Log.e("test", "provider: " + object);
                    textAccounts = textAccounts + "\n\n Display Name "
                              + provider.getDisplayName() + "\n"
                              + "ProviderImageUrl "
                              + provider.getProviderImageUrl() + "\n"
                              + "account length "
                              + provider.getAccounts().length + "\n";
                    if (provider.getAccounts().length > 0) {
                         textAccounts = textAccounts
                                   + "AccountId "
                                   + ((Account) provider.getAccounts()[0])
                                             .getAccountId()
                                   + "\n"
                                   + "DisplayName "
                                   + ((Account) provider.getAccounts()[0])
                                             .getDisplayName()
                                   + "\n"
                                   + "EmailAddress "
                                   + ((Account) provider.getAccounts()[0])
                                             .getEmailAddress()
                                   + "\n"
                                   + "PictureUrl "
                                   + ((Account) provider.getAccounts()[0])
                                             .getPictureUrl() + "\n";
                    }

               }
               textView.setText(textAccounts);
            // Get friends data
               ArrayList<Friend> friends = is.getFriends();
               String textFriends = textView.getText().toString()
                         + "\n--FRIENDS--";
               for (Friend friend : friends) {

                    textFriends = textFriends + "\n\n Display Name "
                              + friend.getDisplayName() + "\n" + "\n" + "\n"
                              + "IdentityId " + friend.getIdentityId() + "\n"
                              + "\n" + "SmallPicture "
                              + friend.getSmallPicture() + "\n" + "\n"
                              + "LargePictures " + friend.getLargePictures()
                              + "\n" + "\n" + "MediumPictures "
                              + friend.getMediumPictures() + "\n" + "\n"
                              + "SmallPictures " + friend.getSmallPictures()
                              + "\n";

               }
               textView.setText(textFriends);
            // Get roles data
               ArrayList<Role> roles = is.getRoles();
               String textRoles = textView.getText().toString() + "\n--ROLES--";
               if (roles != null) {
                    for (Role role : roles) {

                         textRoles = textRoles + "\n\n Name " + role.getName()
                                   + "\n";

                    }
               }
               textView.setText(textRoles);

          } catch (InterruptedException e) {
               e.printStackTrace();
               Log.e("test", "InterruptedException " + e.getLocalizedMessage());
          } catch (ExecutionException e) {
               e.printStackTrace();
               Log.e("test", "ExecutionException " + e.getLocalizedMessage());
          }
     }

}
