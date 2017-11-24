package com.cmput301f17t07.ingroove.DataManagers.Command;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * This class managers the job of checking for a server connection and synchronizing any
 * local changes the user has made while offline
 *
 * @see android.app.job.JobScheduler
 *
 * Created by fraserbulbuc on 2017-11-07.
 */
public class ESJobService extends JobService {

    private final int IDENTIFIER = 1;

    /**
     * @param jobParameters the parameters for the particular Job
     * @return true if the job will not finish before returning
     */
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        elasticHandler.sendMessage(Message.obtain(elasticHandler, IDENTIFIER, jobParameters));
        return true;
    }

    /**
     * @param jobParameters
     * @return
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private Handler elasticHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Toast.makeText( getApplicationContext(),
                    "JobService task running", Toast.LENGTH_SHORT )
                    .show();

            // Must call jobFinished after job is completed
            // the parameters should be the same as the onStartJob parameters for the same job to
            //  run again, and the boolean should be true if you want to reschedule the job
            jobFinished( (JobParameters) message.obj, false );
            return true;
        }
    });
}
