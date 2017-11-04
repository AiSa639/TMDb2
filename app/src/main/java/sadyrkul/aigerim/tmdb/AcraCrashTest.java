package sadyrkul.aigerim.tmdb;

import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

//@ReportsCrashes(mailTo = "ai.sa639@gmail.com", mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)

/**
 * Created by Aigerim on 04.11.2017.
 */

public class AcraCrashTest extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // The following line triggers the initialization of ACRA
        ACRA.init(this);
    }
}