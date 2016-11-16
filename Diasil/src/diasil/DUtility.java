package diasil;

import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DUtility
{
    public static List<String> readAllLines(String file_path)
    {
        try
        {
            return Files.readAllLines(Paths.get(file_path), StandardCharsets.US_ASCII);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

	private static DateFormat date_format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	public static String getCurrentDateTime()
	{
		Date dateobj = new Date();
		return date_format.format(dateobj);
	}
	
	private static DateFormat date_format_file_name = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	public static String getCurrentDateTimeFileName()
	{
		Date date = new Date();
		return date_format_file_name.format(date);
	}
	
	private static DecimalFormat df = new DecimalFormat("#.##");
	public static String getReadableTime(long m)
    {
        int milli_in_sec = 1000;
        int milli_in_min = milli_in_sec*60;
        int milli_in_hour = milli_in_min*60;
        int milli_in_day = milli_in_hour*24;
        float mp = m;
        if (m < milli_in_sec)
        {
            return m+" milliseconds";
        }
        else if (m < milli_in_min)
        {
            return df.format(mp/milli_in_sec)+" seconds";
        }
        else if (m < milli_in_hour)
        {
            return df.format(mp/milli_in_min)+" minutes";
        }
        else if (m < milli_in_day)
        {
            return df.format(mp/milli_in_hour)+" hours";
        }
        return df.format(mp/milli_in_day)+" days";
    }
}
