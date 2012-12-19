package com.example.htmlgrabber;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class HtmlGrabber {

	long delay = 60*1000; // delay in ms : 60 * 1000 ms = 60 sec.
	LoopTask task = new LoopTask();
	Timer timer = new Timer("Task");
	
	public void start() {
		timer.cancel();
		timer = new Timer("Task");
		Date executionDate = new Date(); // no params = now
		timer.schedule(task, executionDate, delay);
	}

	private class LoopTask extends TimerTask {
		public void run() {
			try {
				URL url = new URL("http://207.251.86.229/nyc-links-cams/TrafficSpeed.php");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();

				InputStream stream = connection.getInputStream();

				int bufferSize = 4000;

				final char[] buffer = new char[bufferSize];
				final StringBuilder out = new StringBuilder();
				final Reader in = new InputStreamReader(stream, "UTF-8");
				for (;;) {
					int rsz = in.read(buffer, 0, buffer.length);
					if (rsz < 0)
						break;
					out.append(buffer, 0, rsz);
				}

				in.close();
				writeStringToFile(out.toString());
			}
			catch(Exception e) {}
		}
		
		private String getTime() {
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Calendar cal = Calendar.getInstance();
			return dateFormat.format(cal.getTime());
		}

		private void writeStringToFile(String text) throws IOException {
			// TODO Auto-generated method stub
			boolean success = (new File("C:\\HtmlGrabber")).mkdir();
			if(success) {
				File f = new File("C:\\HtmlGrabber\\" + getTime() + ".txt");
				if(!f.exists())
					f.createNewFile();
				FileWriter fstream = new FileWriter(f);
				BufferedWriter outfile = new BufferedWriter(fstream);
				outfile.write(text);
				outfile.close();
			}
		}
		
	}
	
	public static void main(String args[]) {
		HtmlGrabber obj = new HtmlGrabber();
		obj.start();
	}

}
