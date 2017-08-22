package com.jobs.schedule;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


public class CleanupJobService extends JobService {
    private static final String TAG = CleanupJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Cleanup job started");
        new CleanupTask().execute(params);
        //Work is not yet complete

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        //No need to reschedule any jobs
        Toast.makeText(getApplicationContext(), "job stop", Toast.LENGTH_LONG).show();

        return true;
    }


    /* Handle access to the database on a background thread */
    private class CleanupTask extends AsyncTask<JobParameters, Void, JobParameters> {

        @Override
        protected JobParameters doInBackground(JobParameters... params) {
            return params[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            //Notify that the work is now done
            Toast.makeText(getApplicationContext(), "job started", Toast.LENGTH_LONG).show();
            Intent launchIntent = getApplication()
                    .getPackageManager()
                    .getLaunchIntentForPackage(getApplicationContext().getPackageName());
            getApplication().startActivity(launchIntent);
            jobFinished(jobParameters,false);
        }
    }
}
