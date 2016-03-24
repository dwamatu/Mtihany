package com.example.white.mtihany.utility;


        import android.content.Context;
        import android.os.Environment;

        import com.example.white.mtihany.R;

        import java.io.File;
        import java.util.ArrayList;
        import java.util.List;

public class ExternalStorageUtils {
    public static String[] documentDirectories;
    public static String rootDirectory;

    public static void createDirectory(File folder) {
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public static void createAppDirectories(Context context) {
        if (!isFullyAccessible()) {
            return;
        }
        rootDirectory = getRootDirectory(context);
        documentDirectories = context.getResources().getStringArray(R.array.resourceDirectory);

        for (String dir : documentDirectories) {
            File folder = new File(rootDirectory + dir);
            createDirectory(folder);
        }
    }

    public static String getRootDirectory(Context context) {
        return Environment.getExternalStorageDirectory() + "/" + context.getResources().getString(R.string.mtihany_root_directory);
    }

    public static List<File> getAllMedia() {
        List<File> files = new ArrayList<>();
        for (String dir : documentDirectories) {
            File folder = new File(rootDirectory + dir);
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    files.add(file);
                }
            }
        }

        return files;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public static boolean isFullyAccessible() {
        return (isExternalStorageReadable() && isExternalStorageWritable());
    }
}
