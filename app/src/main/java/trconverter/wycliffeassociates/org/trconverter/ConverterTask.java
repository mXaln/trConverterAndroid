package trconverter.wycliffeassociates.org.trconverter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import org.wycliffeassociates.trConverter.Converter;
import org.wycliffeassociates.trConverter.Mode;

import java.util.List;

/**
 * Created by mXaln on 11/11/17.
 */

public final class ConverterTask extends AsyncTask<List<Mode>, Integer, String> {

    Context context;
    ConverterResultCallback mCallback;

    public ConverterTask(Context c) {
        this.context = c;
        this.mCallback = (ConverterResultCallback) c;
    }

    @Override
    protected String doInBackground(List<Mode>... lists) {
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String[] params = new String[]{dir,"a"};
        List<Mode> modes = lists[0];

        try {
            Converter converter = new Converter(params);

            converter.setModes(modes);
            return converter.convert();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPreExecute() {
        mCallback.convertionStarted();
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        mCallback.convertionDone(result);
        super.onPostExecute(result);
    }

    public interface ConverterResultCallback {
        Void convertionStarted();

        Void convertionDone(String result);
    }
}
