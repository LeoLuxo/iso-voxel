package engine.io;

import java.util.ArrayList;

public class Console {
	
	private static ArrayList<String> stream = new ArrayList<String>();
	
	public static int getStreamSize() {
		return stream.size();
	}
	
	public static String[] getFullStream() {
		return stream.toArray(new String[0]);
	}
	
	public static String[] getStream(int start) {
		return stream.subList(start, stream.size() - 1).toArray(new String[0]);
	}
	
	public static String[] getStream(int start, int end) {
		return stream.subList(start, end).toArray(new String[0]);
	}
	
	public static void print(String s) {
		stream.add(s);
		System.out.print(s);
	}
	
	public static void println(String s) {
		print(s + "\n");
	}
	
	public static void print(double d) {
		print("" + d);
	}
	
	public static void println(double d) {
		println("" + d);
	}
	
	public static void print(boolean b) {
		print("" + b);
	}
	
	public static void println(boolean b) {
		println("" + b);
	}
	
	public static void errprint(String s) {
		stream.add(s);
		System.err.print(s);
	}
	
	public static void errprintln(String s) {
		errprint(s + "\n");
	}
	
	public static void err(Exception e) {
		stream.add(e.getMessage());
		e.printStackTrace();
	}
	
}
