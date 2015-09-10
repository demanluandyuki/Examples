package com.joyfulmath.study.utils;

import java.util.Locale;

public class TraceLog {

	private static class LogLine {
		private String tag;
		private String text;

		public LogLine(String tag, String text) {
			this.tag = tag;
			this.text = text;
		}
	}

	private static final LogLine concatLogLine(String extraMessage) {
		StackTraceElement ste = new Throwable().getStackTrace()[2];
		String shortClassName = ste.getClassName().substring(
				ste.getClassName().lastIndexOf(".") + 1);
		String logText = String.format(Locale.US, "%s: %s [at (%s:%d)]\n",
				ste.getMethodName(), extraMessage, ste.getFileName(),
				ste.getLineNumber());
		return new LogLine(shortClassName, logText);
	}

	public static void v(String msg) {
		LogLine line = concatLogLine(msg);
		System.out.print(line.text);
	}

	public static void v() {
		LogLine line = concatLogLine("");
		System.out.print(line.text);
	}
	
	public static void v(String format, Object... args) {
		LogLine line = concatLogLine(String.format(format, args));
		System.out.print(line.text);
	}

}
