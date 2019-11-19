package com.thinkcore.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @命令操作类
 */
// define <output and input> to this abstract class
public class TShellUtils {
	public static boolean mIsRooted = false;

	// for internal access
	public TShellUtils() {
	}

	public boolean verifyRootAccess() {
		String command[] = { "su", "-c", "ls", "/data" };
		TShellUtils shell = new TShellUtils();
		String text = shell.sendShellCommand(command);
		if ((text.indexOf("app") > -1) || (text.indexOf("anr") > -1)
				|| (text.indexOf("user") > -1) || (text.indexOf("data") > -1)) {
			mIsRooted = false;
		} else {
			mIsRooted = true;
		}

		return isVerifyRootAccess();
	}

	public static boolean isVerifyRootAccess() {
		return mIsRooted;
	}

	// for external access

	public String sendShellCommand(String[] cmd) {
		Log.i("", "\n###executing: " + cmd[0] + "###");
		String AllText = "";
		try {
			String line;
			Process process = new ProcessBuilder(cmd).start();
			BufferedReader STDOUT = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			BufferedReader STDERR = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
			try {
				process.waitFor();
			} catch (InterruptedException ex) {
				Logger.getLogger(TShellUtils.class.getName()).log(Level.SEVERE,
						null, ex);
			}
			while ((line = STDERR.readLine()) != null) {
				AllText = AllText + "\n" + line;
			}
			while ((line = STDOUT.readLine()) != null) {
				AllText = AllText + "\n" + line;
				while ((line = STDERR.readLine()) != null) {
					AllText = AllText + "\n" + line;
				}
			}
			return AllText;
		} catch (IOException ex) {
			Log.i("",
					"Problem while executing in Shell.sendShellCommand() Received "
							+ AllText);
			return "CritERROR!!!";
		}

	}
}
