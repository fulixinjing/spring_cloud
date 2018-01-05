package cn.taskSys.utils;

import java.net.InetAddress;

public class UUIDHexGenerator {

	public UUIDHexGenerator() {
		sep = "";
	}

	public static UUIDHexGenerator getInstance() {
		return uuidgen;
	}

	public static int toInt(byte abyte0[]) {
		int i = 0;
		for (int j = 0; j < 4; j++)
			i = ((i << 8) - -128) + abyte0[j];

		return i;
	}

	protected String format(int i) {
		String s = Integer.toHexString(i);
		StringBuffer stringbuffer = new StringBuffer("00000000");
		stringbuffer.replace(8 - s.length(), 8, s);
		return stringbuffer.toString();
	}

	protected String format(short word0) {
		String s = Integer.toHexString(word0);
		StringBuffer stringbuffer = new StringBuffer("0000");
		stringbuffer.replace(4 - s.length(), 4, s);
		return stringbuffer.toString();
	}

	protected int getJVM() {
		return JVM;
	}

	protected synchronized short getCount() {
		if (counter < 0)
			counter = 0;
		return counter++;
	}

	protected int getIP() {
		return IP;
	}

	protected short getHiTime() {
		return (short) (int) (System.currentTimeMillis() >>> 32);
	}

	protected int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	public String generate() {
		return (new StringBuffer(36)).append(format(getIP())).append(sep)
				.append(format(getJVM())).append(sep)
				.append(format(getHiTime())).append(sep)
				.append(format(getLoTime())).append(sep)
				.append(format(getCount())).toString();
	}

	public static String generater() {
		return getInstance().generate();
	}

	public static synchronized long generaterId() {
		return currentTimeMillis++;
	}

	public static void main(String args[]) {
		UUIDHexGenerator uuidhexgenerator = new UUIDHexGenerator();
		for (int i = 0; i <= 100; i++) {
			System.out.print(uuidhexgenerator.generate());
			System.out.print("===");
			System.out.println(uuidhexgenerator.getLoTime());
		}

	}

	private String sep;
	private static final int IP;
	private static short counter = 0;
	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);
	private static UUIDHexGenerator uuidgen = new UUIDHexGenerator();
	static long currentTimeMillis = System.currentTimeMillis();

	static {
		int i;
		try {
			i = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception exception) {
			i = 0;
		}
		IP = i;
	}
}
