import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.IOException;

public class DirSize{
	public static void main(String[] args){
		String dir = args[0];
		

		Path path = Paths.get(dir);
		try{
		Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
			@Override
			public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attrs) throws IOException{	
				SizeCalc FolderSize = new SizeCalc(directory); //Iterating on all directories and creating a thread for each one
				FolderSize.start();
				return FileVisitResult.CONTINUE;
			}
		});}
		catch (IOException e){
			e.printStackTrace();
		}
}}

class SizeCalc extends Thread{
	
	private Path path;
	private long totalSize;
	
	public SizeCalc(Path directory){
		
		path = directory;
		totalSize = 0;
		
	}
	
	public void run(){
		try{
		Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					System.out.println("File stats are found: path=" + file + ", size=" + attrs.size());
					totalSize = totalSize + attrs.size(); //Outputting each individual file details and adding each file size to the total in order to calculate the directory size
                    return FileVisitResult.CONTINUE;
                }
		});
		System.out.println("Folder stats are found: path=" + path + ", size=" + totalSize);}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}