import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class SubtitlesTool {
    public static void main(String[] args) throws FileNotFoundException {
	File rootFolder = new File(
		"D:\\Videos\\Movies & TV\\Series\\The Big Bang Theory\\Season 05\\the-big-bang-theory-fifth-season_arabic-953588_2");

	for (File subs : rootFolder.listFiles())
	    removePos(subs);

	System.out.println("Finished");
    }

    public static void removePos(File file) throws FileNotFoundException {
	Scanner scanner = new Scanner(file);

	String line = null;
	ArrayList<String> lines = new ArrayList<>();
	while (scanner.hasNextLine()) {
	    line = scanner.nextLine();

	    if (line.contains("{\\pos(")) {
		int p = line.indexOf("{\\pos(");
		line = line.substring(0, p);
	    }

	    lines.add(line);
	}

	PrintWriter printWriter = new PrintWriter(file);

	for (String s : lines)
	    printWriter.println(s);

	printWriter.flush();
	printWriter.close();
    }

    public static String shiftDate(String dateString, long shift) throws ParseException {
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:m:s,S");

	Date date = simpleDateFormat.parse(dateString);

	date.setTime(date.getTime() + shift);
	if (date.getTime() + shift < 0)
	    date.setTime(0);

	String newDateString = simpleDateFormat.format(date);
	return newDateString;
    }

    public static String shiftDate(String dateString, long shift, String shiftfrom) throws ParseException {
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:m:s,S");

	Date date = simpleDateFormat.parse(dateString);
	Date datezero = simpleDateFormat.parse(shiftfrom);

	if (date.after(datezero))
	    date.setTime(date.getTime() + shift);
	    // if (date.getTime() + shift < 0)
	    // date.setTime(0);

	String newDateString = simpleDateFormat.format(date);
	System.out.println(newDateString);
	return newDateString;
    }

    public static void shiftSubtitles() {
	Scanner in = new Scanner(System.in);

	System.out.print("Enter file path: ");
	String filename = in.nextLine().trim();
	filename = filename.substring(1, filename.length() - 1);
	File file = new File(filename);
	System.out.println("Openning... " + filename);

	System.out.print("Enter shift amount: ");
	long shift = in.nextLong();

	System.out.print("Enter shift start (ex \"0:0:0,0\"): ");
	String shiftfrom = in.nextLine();

	// System.out.print("Enter charset: ");
	// String charset = in.nextLine();
	String charset = "ANSI";

	in.close();

	shiftSubtitles(file, shift, shiftfrom, charset);
    }

    public static void shiftSubtitles(File file, long shift) {
	try {
	    String filename = file.getAbsolutePath();
	    Scanner fin = new Scanner(new FileInputStream(file));
	    ArrayList<String> filecontent = new ArrayList<>();
	    while (fin.hasNextLine())
		filecontent.add(fin.nextLine());
	    fin.close();

	    String oldCopyPath = filename.substring(0, filename.length() - 4) + " old"
		    + filename.substring(filename.length() - 4);

	    file.renameTo(new File(oldCopyPath));

	    file = new File(filename);

	    PrintWriter fout = new PrintWriter(new FileOutputStream(file));

	    for (String line : filecontent) {
		if (line.contains(" --> ")) {
		    int firstSpace = line.indexOf(' ');
		    int lastSpace = line.indexOf(' ', firstSpace + 1);
		    String fromTime = line.substring(0, firstSpace);
		    String toTime = line.substring(lastSpace + 1);

		    line = shiftDate(fromTime, shift) + " --> " + shiftDate(toTime, shift);
		}
		fout.println(line);
	    }

	    fout.flush();
	    fout.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    private static void shiftSubtitles(File file, long shift, String shiftfrom) {
	try {
	    String filename = file.getAbsolutePath();
	    Scanner fin = new Scanner(new FileInputStream(file));
	    // Scanner fin = new Scanner(new FileInputStream(file), charset);
	    ArrayList<String> filecontent = new ArrayList<>();
	    while (fin.hasNextLine())
		filecontent.add(fin.nextLine());
	    fin.close();

	    System.out.println(filecontent.get(3));

	    String oldCopyPath = filename.substring(0, filename.length() - 4) + " old"
		    + filename.substring(filename.length() - 4);

	    // if (!(new File(oldCopyPath)).exists())
	    file.renameTo(new File(oldCopyPath));

	    file = new File(filename);

	    PrintWriter fout = new PrintWriter(file);
	    // PrintWriter fout = new PrintWriter(file.getAbsolutePath(),
	    // charset);
	    for (String line : filecontent) {
		if (line.contains(" --> ")) {
		    int firstSpace = line.indexOf(' ');
		    int lastSpace = line.indexOf(' ', firstSpace + 1);
		    String fromTime = line.substring(0, firstSpace);
		    String toTime = line.substring(lastSpace + 1);
		    line = shiftDate(fromTime, shift, shiftfrom) + " --> " + shiftDate(toTime, shift, shiftfrom);
		}
		fout.println(line);
	    }
	    fout.flush();
	    fout.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private static void shiftSubtitles(File file, long shift, String shiftfrom, String charset) {
	try {
	    String filename = file.getAbsolutePath();
	    Scanner fin = new Scanner(new FileInputStream(file), charset);
	    ArrayList<String> filecontent = new ArrayList<>();
	    while (fin.hasNextLine())
		filecontent.add(fin.nextLine());
	    fin.close();

	    System.out.println(filecontent.get(3));

	    String oldCopyPath = filename.substring(0, filename.length() - 4) + " old"
		    + filename.substring(filename.length() - 4);

	    // if (!(new File(oldCopyPath)).exists())
	    file.renameTo(new File(oldCopyPath));

	    file = new File(filename);

	    // PrintWriter fout = new PrintWriter(file);
	    PrintWriter fout = new PrintWriter(file.getAbsolutePath(), charset);
	    for (String line : filecontent) {
		if (line.contains(" --> ")) {
		    int firstSpace = line.indexOf(' ');
		    int lastSpace = line.indexOf(' ', firstSpace + 1);
		    String fromTime = line.substring(0, firstSpace);
		    String toTime = line.substring(lastSpace + 1);
		    line = shiftDate(fromTime, shift, shiftfrom) + " --> " + shiftDate(toTime, shift, shiftfrom);
		}
		fout.println(line);
	    }
	    fout.flush();
	    fout.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
