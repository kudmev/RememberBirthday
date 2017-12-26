package dmitrykuznetsov.rememberbirthday.common.service;

import android.content.Context;

/**
 * Created by vernau on 6/1/17.
 */

class TokenJobImpl implements TokenJob {

    private static final String TAG = TokenJobImpl.class.getSimpleName();

    private Context context;

    public TokenJobImpl(Context context) {
        this.context = context;
    }

    @Override
    public void startTokenService() {
//        Log.d(TAG, "start Service");
//        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
//        Job myJob = dispatcher.newJobBuilder()
//                .setService(TokenService.class)
//                .setTag(Constants.JOB_VERIFICATION_TOKEN)
//                .setRecurring(true)
//                .setLifetime(Lifetime.FOREVER)
//                .setTrigger(Trigger.executionWindow(1, 30))
//                .setReplaceCurrent(true)
//                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
//                .setConstraints(
//                        Constraint.ON_ANY_NETWORK
//                )
//                .build();
//        dispatcher.mustSchedule(myJob);
    }
}
