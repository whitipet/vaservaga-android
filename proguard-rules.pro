#Remove logs
-assumenosideeffects class android.util.Log {
	public static boolean isLoggable(java.lang.String, int);
	public static int v(...);
	public static int d(...);
	public static int i(...);
	public static int w(...);
	public static int e(...);
	public static int wtf(...);
	public static int println(...);
}

# Get rid of package names, makes file smaller
-repackageclasses