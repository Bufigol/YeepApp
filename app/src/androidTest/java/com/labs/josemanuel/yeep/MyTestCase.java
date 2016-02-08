package com.labs.josemanuel.yeep;


import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import com.parse.ParseUser;


public class MyTestCase extends ActivityInstrumentationTestCase2<LoginActivity> {
    private static final String USER = "c";
    private static final String PASS = "c";

    private LoginActivity activity;
    private EditText user;
    private EditText pass;

    private Button login;

    public MyTestCase() {
        super(LoginActivity.class);

    }

    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        user = (EditText) activity.findViewById(R.id.userFieldSign);
        pass = (EditText) activity.findViewById(R.id.passwordField);
        login = (Button) activity.findViewById(R.id.loginBtn);

    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPrueba() {
        if(ParseUser.getCurrentUser()!=null){
            ParseUser.logOut();
        }
        TouchUtils.tapView(this, user);
        getInstrumentation().sendStringSync(USER);

        TouchUtils.tapView(this, pass);
        getInstrumentation().sendStringSync(PASS);

        TouchUtils.clickView(this, login);

        String user = ParseUser.getCurrentUser().getUsername();
        assertTrue("The real result is: ", user.equals(USER));

    }
}