package org.catrobat.catroid.ui.recyclerview.dialog.login;

import android.os.Bundle;

public interface SignInCompleteListener {
    void onLoginCancel();

    void onLoginSuccessful(Bundle bundle);
}
